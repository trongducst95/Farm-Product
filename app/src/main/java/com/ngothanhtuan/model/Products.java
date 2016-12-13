package com.ngothanhtuan.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MyPC on 10/29/2016.
 */

public class Products implements Parcelable{

    String ID_Prodc,name_Prodc, ID_Type;
    int Count, ID_User;
    byte[] img;
    Long Price;

    public Products() {

    }

    public Products(int count,int id_User, String ID_Prodc, String ID_Type, byte[] img, String name_Prodc, Long price) {
        Count = count;
        ID_User = id_User;
        this.ID_Prodc = ID_Prodc;
        this.ID_Type = ID_Type;
        this.img = img;
        this.name_Prodc = name_Prodc;
        Price = price;
    }

    protected Products(Parcel in) {
        ID_Prodc = in.readString();
        name_Prodc = in.readString();
        ID_Type = in.readString();
        Count = in.readInt();
        ID_User = in.readInt();
        img = in.createByteArray();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public String getID_Type() {
        return ID_Type;
    }

    public void setID_Type(String ID_Type) {
        this.ID_Type = ID_Type;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
    public String getID_Prodc() {
        return ID_Prodc;
    }

    public void setID_Prodc(String ID_Prodc) {
        this.ID_Prodc = ID_Prodc;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getName_Prodc() {
        return name_Prodc;
    }

    public void setName_Prodc(String name_Prodc) {
        this.name_Prodc = name_Prodc;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID_Prodc);
        dest.writeString(name_Prodc);
        dest.writeString(ID_Type);
        dest.writeInt(Count);
        dest.writeInt(ID_User);
        dest.writeByteArray(img);
    }

    @Override
    public String toString() {
        return "Products{" +
                "name_Prodc='" + name_Prodc + '\'' +
                '}';
    }
}
