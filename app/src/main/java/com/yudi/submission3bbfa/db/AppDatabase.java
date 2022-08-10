package com.yudi.submission3bbfa.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yudi.submission3bbfa.model.UserModel;

@Database(entities = {UserModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}
