package com.example.pojo;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = -4912851169538491158L;

    private Long id;

    private String productname;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
