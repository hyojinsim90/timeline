package com.timeline.repository;

import com.timeline.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByKey(String key);

    void delete(RefreshToken refreshToken);
}
