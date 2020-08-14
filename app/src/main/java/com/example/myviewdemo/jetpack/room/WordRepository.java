package com.example.myviewdemo.jetpack.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/24 13:41
 * @UpdateUser:
 * @UpdateDate: 2020/6/24 13:41
 * @UpdateRemark:
 * @Version:
 */
class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getWordDatabase(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        allWords = wordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    void insertWords(Word... words) {
        new InsertWordsTask(wordDao).execute(words);
    }

    void updateWords(Word... words) {
        new UpdateWordsAsyncTask(wordDao).execute(words);
    }

    void deleteWords(Word... words) {
        new DeleteWordsAysncTask(wordDao).execute(words);
    }

    void deleteAllWords() {
        new DeleteAllWordsAysncTask(wordDao).execute();
    }

    static class InsertWordsTask extends AsyncTask<Word, Void, Void> {
        WordDao wordDao;

        InsertWordsTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    static class UpdateWordsAsyncTask extends AsyncTask<Word, Void, Void> {
        WordDao wordDao;

        UpdateWordsAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    static class DeleteWordsAysncTask extends AsyncTask<Word, Void, Void> {
        WordDao wordDao;

        DeleteWordsAysncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    static class DeleteAllWordsAysncTask extends AsyncTask<Void, Void, Void> {
        WordDao wordDao;

        DeleteAllWordsAysncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }

}
