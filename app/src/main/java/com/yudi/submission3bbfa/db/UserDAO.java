package com.yudi.submission3bbfa.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yudi.submission3bbfa.model.UserModel;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM favorite ORDER BY id DESC")
    List<UserModel> getAll();

    @Query("SELECT * FROM favorite WHERE login= :username")
    UserModel getUserByUsername(String username);

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    Cursor selectAll();

    @Insert
    void insertAll(UserModel... users);

    @Query("DELETE FROM favorite WHERE id = :uid")
    void deleteByUsername(int uid);


}
