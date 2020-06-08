package com.e.majorprojectfrontend;

public class OrderHistoryItem {
    String orderid,itemnames,itemprices,totalprice,datecreated,dealer,orderstatus;

    public OrderHistoryItem(String orderid, String itemnames, String itemprices, String totalprice, String datecreated, String dealer, String orderstatus) {
        this.orderid = orderid;
        this.itemnames = itemnames;
        this.itemprices = itemprices;
        this.totalprice = totalprice;
        this.datecreated = datecreated;
        this.dealer = dealer;
        this.orderstatus = orderstatus;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getItemnames() {
        return itemnames;
    }

    public void setItemnames(String itemnames) {
        this.itemnames = itemnames;
    }

    public String getItemprices() {
        return itemprices;
    }

    public void setItemprices(String itemprices) {
        this.itemprices = itemprices;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }
}
