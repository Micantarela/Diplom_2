package org.example.dto;

public class IngredientDto {
    private String _id;

    public IngredientDto(String _id) {
        this._id = _id;
    }

    public IngredientDto() {
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }
}
