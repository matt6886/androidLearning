package com.example.chapter06.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chapter06.entity.BookInfo;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(BookInfo... book);

    @Delete
    void delete(BookInfo... book);

    @Query("DELETE FROM BookInfo")
    void deleteAll();

    @Update
    int update(BookInfo... book);

    @Query("SELECT * FROM BookInfo")
    List<BookInfo> queryAll();

    @Query("SELECT * FROM bookinfo WHERE name = :name ORDER BY id LIMIT 1")
    BookInfo queryByName(String name);
}
