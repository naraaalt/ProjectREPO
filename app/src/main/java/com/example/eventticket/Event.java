package com.example.eventticket;

import java.io.Serializable;

public class Event implements Serializable {
    private int id;
    private String name;
    private String place;
    private String description;
    private String date;
    private double price;

    // Constructor
    public Event(int id, String name, String place, String description, String date, double price)  {
        this.id = id;
        this.name = name;
        this.place = place;
        this.description = description;
        this.date = date;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // To String Method
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                '}';
    }
}

