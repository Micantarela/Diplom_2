package org.example.dto;

import java.util.List;

public class OrderCreateDto {
    private List<String> ingredients;

    public OrderCreateDto(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrderCreateDto() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
