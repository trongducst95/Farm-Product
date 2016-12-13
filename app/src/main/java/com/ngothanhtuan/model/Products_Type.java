package com.ngothanhtuan.model;

/**
 * Created by MyPC on 10/29/2016.
 */

public class Products_Type {

    private String ID_Type,name_type;

    public String getID_Type() {
        return ID_Type;
    }

    public void setID_Type(String ID_Type) {
        this.ID_Type = ID_Type;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public Products_Type() {
    }

    public Products_Type(String ID_Type, String name_type) {
        this.ID_Type = ID_Type;
        this.name_type = name_type;
    }

    @Override
    public String toString() {
        return name_type;
    }
}
