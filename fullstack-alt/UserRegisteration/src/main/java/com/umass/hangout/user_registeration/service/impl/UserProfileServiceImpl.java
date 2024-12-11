package com.umass.hangout.user_registeration.service.impl;

import com.umass.hangout.user_registeration.constants.ResponseCodes;
import com.umass.hangout.user_registeration.dto.ErrorResponse;
import com.umass.hangout.user_registeration.dto.UserProfileRequest;
import com.umass.hangout.user_registeration.dto.UserProfileResponse;
import com.umass.hangout.user_registeration.dto.UserProfileSuccessResponse;
import com.umass.hangout.user_registeration.entity.User;
import com.umass.hangout.user_registeration.entity.UserProfile;
import com.umass.hangout.user_registeration.repository.UserProfileRepository;
import com.umass.hangout.user_registeration.repository.UserRepository;
import com.umass.hangout.user_registeration.service.UserProfileService;
import com.umass.hangout.user_registeration.constants.Constants;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserProfileResponse updateUserProfile(UserProfileRequest profileRequest) {
        UserProfileResponse userProfileResponse = null;
        boolean firstTimeUpdate = false;
        try{
            userProfileResponse = new UserProfileResponse();
            String email = profileRequest.getEmailId();
            System.out.println(email);
            Optional<UserProfile> optionaUserProfile = userProfileRepository.findByEmail(email);
            System.out.println("Inside updateUserPRofile ");
            if (!optionaUserProfile.isPresent()) {
                userProfileResponse.setHttpResponseCode(404);
                userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.UP_DOES_NOT_EXIST.getValue(), ResponseCodes.UP_DOES_NOT_EXIST.getMessage()));
                return userProfileResponse;
            }
            UserProfile userProfile = optionaUserProfile.get();
            if (userProfile.isFirstTimeUpdate()) {
                firstTimeUpdate = true;
                userProfile.setFirstTimeUpdate(false);
            }
            userProfile.setFirstName(profileRequest.getFirstName());
            userProfile.setMiddleName(profileRequest.getMiddleName());
            userProfile.setLastName(profileRequest.getLastName());
            userProfile.setDepartment(profileRequest.getDepartment());
            userProfile.setBio(profileRequest.getBio());
            userProfile.setGraduationYear(profileRequest.getGraduationYear());

            UserProfile savedUserProfile = userProfileRepository.save(userProfile);
            if (savedUserProfile != null) {
                userProfileResponse.setHttpResponseCode(200);
                if (firstTimeUpdate) {
                    userProfileResponse.setSuccessResponse(new UserProfileSuccessResponse(ResponseCodes.UP_CREATE_SUCCESS.getValue(), savedUserProfile, ResponseCodes.UP_CREATE_SUCCESS.getMessage()));
                } else {

                    userProfileResponse.setSuccessResponse(new UserProfileSuccessResponse(ResponseCodes.UP_UPDATE_SUCCESS.getValue(), savedUserProfile, ResponseCodes.UP_UPDATE_SUCCESS.getMessage()));
                }
            } else {
                userProfileResponse.setHttpResponseCode(500);
                userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.UP_UPDATE_FAIL.getValue(), ResponseCodes.UP_UPDATE_FAIL.getMessage()));
            }
        } catch (Exception e) {
            userProfileResponse.setHttpResponseCode(500);
            userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.GENERIC_ERROR.getValue(), ResponseCodes.GENERIC_ERROR.getMessage()));
        }
        return userProfileResponse;
    }

    public UserProfileResponse getUserProfile(String email) {
        System.out.println("Inside user profile from service");
        UserProfileResponse userProfileResponse = null;
        try {
            userProfileResponse = new UserProfileResponse();
            Optional<UserProfile> optionaUserProfile = userProfileRepository.findByEmail(email);

            if (!optionaUserProfile.isPresent()) {
                System.out.println("did not find the profile");
                userProfileResponse.setHttpResponseCode(404);
                userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.UP_DOES_NOT_EXIST.getValue(), ResponseCodes.UP_DOES_NOT_EXIST.getMessage()));
                return userProfileResponse;
            }
            UserProfile userProfile = optionaUserProfile.get();
            if (userProfile != null) {
                userProfileResponse.setHttpResponseCode(200);
                userProfileResponse.setSuccessResponse(new UserProfileSuccessResponse(ResponseCodes.UP_LOOKUP_SUCCESS.getValue(), userProfile, ResponseCodes.UP_LOOKUP_SUCCESS.getMessage()));
            } else {
                userProfileResponse.setHttpResponseCode(500);
                userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.UP_LOOKUP_FAIL.getValue(), ResponseCodes.UP_LOOKUP_FAIL.getMessage()));
            }
            } catch (Exception e) {
            userProfileResponse.setHttpResponseCode(500);
            userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.GENERIC_ERROR.getValue(), ResponseCodes.GENERIC_ERROR.getMessage()));
        }
        return userProfileResponse;
    }
}
