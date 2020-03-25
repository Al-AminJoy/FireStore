package com.trustedoffer.firestore;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class TestModelClass {
   // private String key;
    private String title;
    private int price;
    private String key;
    private List<String> items;
    public TestModelClass() {

    }

    public TestModelClass(String title, int price,List<String> items) {
      //  this.key=key;
        this.title = title;
        this.price = price;
        this.items=items;

    }
    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getItems() {
        return items;
    }
}
