package com.example.myviewdemo.jetpack.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/22 16:11
 * @UpdateUser:
 * @UpdateDate: 2020/6/22 16:11
 * @UpdateRemark:
 * @Version:
 */
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTACE;

    synchronized static WordDatabase getWordDatabase(Context context) {
        if (INSTACE == null) {
            INSTACE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
//                    .fallbackToDestructiveMigration()//不保留现有数据，暴力修改，会清楚现有数据
//                    .addMigrations(MIGRADION_2_3)//保留现有数据，迁移数据库
                    .build();
        }
        return INSTACE;
    }

    public abstract WordDao getWordDao();

    /**
     * 加入新字段
     */
    static final Migration MIGRADION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE wrod ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };
    /**
     * 删除字段
     */
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY  NOT NULL ,english_word TEXT,chinese_meaning TEXT )");
            database.execSQL("INSERT INTO word_temp (id ,english_word,chinese_meaning) SELECT id,english_word,chinese_meaning FROM word");
            database.execSQL("DROP TABLE word");
            database.execSQL("ALTER TABLE word_temp RENAME TO word");
        }
    };
}
