package com.example.myviewdemo.jetpack.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/24 11:06
 * @UpdateUser:
 * @UpdateDate: 2020/6/24 11:06
 * @UpdateRemark:
 * @Version:
 */
public class WordViewModel extends AndroidViewModel {


    private WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);

    }
    LiveData<List<Word>> getAllWords() {
        return wordRepository.getAllWords();
    }

    void insertWords(Word... words) {
        wordRepository.insertWords(words);
    }

    void updateWords(Word... words) {
        wordRepository.updateWords(words);
    }

    void deleteWords(Word... words) {
        wordRepository.deleteWords(words);
    }

    void deleteAllWords() {
        wordRepository.deleteAllWords();
    }
}
