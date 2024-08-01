package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.dto.UserInfoDto;
import org.example.dto.UserLoginResponseDto;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserUpdateTest {

    private static final String AUTHORIZATION_REQUIRED_MSG = "You should be authorised";
    public static final String NEW_USERNAME = "someNewUsername1234568";
    public static final String NEW_PASSWORD = "someNewPassword1234568";
    public static final String NEW_EMAIL = "someNewEmail1234568434325@ya.ru";

    @BeforeClass
    public static void beforeClass() {
        TestUtils.deleteDefaultUser();
        TestUtils.deleteUser(NEW_USERNAME, TestUtils.DEFAULT_PASSWORD, TestUtils.DEFAULT_EMAIL);
        TestUtils.deleteUser(TestUtils.DEFAULT_USERNAME, NEW_PASSWORD, TestUtils.DEFAULT_EMAIL);
        TestUtils.deleteUser(TestUtils.DEFAULT_USERNAME, TestUtils.DEFAULT_PASSWORD, NEW_EMAIL);

    }

    @AfterClass
    public static void afterClass() {
        TestUtils.deleteDefaultUser();
        TestUtils.deleteUser(NEW_USERNAME, TestUtils.DEFAULT_PASSWORD, TestUtils.DEFAULT_EMAIL);
        TestUtils.deleteUser(TestUtils.DEFAULT_USERNAME, NEW_PASSWORD, TestUtils.DEFAULT_EMAIL);
        TestUtils.deleteUser(TestUtils.DEFAULT_USERNAME, TestUtils.DEFAULT_PASSWORD, NEW_EMAIL);
    }

    @After
    public void after() {
        TestUtils.deleteDefaultUser();
    }

    @Test
    @DisplayName("Update username field")
    @Description("Изменение имени пользователя")
    public void updateUsernameTest() {
        UserLoginResponseDto loginResponse = TestUtils.registerDefaultUser().as(UserLoginResponseDto.class);
        UserInfoDto userInfoDto = new UserInfoDto(NEW_USERNAME, null, null);

        TestUtils.updateUser(userInfoDto, loginResponse.getAccessToken())
            .then()
            .statusCode(200)
            .and()
            .body("success", Matchers.equalTo(true))
            .body("user.email", Matchers.equalToIgnoringCase(TestUtils.DEFAULT_EMAIL))
            .body("user.name", Matchers.equalTo(NEW_USERNAME));

        TestUtils.deleteUser(NEW_USERNAME, TestUtils.DEFAULT_PASSWORD, TestUtils.DEFAULT_EMAIL);
    }

    @Test
    @DisplayName("Update password field")
    @Description("Изменение пароля")
    public void updatePasswordTest() {
        UserLoginResponseDto loginResponse = TestUtils.registerDefaultUser().as(UserLoginResponseDto.class);
        UserInfoDto userInfoDto = new UserInfoDto(null, NEW_PASSWORD, null);
        TestUtils.updateUser(userInfoDto, loginResponse.getAccessToken())
            .then()
            .statusCode(200)
            .and()
            .body("success", Matchers.equalTo(true))
            .body("user.email", Matchers.equalToIgnoringCase(TestUtils.DEFAULT_EMAIL))
            .body("user.name", Matchers.equalTo(TestUtils.DEFAULT_USERNAME));

        TestUtils.deleteUser(TestUtils.DEFAULT_USERNAME, NEW_PASSWORD, TestUtils.DEFAULT_EMAIL);
    }

    @Test
    @DisplayName("Update email field")
    @Description("Изменение адреса электронной почты")
    public void updateEmailTest() {
        UserLoginResponseDto loginResponse = TestUtils.registerDefaultUser().as(UserLoginResponseDto.class);
        UserInfoDto userInfoDto = new UserInfoDto(null, null, NEW_EMAIL);

        TestUtils.updateUser(userInfoDto, loginResponse.getAccessToken())
            .then()
            .statusCode(200)
            .and()
            .body("success", Matchers.equalTo(true))
            .body("user.email", Matchers.equalToIgnoringCase(NEW_EMAIL))
            .body("user.name", Matchers.equalTo(TestUtils.DEFAULT_USERNAME));

        TestUtils.deleteUser(TestUtils.DEFAULT_USERNAME, TestUtils.DEFAULT_PASSWORD, NEW_EMAIL);
    }


    @Test
    @DisplayName("Update username field without authorization")
    @Description("Изменение имени пользователя без авторизации")
    public void updateUsernameWithoutAuthorizationTest() {
        UserLoginResponseDto loginResponse = TestUtils.registerDefaultUser().as(UserLoginResponseDto.class);
        UserInfoDto userInfoDto = new UserInfoDto(NEW_USERNAME, null, null);

        TestUtils.updateUser(userInfoDto, null)
            .then()
            .statusCode(401)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(AUTHORIZATION_REQUIRED_MSG));

        TestUtils.deleteDefaultUser();
    }

    @Test
    @DisplayName("Update password field without authorization")
    @Description("Изменение пароля без авторизации")
    public void updatePasswordWithoutAuthorizationTest() {
        UserLoginResponseDto loginResponse = TestUtils.registerDefaultUser().as(UserLoginResponseDto.class);
        UserInfoDto userInfoDto = new UserInfoDto(null, NEW_PASSWORD, null);

        TestUtils.updateUser(userInfoDto, null)
            .then()
            .statusCode(401)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(AUTHORIZATION_REQUIRED_MSG));

        TestUtils.deleteDefaultUser();
    }

    @Test
    @DisplayName("Update email field without authorization")
    @Description("Изменение адреса электронной почты без авторизации")
    public void updateEmailWithoutAuthorizationTest() {
        UserLoginResponseDto loginResponse = TestUtils.registerDefaultUser().as(UserLoginResponseDto.class);
        UserInfoDto userInfoDto = new UserInfoDto(null, null, NEW_EMAIL);

        TestUtils.updateUser(userInfoDto, null)
            .then()
            .statusCode(401)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(AUTHORIZATION_REQUIRED_MSG));

        TestUtils.deleteDefaultUser();
    }


}
