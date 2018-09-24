package com.example.swainstha.roomies;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by swainstha on 9/17/18.
 */


@Entity(tableName = "expenses")
public class Expenses {

    @PrimaryKey(autoGenerate = true)
    private int id;
    //private Date date;
    private String items;
    private int cost;

//    public Expenses(@NonNull Date date, @NonNull String items, @NonNull double cost) {
//        this.date = date;
//        this.items = items;
//        this.cost = cost;
//    }
public Expenses(@NonNull String items, @NonNull int cost) {
    this.items = items;
    this.cost = cost;
}

//    public Date getDate() {
//        return date;
//    }


    public void setId(int id) {
        this.id = id;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getItems() {
        return items;
    }

    public int getCost() {
        return cost;
    }

}
