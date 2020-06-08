package com.e.majorprojectfrontend;

public class ItemStock {
    String id,name,capacity,price,temperature,quality,dealerId;

    public ItemStock(String id, String name, String capacity, String price, String temperature, String quality, String dealerId) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.temperature = temperature;
        this.quality = quality;
        this.dealerId = dealerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
}
