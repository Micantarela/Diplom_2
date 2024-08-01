package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.dto.IngredientsResponseDto;
import org.example.dto.OrderCreateDto;
import org.example.dto.UserLoginResponseDto;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class OrderCreateTest {

    public static final String INGREDIENTS_MUST_BE_PROVIDED_MSG = "Ingredient ids must be provided";

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
    @DisplayName("Create order with authorization")
    @Description("Создание заказа авторизованным пользователем")
    public void createOrderWithAuthorization() {
        UserLoginResponseDto userDto = TestUtils.loginDefaultUser().as(UserLoginResponseDto.class);
        IngredientsResponseDto ingredients = TestUtils.findIngredients();

        OrderCreateDto orderCreateDto = new OrderCreateDto(List.of(
            ingredients.getData().get(0).getId(),
            ingredients.getData().get(1).getId()
        ));

        TestUtils.createOrder(orderCreateDto, userDto.getAccessToken())
            .then()
            .statusCode(200)
            .and()
            .body("success", Matchers.equalTo(true))
            .body("name", Matchers.notNullValue())
            .body("order.number", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Create order without authorization")
    @Description("Создание заказа неавторизованным пользователем")
    public void createOrderWithoutAuthorization() {
        IngredientsResponseDto ingredients = TestUtils.findIngredients();

        OrderCreateDto orderCreateDto = new OrderCreateDto(List.of(
            ingredients.getData().get(0).getId(),
            ingredients.getData().get(1).getId()
        ));

        TestUtils.createOrder(orderCreateDto, null)
            .then()
            .statusCode(200)
            .and()
            .body("success", Matchers.equalTo(true))
            .body("name", Matchers.notNullValue())
            .body("order.number", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Create order without ingredients")
    @Description("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients() {
        UserLoginResponseDto userDto = TestUtils.loginDefaultUser().as(UserLoginResponseDto.class);

        OrderCreateDto orderCreateDto = new OrderCreateDto(Collections.emptyList());

        TestUtils.createOrder(orderCreateDto, userDto.getAccessToken())
            .then()
            .statusCode(400)
            .and()
            .body("success", Matchers.equalTo(false))
            .body("message", Matchers.equalTo(INGREDIENTS_MUST_BE_PROVIDED_MSG));
    }

    @Test
    @DisplayName("Create order with invalid ingredient hash")
    @Description("Создание заказа с несуществующим хешем ингредиента")
    public void createOrderWithInvalidIngredientHash() {
        UserLoginResponseDto userDto = TestUtils.loginDefaultUser().as(UserLoginResponseDto.class);
        IngredientsResponseDto ingredients = TestUtils.findIngredients();

        OrderCreateDto orderCreateDto = new OrderCreateDto(List.of(
            ingredients.getData().get(0).getId(),
            "lpkoinrthmrti123423"
        ));

        TestUtils.createOrder(orderCreateDto, userDto.getAccessToken())
            .then()
            .statusCode(500);
    }
}
