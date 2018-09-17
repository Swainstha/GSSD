package com.example.swainstha.roomies;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by swainstha on 9/17/18.
 */

@Dao
public interface ExpensesDao {

    @Insert
    void insert(Expenses expenses);

    @Query("DELETE FROM expenses")
    void deleteAll();

    @Query("SELECT * from expenses ORDER BY id ASC")
    LiveData<List<Expenses>> getAllExpenses();
}
