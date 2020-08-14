package com.example.myviewdemo.jetpack.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/22 15:34
 * @UpdateUser:
 * @UpdateDate: 2020/6/22 15:34
 * @UpdateRemark:
 * @Version:
 */
@Entity
public class Word {


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "english_word")
    private String word;
    @ColumnInfo(name = "chinese_meaning")
    private String ChineseMeaning;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    Word() {
    }

    Word(String word, String chineseMeaning) {
        this.word = word;
        this.ChineseMeaning = chineseMeaning;
    }

    String getWord() {
        return word;
    }

    void setWord(String word) {
        this.word = word;
    }

    String getChineseMeaning() {
        return ChineseMeaning;
    }

    void setChineseMeaning(String chineseMeaning) {
        ChineseMeaning = chineseMeaning;
    }
}
