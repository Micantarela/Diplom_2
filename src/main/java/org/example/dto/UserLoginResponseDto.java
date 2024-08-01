package org.example.dto;

public class UserLoginResponseDto {

    private Boolean success;
    private String accessToken;
    private String refreshToken;
    private UserDto user;

    public UserLoginResponseDto(Boolean success, String accessToken, String refreshToken, UserDto user) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public UserLoginResponseDto() {
    }

    static class UserDto {
        private String name;
        private String email;

        public UserDto(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public UserDto() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
