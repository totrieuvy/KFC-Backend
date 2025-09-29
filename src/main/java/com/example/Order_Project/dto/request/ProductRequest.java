package com.example.Order_Project.dto.request;

import com.example.Order_Project.entity.Category;

public class ProductRequest {

    private String name;

    private String description;

    private String image;

    private Double price;

    private int quantity;

    private Long category_id;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, String image, Double price, int quantity, Long category_id) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.category_id = category_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}
