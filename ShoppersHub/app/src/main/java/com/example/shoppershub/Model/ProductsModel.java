package com.example.shoppershub.Model;

public class ProductsModel {
    int product_id, price;
    String image, name, description, category, size, colour;

    public ProductsModel() {
    }

    public ProductsModel(int product_id, String image, String name, String description, String category, String size, String colour, int price) {
        this.product_id = product_id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.category = category;
        this.size = size;
        this.colour = colour;
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
