package com.example.baikiemtra;

public class Product {
    private String name;
    private int price;
    private String description;
    private int imageRes;

    public Product(String name, int price, String description, int imageRes) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageRes = imageRes;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
    public int getImageRes() { return imageRes; }
}