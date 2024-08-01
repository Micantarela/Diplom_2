package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.dto.IngredientsResponseDto;
import org.example.dto.OrderCreateDto;
import org.example.dto.UserInfoDto;
import org.example.dto.UserLoginResponseDto;

import static io.restassured.RestAssured.given;

public class TestUtils {

    public static final String CONTENT_TYPE_HEADER = "Content-type";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String APPLICATION_JSON_VALUE = "application/json";

    public static final String STAND_URL = " https://stellarburgers.nomoreparties.site/api";
    public static final String REGISTRATION_URL = String.join("/", STAND_URL, "auth/register");
    public static final String USER_URL = String.join("/", STAND_URL, "auth/user");
    public static final String LOGIN_URL = String.join("/", STAND_URL, "auth/login");
    public static final String INGREDIENTS_URL = String.join("/", STAND_URL, "ingredients");
    public static final String ORDERS_URL = String.join("/", STAND_URL, "orders");

    public static final String DEFAULT_USERNAME = "someDefaultUsername1234568";
    public static final String DEFAULT_PASSWORD = "someDefaultPassword1234568";
    public static final String DEFAULT_EMAIL = "someDefaultEmail1234568@ya.ru";
    public static final String INCORRECT_USERNAME = "fkdfjhigwoefqiujfnviosuyigh34264";
    public static final String INCORRECT_PASSWORD = "fkdfjhigwoefqiujfnviosuyigh34264";

    @Step("Вызов запроса для регистрации пользователя")
    public static Response registerUser(String name, String password, String email) {
        UserInfoDto createDto = new UserInfoDto(name, password, email);
        return given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(createDto)
            .when()
            .post(REGISTRATION_URL);
    }

    @Step("Вызов запроса для регистрации стандартного тестового пользователя")
    public static Response registerDefaultUser() {
        UserInfoDto createDto = new UserInfoDto(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        return given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(createDto)
            .when()
            .post(REGISTRATION_URL);
    }

    @Step("Удаление стандартного тестового пользователя")
    public static void deleteDefaultUser() {
        Response response = loginUser(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        if (response.getStatusCode() == 200) {
            String accessToken = response.as(UserLoginResponseDto.class).getAccessToken();
            deleteUser(accessToken);
        }
    }

    @Step("Удаление пользователя")
    public static void deleteUser(String username, String password, String email) {
        Response response = loginUser(username, password, email);
        if (response.getStatusCode() == 200) {
            String accessToken = response.as(UserLoginResponseDto.class).getAccessToken();
            deleteUser(accessToken);
        }
    }

    @Step("Вызов запроса для авторизации пользователя")
    public static Response loginUser(String name, String password, String email) {
        UserInfoDto loginDto = new UserInfoDto(name, password, email);
        return given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(loginDto)
            .when()
            .post(LOGIN_URL);
    }

    @Step("Вызов запроса для авторизации стандартного тестового пользователя")
    public static Response loginDefaultUser() {
        UserInfoDto loginDto = new UserInfoDto(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
        return given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE)
            .auth().none()
            .and()
            .body(loginDto)
            .when()
            .post(LOGIN_URL);
    }

    @Step("Вызов запроса для удаления пользователя")
    public static void deleteUser(String accessToken) {
        given()
            .header(AUTHORIZATION_HEADER, accessToken)
            .when()
            .delete(USER_URL);
    }

    @Step("Вызов запроса для обновления пользователя")
    public static Response updateUser(UserInfoDto userInfoDto, String accessToken) {
        RequestSpecification spec = given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE);

        if (accessToken != null) {
            spec.header(AUTHORIZATION_HEADER, accessToken);
        }

        return spec
            .and()
            .body(userInfoDto)
            .when()
            .patch(USER_URL);
    }

    @Step("Вызов запроса для получения всех ингредиентов")
    public static IngredientsResponseDto findIngredients() {
        return given()
            .when()
            .get(INGREDIENTS_URL)
            .as(IngredientsResponseDto.class);
    }

    @Step("Вызов запроса для создания заказа")
    public static Response createOrder(OrderCreateDto createDto, String accessToken) {
        RequestSpecification spec = given()
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON_VALUE);

        if (accessToken != null) {
            spec.header(AUTHORIZATION_HEADER, accessToken);
        }

        return spec
            .and()
            .body(createDto)
            .when()
            .post(ORDERS_URL);
    }

    @Step("Вызов запроса для получения заказов конкретного пользователя")
    public static Response getOrdersList(String accessToken) {
        RequestSpecification spec = given();

        if (accessToken != null) {
            spec.header(AUTHORIZATION_HEADER, accessToken);
        }

        return spec
            .when()
            .get(ORDERS_URL);
    }
}
