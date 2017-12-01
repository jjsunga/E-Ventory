package com.example.mike.e_ventory;

public class ListItemProd {
    private int imageId;
    private int qty;
    private double price;
    private String name;
    private String vendor;
    private String desc;
    private String url;

    public ListItemProd(String name, double price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getProdName() {
        return name;
    }
    public void setProdName(String name) {
        this.name = name;
    }
    public String getvendor() {
        return vendor;
    }
    public void setVendor(String url) {
        this.vendor = vendor;
    }
}
