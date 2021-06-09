package com.timeline.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-01 오후 6:07
 * @brief : aws s3 이미지 업로드
 **/
@Slf4j
@Service
@NoArgsConstructor
public class S3Service {

    private AmazonS3 s3Client;
    public static final String CLOUD_FRONT_DOMAIN_NAME = "d1sqaqxrsss9su.cloudfront.net"; // CloudFront 도메인명

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(String currentFilePath, MultipartFile file) throws IOException {
        // S3에 직접 접근하는 것이 아닌, CloudFront을 통해 캐싱된 이미지를 가져올 것

        log.info("[file - getSize]" + file.getSize());


        if(file.getSize()<10){

            String absolutePath = new File("").getAbsolutePath();
            File orifile = new File(absolutePath + "/timeline/src/main/resources/image/timeline.jpg");

            // 고유한 key값을 갖기 위해 현재 시간을 postfix로 붙여줌
            SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
            String fileName = date.format(new Date()) + "-" + orifile.getName();

            FileInputStream input = new FileInputStream(orifile);

            // 파일 업로드
            s3Client.putObject(new PutObjectRequest(bucket, fileName, input, null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return fileName;

        } else {
            // 고유한 key값을 갖기 위해 현재 시간을 postfix로 붙여줌
            SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
            String fileName = date.format(new Date()) + "-" + file.getOriginalFilename();

            // key가 존재하면 기존 파일은 삭제
            if ("".equals(currentFilePath) == false && currentFilePath != null) {
                boolean isExistObject = s3Client.doesObjectExist(bucket, currentFilePath);

                if (isExistObject == true) {
                    s3Client.deleteObject(bucket, currentFilePath);
                    log.info("기존파일 존재. 삭제 완료.");
                }
            }

            // 파일 업로드
            s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return fileName; // 파일명만 db에 저장되므로 s3객체의 key가 됨. 불러올때는 cloudFront도메인명+key가 되야함
        }


    }
}
