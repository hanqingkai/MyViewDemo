package com.example.myviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/3 17:00
 * @UpdateUser:
 * @UpdateDate: 2020/6/3 17:00
 * @UpdateRemark:
 * @Version:
 */
public class LoginKeyBoardView extends LinearLayout {
    private static final String TAG = "LoginKeyBoard";

    public LoginKeyBoardView(Context context) {
        this(context, null);
    }

    public LoginKeyBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = LayoutInflater.from(context).inflate(R.layout.num_key_pad, this);
        ButterKnife.bind(inflate, this);
    }
    @OnClick({R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4, R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9, R.id.num_0, R.id.back})
    public void onViewClicked(View view) {
        if (mOnKeyPressListener == null) {
            Log.d(TAG, "mOnKeyPressListener is null need to callback... ");
            return;
        }
        int viewId = view.getId();
        if (viewId == R.id.back) {
            mOnKeyPressListener.onBackPress();
        } else {
            String text = ((TextView) view).getText().toString();
            Log.d(TAG, "click  text = = > " + text);
            mOnKeyPressListener.onKeyPressListener(Integer.parseInt(text));
        }

    }

    private OnKeyPressListener mOnKeyPressListener;

    public void setOnKeyPressListener(OnKeyPressListener listener) {
        mOnKeyPressListener = listener;
    }

    public interface OnKeyPressListener {
        void onKeyPressListener(int num);

        void onBackPress();
    }
}
