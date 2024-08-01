package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.dto.UserLoginResponseDto;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GetOrdersListTest {

    public static final String AUTHORIZATION_REQUIRED_MSG = "You should be authorised";

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
    @DisplayName("Get orders list with authorization")
    @Description("Получение списка заказов для авторизованного пользователя")
    public void getOrdersListWithAuthorization() {
        UserLoginResponseDto loginInfo = TestUtils.loginDefaultUser().as(UserLoginResponseDto.class);

        TestUtils.getOrdersList(loginInfo.getAccessToken())
            .then()
            .statusCode(200)
            .and()
            .body("success", Matchers.equalTo(true))
            .body("orders", Matchers.notNullValue())
            .body("total", Matchers.notNullValue())
            .body("totalToday", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Get orders list without authorization")
    @Description("Получение списка заказов для неавторизованного пользователя")
    public void getOrdersListWithoutAuthorization() {
        TestUtils.getOrdersList(null)
            .then()
            .statusCode(401)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(AUTHORIZATION_REQUIRED_MSG));
    }
}
