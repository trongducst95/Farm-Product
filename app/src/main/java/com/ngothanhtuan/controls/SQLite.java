package com.ngothanhtuan.controls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ngothanhtuan.model.Products;
import com.ngothanhtuan.model.Products_Type;
import com.ngothanhtuan.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 10/29/2016.
 */

public class SQLite extends SQLiteOpenHelper {
    public SQLite(Context context) {
        super(context, "Farm_Manager", null, 1);
    }

  /*  public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }*/

    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS User(" +
                "ID INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "IP CHAR  UNIQUE NOT NULL," +
                "Pass CHAR  NOT NULL" +
                ");" );
        db.execSQL("CREATE TABLE IF NOT EXISTS Product (" +
                "    ID_Product   CHAR    PRIMARY KEY" +
                "                         UNIQUE" +
                "                         NOT NULL," +
                "    Name_Product NCHAR   NOT NULL," +
                "    ID_TypeP      CHAR" +
                "                         NOT NULL" +
                "                         REFERENCES Type (ID_Type)," +
                "    IMG          BLOB," +
                "    Price        LONG," +
                "    Count        INTEGER," +
                "    ID_USER      INTEGER  REFERENCES User (ID)" +
                ");" );
        db.execSQL("CREATE TABLE IF NOT EXISTS Type (\n" +
                "    ID_Type   CHAR  PRIMARY KEY\n" +
                "                    NOT NULL,\n" +
                "    Name_Type NCHAR NOT NULL,\n" +
                "    ID_User   INTEGER  REFERENCES User (ID) \n" +
                ");\n");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

   /*===============================================================================================
   * USER
   * ===============================================================================================*/
    public void addUsers(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("IP", user.getIP());
        values.put("Pass",user.getPassword());

        db.insert("User",null,values); //Insert 1 dòng data vào table
        db.close();

    }


    public User getUser(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("User",null,"IP" + "=?",new String[] {String.valueOf(username)},null,null,null);

        User user = new User();

        if (cursor != null && cursor.moveToFirst()){
            user.setIP(cursor.getString(1));
            user.setPassword(cursor.getString(2));
        }
        return user;
    }

    public int getID(String IP){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("User",null,"IP" + "=?",new String[] {IP},null,null,null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Boolean Test_IP(String IP){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("User",null,"IP" + "=?",new  String[] {String.valueOf(IP)},null,null,null,null);

        if (cursor != null && cursor.moveToFirst())
            return true;
        return false;
    }

/*======================================================================================================================
* PRODUCT
* ======================================================================================================================*/
    public void addProduct(Products products,int User_ID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ID_Product", products.getID_Prodc());
        values.put("Name_Product", products.getName_Prodc());
        values.put("ID_TypeP", products.getID_Type());
        values.put("IMG", products.getImg());
        values.put("Price", products.getPrice());
        values.put("Count", products.getCount());
        values.put("ID_USER",User_ID);

        db.insert("Product", null, values); //Insert 1 dòng data vào table
        db.close();
    }

    //Test ID
    public Boolean Test_ID(String ID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Product",null,"ID_USER" + "=?",new  String[] {String.valueOf(ID)},null,null,null,null);

        if (cursor != null && cursor.moveToFirst())
            return true;
        return false;
    }

    public String getPr(int a){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Product",null,"ID_User" + "=?",new String[] {String.valueOf(a)},null,null,null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    //get All Product
    public ArrayList<Products> getAllProduct(int user_id){
        ArrayList<Products> productsList = new ArrayList<Products>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Product",null,"ID_USER" + "=?",new String[] {String.valueOf(user_id)},null,null,null);

        if (cursor.moveToFirst()){
            do {
                Products products = new Products();
                products.setID_Prodc(cursor.getString(0));
                products.setName_Prodc(cursor.getString(1));
                products.setID_Type(cursor.getString(2));
                products.setImg(cursor.getBlob(3));
                products.setPrice(Long.parseLong(cursor.getString(4)));
                products.setCount(cursor.getInt(5));
                products.setID_User(cursor.getInt(6));

                productsList.add(products);
            }while(cursor.moveToNext());
        }
        return  productsList;
    }

    public void updateDataProduct(Products products,String ID_pr){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name_Product", products.getName_Prodc());
        values.put("IMG", products.getImg());
        values.put("Price", products.getPrice());
        values.put("Count", products.getCount());
        db.update("Product",values,"ID_Product=?",new String[]{(ID_pr)});
        db.close();
    }

    public void deleteProduct(Products products){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Product","ID_Product" + "= ?",new String[]{String.valueOf(products.getID_Prodc())});
        db.close();
    }

    /*==================================================================================================
    *TYPE
    * ==================================================================================================*/
    public void addType(Products_Type type,int User_ID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ID_Type",type.getID_Type());
        values.put("Name_Type",type.getName_type());
        values.put("ID_USER",User_ID);

        db.insert("Type", null, values); //Insert 1 dòng data vào table
        db.close();
    }
    //Test add Type
    public String getType(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Type",null,"ID_Type" + "=?",new String[] {name},null,null,null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    //get All Type
    public ArrayList<Products_Type> getAllType(int user_id){
        ArrayList<Products_Type> typeList = new ArrayList<Products_Type>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Type",null,"ID_User" + "=?",new String[] {String.valueOf(user_id)},null,null,null);

        if (cursor.moveToFirst()){
            do {
                Products_Type type = new Products_Type(cursor.getString(0),cursor.getString(1));
                typeList.add(type);
            }while(cursor.moveToNext());
        }
        return typeList;
    }
    public void deleteType(Products_Type products_type){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Type","ID_type" + "= ?",new String[]{String.valueOf(products_type.getID_Type())});
        db.close();
    }

    public void updateType(Products_Type products_type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_Type", products_type.getID_Type());
        values.put("Name_Type", products_type.getName_type());
        db.update("Type",values,"ID_Type=?",new String[]{String.valueOf(products_type.getID_Type())});
        db.close();
    }

    public Boolean Test_Type(String ID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Type",null,"ID_Type" + "=?",new  String[] {String.valueOf(ID)},null,null,null,null);

        if (cursor != null && cursor.moveToFirst())
            return true;
        return false;
    }
}
