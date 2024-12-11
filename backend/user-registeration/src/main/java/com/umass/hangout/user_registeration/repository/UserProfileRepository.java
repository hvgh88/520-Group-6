package com.umass.hangout.user_registeration.repository;

import com.umass.hangout.user_registeration.entity.UserProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findById(long userId);
    Optional<UserProfile> findByEmail(String email);
}
