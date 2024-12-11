package com.umass.hangout.user_registeration.dto;

import com.umass.hangout.user_registeration.entity.User;
import com.umass.hangout.user_registeration.entity.UserProfile;

public class UserSuccessResponse {

        private int code;
        private UserData data;
        private String message;

        public UserSuccessResponse(int code, User data, String message) {
            this.code = code;
            if (data != null) {
                this.data = new UserData(data);
            }
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public UserData getData() {
            return data;
        }

        public void setData(UserData data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }


        public class UserData {
            private Long id;
            private String email;

            public UserData() {
            }

            public UserData(User user){
                this.id = user.getId();
                this.email = user.getEmail();
            }

            public Long getId() {
                return id;
            }

            public String getEmail() {
                return email;
            }
        }
    }