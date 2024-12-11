package com.umass.hangout.user_registeration.service.impl;

import com.umass.hangout.user_registeration.constants.ResponseCodes;
import com.umass.hangout.user_registeration.dto.UserRequest;
import com.umass.hangout.user_registeration.dto.UserResponse;
import com.umass.hangout.user_registeration.dto.UserSuccessResponse;
import com.umass.hangout.user_registeration.entity.User;
import com.umass.hangout.user_registeration.entity.UserProfile;
import com.umass.hangout.user_registeration.repository.UserProfileRepository;
import com.umass.hangout.user_registeration.repository.UserRepository;
import com.umass.hangout.user_registeration.service.UserService;
import com.umass.hangout.user_registeration.constants.Constants;
import com.umass.hangout.user_registeration.dto.ErrorResponse;
import javax.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Transactional
    public User createUser(String email, String password) {
        try {
            // Create user
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            User savedUser = userRepository.save(user);

            // Create user profile for the first-time login (firstTimeLogin = false)
            if (savedUser != null && savedUser.getId() != null) {
                UserProfile userProfile = new UserProfile();
                userProfile.setUser(savedUser);
                userProfile.setUserId(savedUser.getId());
                userProfile.setEmail(email);
                userProfileRepository.save(userProfile);
            }
            return savedUser;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        UserResponse userResponse = null;
        try {
            String email = userRequest.getEmail();
            String password = userRequest.getPassword();

            userResponse = new UserResponse();

            System.out.println("Inside register in service");
            if (userRepository.findByEmail(email).isPresent()) {
                userResponse.setHttpResponseCode(400);
                userResponse.setErrorResponse(new ErrorResponse(ResponseCodes.USER_ALREADY_EXISTS.getValue(), ResponseCodes.USER_ALREADY_EXISTS.getMessage()));
                return userResponse;
            }

            User savedUser = this.createUser(email, password);

            if (savedUser != null && savedUser.getId() != null) {
                // Create success reponse
                UserSuccessResponse userSuccessResponse = new UserSuccessResponse(ResponseCodes.USER_REGISTER_SUCCESS.getValue(), savedUser, ResponseCodes.USER_REGISTER_SUCCESS.getMessage());
                userResponse.setHttpResponseCode(201);
                userResponse.setSuccessResponse(userSuccessResponse);
            } else {
                throw new RuntimeException("User creation failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            userResponse.setHttpResponseCode(500);
            userResponse.setErrorResponse(new ErrorResponse(ResponseCodes.GENERIC_ERROR.getValue(), ResponseCodes.GENERIC_ERROR.getMessage()));
        }
        return userResponse;
    }

    @Override
    public UserResponse login(UserRequest userRequest) {
        UserResponse userResponse = null;
        try {
            userResponse = new UserResponse();
            String email = userRequest.getEmail();
            String password = userRequest.getPassword();

            Optional<User> optionalUser = userRepository.findByEmail(userRequest.getEmail());
            if (!optionalUser.isPresent()) {
                // Email Id is invalid
                userResponse.setHttpResponseCode(404);
                userResponse.setErrorResponse(new ErrorResponse(ResponseCodes.USER_DOES_NOT_EXIST.getValue(), ResponseCodes.USER_DOES_NOT_EXIST.getMessage()));
                return userResponse;
            }
            User user = optionalUser.get();
            if (!user.getPassword().equals(userRequest.getPassword())) {
                userResponse.setHttpResponseCode(401);
                userResponse.setErrorResponse(new ErrorResponse(ResponseCodes.USER_LOGIN_FAILURE_PASSWORD.getValue(), ResponseCodes.USER_LOGIN_FAILURE_PASSWORD.getMessage()));
                return userResponse;
            }
            // Login Success
            userResponse.setHttpResponseCode(200);
            userResponse.setSuccessResponse(new UserSuccessResponse(ResponseCodes.USER_LOGIN_SUCCESS.getValue(), user, ResponseCodes.USER_LOGIN_SUCCESS.getMessage()));
        } catch(Exception e) {
            userResponse.setHttpResponseCode(500);
            userResponse.setErrorResponse(new ErrorResponse(ResponseCodes.GENERIC_ERROR.getValue(), ResponseCodes.GENERIC_ERROR.getMessage()));
        }
        return userResponse;
    }
}
