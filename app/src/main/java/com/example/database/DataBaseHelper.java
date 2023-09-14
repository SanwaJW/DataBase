package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String customer_ID = "ID";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement= "CREATE TABLE " + CUSTOMER_TABLE + " (" + customer_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CUSTOMER_NAME + " TEXT, " + CUSTOMER_AGE + " INT, " + ACTIVE_CUSTOMER + " BOOL)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addOne(CustomerModel customerModel){

        ContentValues cv=new ContentValues();
        cv.put(CUSTOMER_NAME,customerModel.getName());
        cv.put(CUSTOMER_AGE,customerModel.getAge());
        cv.put(ACTIVE_CUSTOMER,customerModel.isActive());

        SQLiteDatabase db=this.getWritableDatabase();
        long insert =db.insert(CUSTOMER_TABLE,null,cv);
        if(insert==-1){
            return false;
        }else {
            return  true;
        }

    }

    public List<CustomerModel> getEveryone(){
        List <CustomerModel>customerData=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM " + CUSTOMER_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                int customerID = cursor.getInt (  0) ;
                String customerName =cursor.getString( 1) ;
                int customerAge =cursor.getInt ( 2) ;
                boolean customerActive= cursor.getInt( 3)== 1 ? true: false;

                CustomerModel customerModel=new CustomerModel(customerID,customerName,customerAge,customerActive);
                customerData.add(customerModel);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return customerData;
    }

    public  boolean deleteOne(CustomerModel customerModel){
        SQLiteDatabase db=this.getWritableDatabase();
        String query ="DELETE FROM " + CUSTOMER_TABLE + " WHERE "+ customer_ID + " = " +customerModel.getId();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

}
