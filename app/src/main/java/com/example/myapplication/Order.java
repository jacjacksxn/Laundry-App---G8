package com.example.myapplication;
public class Order {
    private final int id;
    private final String serviceName;
    private final double price;
    private final String status;

    public Order(int id, String serviceName, double price, String status) {
        this.id = id;
        this.serviceName = serviceName;
        this.price = price;
        this.status = status;
    }

    public int getId() { return id; }
    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
}
