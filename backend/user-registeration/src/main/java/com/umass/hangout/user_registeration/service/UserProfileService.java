package com.umass.hangout.user_registeration.service;

import com.umass.hangout.user_registeration.dto.UserProfileRequest;
import com.umass.hangout.user_registeration.dto.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse updateUserProfile(UserProfileRequest profileRequest);

    UserProfileResponse getUserProfile(String email);
}
