package com.example.myviewdemo.jetpack.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/24 14:47
 * @UpdateUser:
 * @UpdateDate: 2020/6/24 14:47
 * @UpdateRemark:
 * @Version:
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder> {
    private List<Word> allWords = new ArrayList<>();


      void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_normal, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        Word word = allWords.get(position);
        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvEnglish.setText(word.getWord());
        holder.tvChinese.setText(word.getChineseMeaning());
        holder.itemView.setOnClickListener(view -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    static class MyViewholder extends RecyclerView.ViewHolder {

          TextView tvNumber;
          TextView tvEnglish;
          TextView tvChinese;

          MyViewholder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvEnglish = itemView.findViewById(R.id.tv_english);
            tvChinese = itemView.findViewById(R.id.tv_chinese);
        }
    }

    private OnItemClickListener mItemClickListener = null;

      void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}
