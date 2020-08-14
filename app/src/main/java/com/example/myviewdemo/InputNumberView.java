package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Objects;


/**
 * @Description: 想要啥功能自己再定义
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/3 10:14
 * @UpdateUser:
 * @UpdateDate: 2020/6/3 10:14
 * @UpdateRemark:
 * @Version:
 */
public class InputNumberView extends RelativeLayout {

    private static final String TAG = "InputNumberView";
    private int max;
    private int min;
    private int mCurrentNumber = 0;
    private View tvMinus, tvPlus;
    private EditText etValue;
    private OnNumberChangeListener onNumberChangeListener;
    private int defValue = 0;
    private int step;
    private int valueSize;
    private int btnBg;

    private boolean disable;

    public InputNumberView(Context context) {
        this(context, null);
    }

    public InputNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取属性
        initAttrs(context, attrs);
        initView(context);
        setUpEvent();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputNumberView);
        max = typedArray.getInt(R.styleable.InputNumberView_maxValue, 0);
        min = typedArray.getInt(R.styleable.InputNumberView_minValue, 0);
        defValue = typedArray.getInt(R.styleable.InputNumberView_defValue, 1);
        mCurrentNumber = defValue;
        step = typedArray.getInt(R.styleable.InputNumberView_step, 1);
        valueSize = typedArray.getDimensionPixelSize(R.styleable.InputNumberView_valueSize, 13);
        btnBg = typedArray.getResourceId(R.styleable.InputNumberView_btnBackground, -1);
        disable = typedArray.getBoolean(R.styleable.InputNumberView_disable, true);
        typedArray.recycle();
    }

    private void setUpEvent() {
        tvMinus.setOnClickListener(v -> {
            tvPlus.setEnabled(true);
            mCurrentNumber -= step;
            if (min != 0 && mCurrentNumber <= min) {
                Log.d(TAG, "不能小于最小值");
                mCurrentNumber = min;
                v.setEnabled(false);
            }

            updateText();
        });
        tvPlus.setOnClickListener(v -> {
            mCurrentNumber += step;
            tvMinus.setEnabled(true);
            if (max != 0 && mCurrentNumber >= max) {
                Log.d(TAG, "不能大于最大值");
                mCurrentNumber = max;
                v.setEnabled(false);
            }
            updateText();
        });
    }

    private void initView(Context context) {
        //LayoutInflater.from(context).inflate(R.layout.input_number_view, this, true);
        //LayoutInflater.from(context).inflate(R.layout.input_number_view, this);
        View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this, false);
        //三种写法均可
        addView(view);
        tvMinus = this.findViewById(R.id.tv_minus);
        etValue = this.findViewById(R.id.et_value);
        tvPlus = this.findViewById(R.id.tv_plus);
        updateText();
        tvMinus.setEnabled(disable);
        tvPlus.setEnabled(disable);
    }


    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public View getTvMinus() {
        return tvMinus;
    }

    public void setTvMinus(View tvMinus) {
        this.tvMinus = tvMinus;
    }

    public EditText getEtValue() {
        return etValue;
    }

    public void setEtValue(EditText etValue) {
        this.etValue = etValue;

    }

    public int getDefValue() {
        return defValue;
    }

    public void setDefValue(int defValue) {
        this.defValue = defValue;
        mCurrentNumber = defValue;
        updateText();
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getValueSize() {
        return valueSize;
    }

    public void setValueSize(int valueSize) {
        this.valueSize = valueSize;
    }

    public int getBtnBg() {
        return btnBg;
    }

    public void setBtnBg(int btnBg) {
        this.btnBg = btnBg;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public void setNumber(int value) {
        this.mCurrentNumber = value;
        updateText();
    }

    public int getNumber() {
        return mCurrentNumber;
    }

    private void updateText() {
        etValue.setText(String.valueOf(mCurrentNumber));
        if (onNumberChangeListener != null)
            onNumberChangeListener.onNumberChange(mCurrentNumber);
    }

    interface OnNumberChangeListener {
        void onNumberChange(int value);
    }

    public void setOnNumberChangeListener(OnNumberChangeListener listener) {
        this.onNumberChangeListener = listener;
    }
}
