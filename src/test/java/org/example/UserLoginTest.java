package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserLoginTest {

    private static final String INCORRECT_FIELDS_MSG = "email or password are incorrect";

    @BeforeClass
    public static void beforeClass() {
        TestUtils.deleteDefaultUser();
        TestUtils.registerDefaultUser();

    }

    @AfterClass
    public static void afterClass() {
        TestUtils.deleteDefaultUser();
    }

    @Test
    @DisplayName("Successful login user")
    @Description("Успешная авторизация пользователя")
    public void successfulLoginUserTest() {
        TestUtils.loginDefaultUser()
            .then()
            .statusCode(200)
            .and()
            .body("success", Matchers.equalTo(true))
            .body("accessToken", Matchers.notNullValue())
            .body("refreshToken", Matchers.notNullValue())
            .body("user", Matchers.notNullValue())
            .body("user.email", Matchers.equalToIgnoringCase(TestUtils.DEFAULT_EMAIL))
            .body("user.name", Matchers.equalTo(TestUtils.DEFAULT_USERNAME));

        TestUtils.deleteDefaultUser();
    }

    @Test
    @DisplayName("Login user with incorrect name")
    @Description("Авторизация пользователя с неверным логином")
    public void loginUserWithIncorrectNameTest() {
        TestUtils.loginUser(TestUtils.INCORRECT_USERNAME, TestUtils.DEFAULT_PASSWORD, TestUtils.DEFAULT_EMAIL)
            .then()
            .statusCode(401)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(INCORRECT_FIELDS_MSG));
    }

    @Test
    @DisplayName("Login user with incorrect password")
    @Description("Авторизация пользователя с неверным паролем")
    public void loginUserWithIncorrectPasswordTest() {
        TestUtils.loginUser(TestUtils.DEFAULT_USERNAME, TestUtils.INCORRECT_PASSWORD, TestUtils.DEFAULT_EMAIL)
            .then()
            .statusCode(401)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(INCORRECT_FIELDS_MSG));
    }

}
