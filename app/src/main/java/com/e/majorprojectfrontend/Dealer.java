package com.e.majorprojectfrontend;

import java.io.Serializable;

public class Dealer {

    String name,address,imageURL,id,items,email;

    public Dealer(String name, String address, String imageURL, String id,String email) {
        this.name = name;
        this.address = address;
        this.imageURL = imageURL;
        this.id = id;
        this.email=email;
    }
    public Dealer(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
