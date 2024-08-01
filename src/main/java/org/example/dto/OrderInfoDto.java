package org.example.dto;

public class OrderInfoDto {
    private String name;
    private Boolean success;
    private OrderDto order;

    public OrderInfoDto(String name, Boolean success, OrderDto order) {
        this.name = name;
        this.success = success;
        this.order = order;
    }

    public OrderInfoDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    static class OrderDto {
        private Integer number;

        public OrderDto(Integer number) {
            this.number = number;
        }

        public OrderDto() {
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}
