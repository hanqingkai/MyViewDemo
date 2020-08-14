package com.example.myviewdemo.jetpack.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/22 15:48
 * @UpdateUser:
 * @UpdateDate: 2020/6/22 15:48
 * @UpdateRemark:
 * @Version:
 */
@Dao
public interface WordDao {

    @Insert
    void insertWords(Word... words);

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWords();


}
