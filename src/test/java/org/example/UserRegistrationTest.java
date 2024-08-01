package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserRegistrationTest {

    private static final String USER_ALREADY_EXISTS_MSG = "User already exists";
    private static final String REQUIRED_FIELD_MISSED_MSG = "Email, password and name are required fields";

    @BeforeClass
    public static void beforeClass() {
        TestUtils.deleteDefaultUser();
    }

    @AfterClass
    public static void afterClass() {
        TestUtils.deleteDefaultUser();
    }

    @Test
    @DisplayName("Successful registration user")
    @Description("Успешная регистрация пользователя")
    public void successfulRegistrationUserTest() {
        TestUtils.registerDefaultUser()
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
    @DisplayName("Registration user that already exists")
    @Description("Регистрация уже существующего пользователя")
    public void registrationUserThatAlreadyExistsTest() {
        TestUtils.registerDefaultUser()
            .then()
            .statusCode(200)
            .and()
            .body("success", Matchers.equalTo(true))
            .body("accessToken", Matchers.notNullValue())
            .body("refreshToken", Matchers.notNullValue())
            .body("user", Matchers.notNullValue())
            .body("user.email", Matchers.equalToIgnoringCase(TestUtils.DEFAULT_EMAIL))
            .body("user.name", Matchers.equalTo(TestUtils.DEFAULT_USERNAME));

        TestUtils.registerDefaultUser()
            .then()
            .statusCode(403)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(USER_ALREADY_EXISTS_MSG));

        TestUtils.deleteDefaultUser();
    }

    @Test
    @DisplayName("Registration user without required field")
    @Description("Регистрация пользователя без одного обязательного поля")
    public void registrationUserWithoutRequiredFieldTest() {
        TestUtils.registerUser(TestUtils.DEFAULT_USERNAME, TestUtils.DEFAULT_PASSWORD, null)
            .then()
            .statusCode(403)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(REQUIRED_FIELD_MISSED_MSG));
    }

}
