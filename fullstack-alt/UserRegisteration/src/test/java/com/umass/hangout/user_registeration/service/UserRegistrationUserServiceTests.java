// package com.umass.hangout.user_registeration.service;

// import com.umass.hangout.user_registeration.constants.ResponseCodes;
// import com.umass.hangout.user_registeration.dto.UserRequest;
// import com.umass.hangout.user_registeration.dto.UserResponse;
// import com.umass.hangout.user_registeration.entity.User;
// import com.umass.hangout.user_registeration.repository.UserProfileRepository;
// import com.umass.hangout.user_registeration.repository.UserRepository;
// import com.umass.hangout.user_registeration.service.impl.UserServiceImpl;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.Mockito.when;

// public class UserRegistrationUserServiceTests {

//     @InjectMocks
//     private UserServiceImpl userService;

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private UserProfileRepository userProfileRepository;

//     private UserRequest userRequest;

//     @BeforeEach
//     public void setup() {
//         MockitoAnnotations.openMocks(this);
//         userRequest = new UserRequest();
//         userRequest.setEmail("testuser@example.com");
//         userRequest.setPassword("password123");
//     }

//     @Test
//     public void testRegister_Success() {
//         User user = new User();
//         user.setId(1L);
//         user.setEmail("testuser@example.com");
//         user.setPassword("password123");

//         // Mock behavior for userRepository
//         when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(java.util.Optional.empty());
//         when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

//         UserResponse response = userService.register(userRequest);

//         assertEquals(201, response.getHttpResponseCode());
//         assertNotNull(response.getSuccessResponse());
//         assertEquals(ResponseCodes.USER_REGISTER_SUCCESS.getValue(), response.getSuccessResponse().getCode());
//     }

//     @Test
//     public void testRegister_UserAlreadyExists() {
//         User user = new User();
//         user.setEmail("testuser@example.com");

//         // Mock behavior for userRepository
//         when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(java.util.Optional.of(user));

//         UserResponse response = userService.register(userRequest);

//         assertEquals(400, response.getHttpResponseCode());
//         assertNotNull(response.getErrorResponse());
//         assertEquals(ResponseCodes.USER_ALREADY_EXISTS.getValue(), response.getErrorResponse().getCode());
//     }

//     @Test
// //    public void testLogin_Success() {
// //        User user = new User();
// //        user.setEmail("testuser@example.com");
// //        user.setPassword("password123");
// //
// //        // Mock behavior for userRepository
// //        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(java.util.Optional.of(user));
// //
// //        UserResponse response = userService.login(userRequest);
// //
// //        assertEquals(200, response.getHttpResponseCode());
// //        assertNotNull(response.getSuccessResponse());
// //        assertEquals(ResponseCodes.USER_LOGIN_SUCCESS.getValue(), response.getSuccessResponse().getCode());
// //    }
//     public void testLogin_Success() {
//         // Initialize the userRequest object
//         UserRequest userRequest = new UserRequest();
//         userRequest.setEmail("testuser@example.com");
//         userRequest.setPassword("password123");

//         // Mock behavior for userRepository
//         User user = new User();
//         user.setId(1L);
//         user.setEmail("testuser@example.com");
//         user.setPassword("password123");

//         // Mocking the findByEmail method to return the mock user
//         when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(java.util.Optional.of(user));

//         // Call the login method
//         UserResponse response = userService.login(userRequest);

//         // Assert the response
//         assertEquals(200, response.getHttpResponseCode());
//         assertNotNull(response.getSuccessResponse());
//         assertEquals(ResponseCodes.USER_LOGIN_SUCCESS.getValue(), response.getSuccessResponse().getCode());
//     }

//     @Test
//     public void testLogin_UserNotFound() {
//         // Mock behavior for userRepository
//         when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(java.util.Optional.empty());

//         UserResponse response = userService.login(userRequest);

//         assertEquals(404, response.getHttpResponseCode());
//         assertNotNull(response.getErrorResponse());
//         assertEquals(ResponseCodes.USER_DOES_NOT_EXIST.getValue(), response.getErrorResponse().getCode());
//     }

//     @Test
//     public void testLogin_InvalidPassword() {
//         User user = new User();
//         user.setEmail("testuser@example.com");
//         user.setPassword("password123");

//         // Mock behavior for userRepository
//         when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(java.util.Optional.of(user));

//         // Change the password in the request
//         userRequest.setPassword("wrongpassword");

//         UserResponse response = userService.login(userRequest);

//         assertEquals(401, response.getHttpResponseCode());
//         assertNotNull(response.getErrorResponse());
//         assertEquals(ResponseCodes.USER_LOGIN_FAILURE_PASSWORD.getValue(), response.getErrorResponse().getCode());
//     }
// }
