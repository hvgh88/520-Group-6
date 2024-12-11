package com.umass.hangout.user_registeration.controller;

// import com.google.gson.Gson;
import com.umass.hangout.user_registeration.dto.*;
import com.umass.hangout.user_registeration.service.UserProfileService;
import com.umass.hangout.user_registeration.service.UserService;
import com.umass.hangout.user_registeration.constants.Constants;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.register(userRequest);
        String responseBody;
        if (userResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            if (userResponse.getHttpResponseCode() == 201) {
                responseBody = new JsonConverter().toJson(userResponse.getSuccessResponse());
            } else {
                responseBody = new JsonConverter().toJson(userResponse.getErrorResponse());
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Connection", "keep-alive");
        return ResponseEntity.status(userResponse.getHttpResponseCode()).headers(headers).body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.login(userRequest);
        String responseBody;
        if (userResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            if (userResponse.getHttpResponseCode() == 200) {
                responseBody = new JsonConverter().toJson(userResponse.getSuccessResponse());
            } else {
                responseBody = new JsonConverter().toJson(userResponse.getErrorResponse());
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Connection", "keep-alive");
        return ResponseEntity.status(userResponse.getHttpResponseCode()).headers(headers).body(responseBody);
    }

    @PostMapping("/profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileRequest profileRequest) {
        UserProfileResponse userProfileResponse = userProfileService.updateUserProfile(profileRequest);
        String responseBody;
        if (userProfileResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            if (userProfileResponse.getHttpResponseCode() == 200) {
                responseBody = new JsonConverter().toJson(userProfileResponse.getSuccessResponse());
            } else {
                responseBody = new JsonConverter().toJson(userProfileResponse.getErrorResponse());
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Connection", "keep-alive");
        return ResponseEntity.status(userProfileResponse.getHttpResponseCode()).headers(headers).body(responseBody);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<String> getUserProfile(@PathVariable String email) {
        System.out.println("Inside get for getUserProfile");
        UserProfileResponse userProfileResponse = userProfileService.getUserProfile(email);
        String responseBody;
        if (userProfileResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            if (userProfileResponse.getHttpResponseCode() == 200) {
                responseBody = new JsonConverter().toJson(userProfileResponse.getSuccessResponse());
            } else {
                responseBody = new JsonConverter().toJson(userProfileResponse.getErrorResponse());
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Connection", "keep-alive");
        return ResponseEntity.status(userProfileResponse.getHttpResponseCode()).headers(headers).body(responseBody);
    }
}
