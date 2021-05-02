package com.timeline.service.user;

import com.timeline.controller.dto.user.UserListResponseDto;
import com.timeline.controller.dto.user.UserResponseDto;
import com.timeline.controller.dto.user.UserSaveRequestDto;
import com.timeline.controller.dto.user.UserUpdateRequestDto;
import com.timeline.domain.user.User;
import com.timeline.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 회원정보 하나 가져오기
    public UserResponseDto findByEmail(String email) {
        User entity = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email = " + email));

        return new UserResponseDto(entity);
    }


    // 회원정보 수정
    public String update(String email, UserUpdateRequestDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email = " + email));

        user.update(requestDto.getNumber(), requestDto.getNickName(), requestDto.getPassword(), requestDto.getCategory());

        return email;
    }

    // 회원정보 삭제
    @Transactional
    public void delete(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email = " + email));

        userRepository.delete(user);
    }

    // 회원정보 추가
    @Transactional
    public String save(UserSaveRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getEmail();
    }

}
