package com.example.myviewdemo.jetpack.room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myviewdemo.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomBasicActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    WordViewModel wordViewModel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_basic);
        ButterKnife.bind(this);
        ViewModelProvider provider = new ViewModelProvider(this);
        wordViewModel = provider.get(WordViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        wordViewModel.getAllWords().observe(this, words -> {
            adapter.setAllWords(words);
            adapter.notifyDataSetChanged();
        });
    }


    @OnClick({R.id.buttonInsert, R.id.buttonClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonInsert:
                for (int i = 0; i < getEnglish().length; i++) {
                    wordViewModel.insertWords(new Word(getEnglish()[i], getChinese()[i]));
                }
                break;
            case R.id.buttonClear:
                wordViewModel.deleteAllWords();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q="
                + Objects.requireNonNull(wordViewModel.getAllWords().getValue()).get(position).getWord());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    public String[] getEnglish() {
        return new String[]{
                "hello",
                "world",
                "Android",
                "Google",
                "Studio",
                "Project",
                "Database",
                "Recycler",
                "View",
                "String",
                "Value",
                "Integer",
        };
    }

    public String[] getChinese() {
        return new String[]{
                "你好",
                "世界",
                "安卓系统",
                "谷歌公司",
                "工作室",
                "项目",
                "数据库",
                "回收站",
                "视图",
                "字符串",
                "价值",
                "整数类型",
        };
    }


}
