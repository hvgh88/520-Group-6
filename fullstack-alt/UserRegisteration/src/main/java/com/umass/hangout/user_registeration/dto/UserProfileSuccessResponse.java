package com.umass.hangout.user_registeration.dto;

import com.umass.hangout.user_registeration.entity.User;
import com.umass.hangout.user_registeration.entity.UserProfile;
import javax.persistence.*;

public class UserProfileSuccessResponse {

    private int code;
    private UserProfileData data;
    private String message;

    public UserProfileSuccessResponse(int code, UserProfile data, String message) {
        this.code = code;
        if (data != null) {
            this.data = new UserProfileData(data);
        }
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserProfileData getData() {
        return data;
    }

    public void setData(UserProfileData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    class UserProfileData {
        private Long userId;
        private String email;
        private String firstName;
        private String middleName;
        private String lastName;
        private String department;
        private String bio;
        private int graduationYear;

        public UserProfileData() {}

        public UserProfileData(UserProfile userProfile) {
            this.userId = userProfile.getUserId();
            this.email = userProfile.getEmail();
            this.firstName = userProfile.getFirstName();
            this.middleName = userProfile.getMiddleName();
            this.lastName = userProfile.getLastName();
            this.department = userProfile.getDepartment();
            this.bio = userProfile.getBio();
            this.graduationYear = userProfile.getGraduationYear();
        }

        public Long getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getDepartment() {
            return department;
        }

        public String getBio() {
            return bio;
        }

        public int getGraduationYear() {
            return graduationYear;
        }
    }
}
