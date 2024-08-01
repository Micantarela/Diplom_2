package org.example.dto;

import java.util.List;

public class IngredientsResponseDto {

    private Boolean success;
    private List<IngredientDto> data;

    public IngredientsResponseDto(Boolean success, List<IngredientDto> data) {
        this.success = success;
        this.data = data;
    }

    public IngredientsResponseDto() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<IngredientDto> getData() {
        return data;
    }

    public void setData(List<IngredientDto> data) {
        this.data = data;
    }
}
