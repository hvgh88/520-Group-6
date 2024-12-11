package com.umass.hangout.user_registeration.controller;

import com.umass.hangout.user_registeration.constants.ResponseCodes;
import com.umass.hangout.user_registeration.controller.UserRegistrationController;
import com.umass.hangout.user_registeration.dto.*;
import com.umass.hangout.user_registeration.entity.User;
import com.umass.hangout.user_registeration.entity.UserProfile;
import com.umass.hangout.user_registeration.repository.UserRepository;
import com.umass.hangout.user_registeration.service.UserProfileService;
import com.umass.hangout.user_registeration.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserRegistrationControllerTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    public UserRegistrationControllerTests() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationController).build();
    }
//
//    @Test
//    public void testRegisterUserSuccess() throws Exception {
//        UserRequest userRequest = new UserRequest();
//
//        User userMock = new User();
//        userMock.setId(1L);
//        userMock.setEmail("test@test.com");
//
//        UserSuccessResponse successResponse = new UserSuccessResponse(201, userMock, "Registration successful!");
//
//        UserResponse userResponse = new UserResponse();
//        userResponse.setHttpResponseCode(201);
//        userResponse.setSuccessResponse(successResponse);
//
//        when(userService.register(userRequest)).thenReturn(userResponse);
//
//        mockMvc.perform(post("/api/v1/auth/register")
//                        .contentType("application/json")
//                        .content("{\"email\":\"test@test.com\", \"password\":\"password123\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Content-Type", "application/json"))
//                .andExpect(jsonPath("$.code").value(201))
//                .andExpect(jsonPath("$.data.email").value("test@test.com"))
//                .andExpect(jsonPath("$.message").value("Registration successful!"));
//    }

    @Mock
    private UserRepository userRepository;

    @Test
    public void testRegisterUserSuccess() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@test.com");
        userRequest.setPassword("password123");

        User userMock = new User();
        userMock.setId(1L);
        userMock.setEmail("test@test.com");

        UserSuccessResponse successResponse = new UserSuccessResponse(201, userMock, "Registration successful!");

        UserResponse userResponse = new UserResponse();
        userResponse.setHttpResponseCode(201);
        userResponse.setSuccessResponse(successResponse);

        // Mock repository behavior
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userMock);

        // Mock service behavior
        when(userService.register(any(UserRequest.class))).thenReturn(userResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType("application/json")
                        .content("{\"email\":\"test@test.com\", \"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data.email").value("test@test.com"))
                .andExpect(jsonPath("$.message").value("Registration successful!"));
    }

    @Test
    public void testRegisterUserEmailAlreadyExists() throws Exception {
        // Prepare mock input and output
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@test.com");
        userRequest.setPassword("password123");

        User existingUserMock = new User();
        existingUserMock.setId(1L);
        existingUserMock.setEmail("test@test.com");

        // Mock repository behavior to return an existing user
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUserMock));

        UserResponse errorResponse = new UserResponse();
        errorResponse.setHttpResponseCode(400);
        errorResponse.setErrorResponse(new ErrorResponse(
                ResponseCodes.USER_ALREADY_EXISTS.getValue(),
                ResponseCodes.USER_ALREADY_EXISTS.getMessage()
        ));

        // Mock service behavior
        when(userService.register(any(UserRequest.class))).thenReturn(errorResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType("application/json")
                        .content("{\"email\":\"test@test.com\", \"password\":\"password123\"}"))
                .andExpect(status().isBadRequest()) // 400
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(ResponseCodes.USER_ALREADY_EXISTS.getValue()))
                .andExpect(jsonPath("$.message").value(ResponseCodes.USER_ALREADY_EXISTS.getMessage()));
    }

    @Test
    public void testRegisterGetMethodNotAllowed() throws Exception {
        // Perform the test with GET request, which is not allowed for login
        mockMvc.perform(get("/api/v1/auth/register"))
                .andExpect(status().isMethodNotAllowed()); // 405
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        // Prepare mock input and output
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@test.com");
        userRequest.setPassword("password123");

        User existingUserMock = new User();
        existingUserMock.setId(1L);
        existingUserMock.setEmail("test@test.com");
        existingUserMock.setPassword("password123");

        UserSuccessResponse successResponse = new UserSuccessResponse(200, existingUserMock, "Login successful!");

        UserResponse userResponse = new UserResponse();
        userResponse.setHttpResponseCode(200);
        userResponse.setSuccessResponse(successResponse);

        // Mock repository behavior to return an existing user
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUserMock));

        // Mock service behavior
        when(userService.login(any(UserRequest.class))).thenReturn(userResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType("application/json")
                        .content("{\"email\":\"test@test.com\", \"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.email").value("test@test.com"))
                .andExpect(jsonPath("$.message").value("Login successful!"));
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        // Prepare mock input
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("nonexistent@test.com");
        userRequest.setPassword("password123");

        UserResponse errorResponse = new UserResponse();
        errorResponse.setHttpResponseCode(404);
        errorResponse.setErrorResponse(new ErrorResponse(ResponseCodes.USER_DOES_NOT_EXIST.getValue(), ResponseCodes.USER_DOES_NOT_EXIST.getMessage()));

        // Mock repository behavior to return empty (user not found)
        when(userRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        // Mock service behavior
        when(userService.login(any(UserRequest.class))).thenReturn(errorResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType("application/json")
                        .content("{\"email\":\"nonexistent@test.com\", \"password\":\"password123\"}"))
                .andExpect(status().isNotFound()) // 404
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(ResponseCodes.USER_DOES_NOT_EXIST.getValue()))
                .andExpect(jsonPath("$.message").value(ResponseCodes.USER_DOES_NOT_EXIST.getMessage()));
    }

    @Test
    public void testLoginInvalidPassword() throws Exception {
        // Prepare mock input and output
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@test.com");
        userRequest.setPassword("wrongpassword");

        User existingUserMock = new User();
        existingUserMock.setId(1L);
        existingUserMock.setEmail("test@test.com");
        existingUserMock.setPassword("password123");

        UserResponse errorResponse = new UserResponse();
        errorResponse.setHttpResponseCode(401);
        errorResponse.setErrorResponse(new ErrorResponse(ResponseCodes.USER_LOGIN_FAILURE_PASSWORD.getValue(), ResponseCodes.USER_LOGIN_FAILURE_PASSWORD.getMessage()));

        // Mock repository behavior to return an existing user
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUserMock));

        // Mock service behavior
        when(userService.login(any(UserRequest.class))).thenReturn(errorResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType("application/json")
                        .content("{\"email\":\"test@test.com\", \"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized()) // 401
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(ResponseCodes.USER_LOGIN_FAILURE_PASSWORD.getValue()))
                .andExpect(jsonPath("$.message").value(ResponseCodes.USER_LOGIN_FAILURE_PASSWORD.getMessage()));
    }

    @Test
    public void testLoginGetMethodNotAllowed() throws Exception {
        // Perform the test with GET request, which is not allowed for login
        mockMvc.perform(get("/api/v1/auth/login"))
                .andExpect(status().isMethodNotAllowed()); // 405
    }

    @Test
    public void testUpdateUserProfile_SuccessfulCreate() throws Exception {
        // Mock the service
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setHttpResponseCode(200);
        UserProfile userProfile = new UserProfile(1L, "testuser@example.com", "test", "middle", "last", "computer science", "", 2024);
        userProfileResponse.setSuccessResponse(new UserProfileSuccessResponse(ResponseCodes.UP_CREATE_SUCCESS.getValue(), userProfile, ResponseCodes.UP_CREATE_SUCCESS.getMessage()));

        when(userProfileService.updateUserProfile(any(UserProfileRequest.class))).thenReturn(userProfileResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/auth/profile")
                        .contentType("application/json")
                        .content("{\"emailId\":\"testuser@example.com\",\"firstName\":\"test\",\"middleName\":\"middle\",\"lastName\":\"last\", \"department\":\"computer science\", \"bio\":\"\",\"graduationYear\":\"2024\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((jsonPath("$.code").value(ResponseCodes.UP_CREATE_SUCCESS.getValue())))
                .andExpect(jsonPath("$.message").value(ResponseCodes.UP_CREATE_SUCCESS.getMessage()));
    }

    @Test
    public void testUpdateUserProfile_UserNotFound() throws Exception {
        // Mock the service for user not found
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setHttpResponseCode(404);
        userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.UP_DOES_NOT_EXIST.getValue(), ResponseCodes.UP_DOES_NOT_EXIST.getMessage()));

        when(userProfileService.updateUserProfile(any(UserProfileRequest.class))).thenReturn(userProfileResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/auth/profile")
                        .contentType("application/json")
                        .content("{\"emailId\":\"nonexistent@example.com\",\"firstName\":\"test\",\"middleName\":\"middle\",\"lastName\":\"last\", \"department\":\"computer science\", \"bio\":\"\",\"graduationYear\":\"2024\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(ResponseCodes.UP_DOES_NOT_EXIST.getValue()))
                .andExpect(jsonPath("$.message").value(ResponseCodes.UP_DOES_NOT_EXIST.getMessage()));
    }

    @Test
    public void testUpdateUserProfile_InternalServerError() throws Exception {
        // Mock the service for internal server error
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setHttpResponseCode(500);
        userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.UP_UPDATE_FAIL.getValue(), ResponseCodes.UP_UPDATE_FAIL.getMessage()));

        when(userProfileService.updateUserProfile(any(UserProfileRequest.class))).thenReturn(userProfileResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/auth/profile")
                        .contentType("application/json")
                        .content("{\"emailId\":\"testuser@example.com\",\"firstName\":\"test\",\"middleName\":\"middle\",\"lastName\":\"last\", \"department\":\"computer science\", \"bio\":\"\",\"graduationYear\":\"2024\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(ResponseCodes.UP_UPDATE_FAIL.getValue()))
                .andExpect(jsonPath("$.message").value(ResponseCodes.UP_UPDATE_FAIL.getMessage()));
    }

    @Test
    public void testGetUserProfile_UserNotFound() throws Exception {
        // Mock the service for user profile not found
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setHttpResponseCode(404);
        userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.UP_DOES_NOT_EXIST.getValue(), ResponseCodes.UP_DOES_NOT_EXIST.getMessage()));

        when(userProfileService.getUserProfile(anyString())).thenReturn(userProfileResponse);

        // Perform the test
        mockMvc.perform(get("/api/v1/auth/profile/nonexistent@example.com")
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(ResponseCodes.UP_DOES_NOT_EXIST.getValue()))
                .andExpect(jsonPath("$.message").value(ResponseCodes.UP_DOES_NOT_EXIST.getMessage()));
    }

    @Test
    public void testGetUserProfile_InternalServerError() throws Exception {
        // Mock the service for internal server error
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setHttpResponseCode(500);
        userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.UP_LOOKUP_FAIL.getValue(), ResponseCodes.UP_LOOKUP_FAIL.getMessage()));

        when(userProfileService.getUserProfile(anyString())).thenReturn(userProfileResponse);

        // Perform the test
        mockMvc.perform(get("/api/v1/auth/profile/testuser@example.com")
                        .contentType("application/json"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(ResponseCodes.UP_LOOKUP_FAIL.getValue()))
                .andExpect(jsonPath("$.message").value(ResponseCodes.UP_LOOKUP_FAIL.getMessage()));
    }

    @Test
    public void testGetUserProfile_GenericError() throws Exception {
        // Mock the service for a generic error
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setHttpResponseCode(500);
        userProfileResponse.setErrorResponse(new ErrorResponse(ResponseCodes.GENERIC_ERROR.getValue(), ResponseCodes.GENERIC_ERROR.getMessage()));

        when(userProfileService.getUserProfile(anyString())).thenReturn(userProfileResponse);

        // Perform the test
        mockMvc.perform(get("/api/v1/auth/profile/testuser@example.com")
                        .contentType("application/json"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(ResponseCodes.GENERIC_ERROR.getValue()))
                .andExpect(jsonPath("$.message").value(ResponseCodes.GENERIC_ERROR.getMessage()));
    }

//    @Test
//    public void testRegisterUserFailure() throws Exception {
//        UserRequest userRequest = new UserRequest();
//        when(userService.register(userRequest)).thenReturn(null);
//
//        mockMvc.perform(post("/api/v1/auth/register")
//                        .contentType("application/json")
//                        .content("{\"email\":\"test@test.com\", \"password\":\"password123\"}"))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void testLoginUserSuccess() throws Exception {
//        UserRequest userRequest = new UserRequest();
//        UserResponse userResponse = new UserResponse();
//        userResponse.setHttpResponseCode(200);
//        userResponse.setSuccessResponse("Login successful!");
//
//        when(userService.login(userRequest)).thenReturn(userResponse);
//
//        mockMvc.perform(post("/api/v1/auth/login")
//                        .contentType("application/json")
//                        .content("{\"email\":\"test@test.com\", \"password\":\"password123\"}"))
//                .andExpect(status().isOk())
//                .andExpect(header().string("Content-Type", "application/json"))
//                .andExpect(content().string("{\"response\":\"Login successful!\"}"));
//    }
//
//    @Test
//    public void testLoginUserUnauthorized() throws Exception {
//        UserRequest userRequest = new UserRequest();
//        when(userService.login(userRequest)).thenReturn(null);
//
//        mockMvc.perform(post("/api/v1/auth/login")
//                        .contentType("application/json")
//                        .content("{\"email\":\"test@test.com\", \"password\":\"wrongpassword\"}"))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void testUpdateUserProfileSuccess() throws Exception {
//        UserProfileRequest profileRequest = new UserProfileRequest();
//        UserProfileResponse userProfileResponse = new UserProfileResponse();
//        userProfileResponse.setHttpResponseCode(200);
//        userProfileResponse.setSuccessResponse("Profile updated successfully!");
//
//        when(userProfileService.updateUserProfile(profileRequest)).thenReturn(userProfileResponse);
//
//        mockMvc.perform(post("/api/v1/auth/profile")
//                        .contentType("application/json")
//                        .content("{\"name\":\"John\", \"age\":30}"))
//                .andExpect(status().isOk())
//                .andExpect(header().string("Content-Type", "application/json"))
//                .andExpect(content().string("{\"response\":\"Profile updated successfully!\"}"));
//    }
//
//    @Test
//    public void testGetUserProfileSuccess() throws Exception {
//        UserProfileResponse userProfileResponse = new UserProfileResponse();
//        userProfileResponse.setHttpResponseCode(200);
//        userProfileResponse.setSuccessResponse("Profile fetched successfully!");
//
//        when(userProfileService.getUserProfile("test@test.com")).thenReturn(userProfileResponse);
//
//        mockMvc.perform(get("/api/v1/auth/profile/test@test.com"))
//                .andExpect(status().isOk())
//                .andExpect(header().string("Content-Type", "application/json"))
//                .andExpect(content().string("{\"response\":\"Profile fetched successfully!\"}"));
//    }
//
//    @Test
//    public void testGetUserProfileNotFound() throws Exception {
//        when(userProfileService.getUserProfile("unknown@test.com")).thenReturn(null);
//
//        mockMvc.perform(get("/api/v1/auth/profile/unknown@test.com"))
//                .andExpect(status().isBadRequest());
//    }
}
