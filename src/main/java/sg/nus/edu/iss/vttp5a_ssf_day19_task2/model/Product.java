package sg.nus.edu.iss.vttp5a_ssf_day19_task2.model;

import java.time.LocalDate;

public class Product {
    private Integer id;
    private String title;
    private String description;
    private Integer price;
    private double discountPercentage;
    private double rating;
    private Integer stock;
    private String brand;
    private String category;
    private LocalDate dated;
    private Integer buy;
    
    public Product() {
    }

    public Product(Integer id, String title, String description, Integer price, double discountPercentage, double rating,
            Integer stock, String brand, String category, LocalDate dated, Integer buy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.brand = brand;
        this.category = category;
        this.dated = dated;
        this.buy = buy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDated() {
        return dated;
    }

    public void setDated(LocalDate dated) {
        this.dated = dated;
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    public boolean canProductBeBought(){
        return this.stock > this.buy;
    }
}
