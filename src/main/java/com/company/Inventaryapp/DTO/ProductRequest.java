
package com.company.Inventaryapp.DTO;


public class ProductRequest {

    private String name;
    private Double price;

    // Constructores, getters y setters

    public ProductRequest() {
    }

    public ProductRequest(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    // Getters y setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
