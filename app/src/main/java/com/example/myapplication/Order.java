package com.example.myapplication;

public class Order {
    private final int id;
    private final String serviceName;
    private final double price;
    private final String status;
    private final String pricingType;
    private final double quantity;

    public Order(int id, String serviceName, double price, String status, String pricingType, double quantity) {
        this.id = id;
        this.serviceName = serviceName;
        this.price = price;
        this.status = status;
        this.pricingType = pricingType;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public String getPricingType() { return pricingType; }
    public double getQuantity() { return quantity; }
}
