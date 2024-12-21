package com.example.chapter06.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chapter06.dao.BookDao;
import com.example.chapter06.entity.BookInfo;

// entities indicates what tables there are
// versions indicates the version of database
// exportSchema indicates if we need to export the json strings of database
// default value is false, if true, we need to config the path of exporting json strings
@Database(entities = {BookInfo.class}, version = 1, exportSchema = true)
public abstract class BookDatabase extends RoomDatabase {
    // get the persisted objects in the database
    public abstract BookDao bookDao();
}
