package com.umass.hangout.user_registeration.service;

import com.umass.hangout.user_registeration.dto.UserRequest;
import com.umass.hangout.user_registeration.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRequest userRequest);
    UserResponse login(UserRequest userRequest);
}