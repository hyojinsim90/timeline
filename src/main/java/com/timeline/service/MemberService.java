package com.timeline.service;

import com.timeline.controller.dto.MemberListResponseDto;
import com.timeline.controller.dto.MemberResponseDto;
import com.timeline.controller.dto.MemberUpdateRequestDto;
import com.timeline.entity.Member;
import com.timeline.repository.MemberRepository;
import com.timeline.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 5:00
 * @brief : 인증된 유저 CRUD 처리
**/
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        log.info("[MemberService - getMyInfo]");
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    /* 유저 정보 가져옴(한명) */
    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    /* 전체 유저 정보 가져옴 */
    @Transactional(readOnly = true) // readOnly : 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도 개선
    public List<MemberListResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberListResponseDto::new) // = .map(Member -> new MemberListResponseDto(Member))
                .collect(Collectors.toList());
        // MemberListResponseDto 넘어온 Member Stream을 map을 통해 MemberListResponseDto 변환 -> List로 반환
    }

    /* 회원정보 수정 */
    @Transactional
    public MemberResponseDto update(String email, MemberUpdateRequestDto requestDto){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다. email =" + email));

        member.update(requestDto.getPassword(), requestDto.getNickname());

        return new MemberResponseDto(member.getEmail());
    }

    /* 회원정보 삭제 */
    @Transactional
    public void delete(String email){
        // 존재하는 Member인지 확인을 위해 엔티티 조회 후 삭제
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다. email =" + email));
        memberRepository.delete(member);
        // 엔티티를 파라미터로 삭제할 수도 있고, deleteById 메소드를 이용하면 id로 삭제할수도있다.
    }

}
