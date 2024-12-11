package com.umass.hangout.user_registeration.service;

import com.umass.hangout.user_registeration.constants.ResponseCodes;
import com.umass.hangout.user_registeration.dto.UserProfileRequest;
import com.umass.hangout.user_registeration.dto.UserProfileResponse;
import com.umass.hangout.user_registeration.entity.User;
import com.umass.hangout.user_registeration.entity.UserProfile;
import com.umass.hangout.user_registeration.repository.UserProfileRepository;
import com.umass.hangout.user_registeration.service.impl.UserProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserRegistrationUserProfileServiceTests {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private UserProfileRequest userProfileRequest;
    private UserProfile existingUserProfile;
    private User existingUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        // Set up mock request
        userProfileRequest = new UserProfileRequest();
        userProfileRequest.setEmailId("test@example.com");
        userProfileRequest.setFirstName("John");
        userProfileRequest.setMiddleName("D");
        userProfileRequest.setLastName("Doe");
        userProfileRequest.setDepartment("Engineering");
        userProfileRequest.setBio("Bio text");
        userProfileRequest.setGraduationYear(2025);

        // Set up an existing user profile
        existingUserProfile = new UserProfile();
        existingUserProfile.setEmail("test@example.com");
        existingUserProfile.setFirstName("John");
        existingUserProfile.setMiddleName("D");
        existingUserProfile.setLastName("Doe");
        existingUserProfile.setDepartment("Engineering");
        existingUserProfile.setBio("Bio text");
        existingUserProfile.setGraduationYear(2025);
        existingUserProfile.setFirstTimeUpdate(true);
    }

    @Test
    void testUpdateUserProfile_UserProfileExists_FirstTimeUpdate() {
        // Mock repository behavior
        when(userProfileRepository.findByEmail(userProfileRequest.getEmailId()))
                .thenReturn(Optional.of(existingUserProfile));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(existingUserProfile);

        // Call the method
        UserProfileResponse response = userProfileService.updateUserProfile(userProfileRequest);

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getHttpResponseCode());
        assertNotNull(response.getSuccessResponse());
        assertEquals(ResponseCodes.UP_CREATE_SUCCESS.getValue(), response.getSuccessResponse().getCode());
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void testUpdateUserProfile_UserProfileExists_SecondTimeUpdate() {
        // Mock repository behavior
        when(userProfileRepository.findByEmail(userProfileRequest.getEmailId()))
                .thenReturn(Optional.of(existingUserProfile));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(existingUserProfile);

        // Call the method
        UserProfileResponse response = userProfileService.updateUserProfile(userProfileRequest);

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getHttpResponseCode());
        assertNotNull(response.getSuccessResponse());
        assertEquals(ResponseCodes.UP_CREATE_SUCCESS.getValue(), response.getSuccessResponse().getCode());
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void testUpdateUserProfile_UserProfileNotFound() {
        // Mock repository behavior
        when(userProfileRepository.findByEmail(userProfileRequest.getEmailId()))
                .thenReturn(Optional.empty());

        // Call the method
        UserProfileResponse response = userProfileService.updateUserProfile(userProfileRequest);

        // Verify the response
        assertNotNull(response);
        assertEquals(404, response.getHttpResponseCode());
        assertNotNull(response.getErrorResponse());
        assertEquals(ResponseCodes.UP_DOES_NOT_EXIST.getValue(), response.getErrorResponse().getCode());
        verify(userProfileRepository, times(0)).save(any(UserProfile.class));
    }

    @Test
    void testGetUserProfile_UserProfileExists() {
        // Mock repository behavior
        when(userProfileRepository.findByEmail(userProfileRequest.getEmailId()))
                .thenReturn(Optional.of(existingUserProfile));

        // Call the method
        UserProfileResponse response = userProfileService.getUserProfile(userProfileRequest.getEmailId());

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getHttpResponseCode());
        assertNotNull(response.getSuccessResponse());
        assertEquals(ResponseCodes.UP_LOOKUP_SUCCESS.getValue(), response.getSuccessResponse().getCode());
    }

    @Test
    void testGetUserProfile_UserProfileNotFound() {
        // Mock repository behavior
        when(userProfileRepository.findByEmail(userProfileRequest.getEmailId()))
                .thenReturn(Optional.empty());

        // Call the method
        UserProfileResponse response = userProfileService.getUserProfile(userProfileRequest.getEmailId());

        // Verify the response
        assertNotNull(response);
        assertEquals(404, response.getHttpResponseCode());
        assertNotNull(response.getErrorResponse());
        assertEquals(ResponseCodes.UP_DOES_NOT_EXIST.getValue(), response.getErrorResponse().getCode());
    }

    @Test
    void testUpdateUserProfile_ExceptionHandling() {
        // Simulate an exception in repository
        when(userProfileRepository.findByEmail(userProfileRequest.getEmailId()))
                .thenThrow(new RuntimeException("Database error"));

        // Call the method
        UserProfileResponse response = userProfileService.updateUserProfile(userProfileRequest);

        // Verify the response
        assertNotNull(response);
        assertEquals(500, response.getHttpResponseCode());
        assertNotNull(response.getErrorResponse());
        assertEquals(ResponseCodes.GENERIC_ERROR.getValue(), response.getErrorResponse().getCode());
    }
}
