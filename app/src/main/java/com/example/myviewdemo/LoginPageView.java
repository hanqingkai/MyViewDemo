package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 所有btn的selector都没写，效果自己添加
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/4 13:56
 * @UpdateUser:
 * @UpdateDate: 2020/6/4 13:56
 * @UpdateRemark:
 * @Version:
 */
public class LoginPageView extends LinearLayout {

    public static final int SIZE_VERIFY_CODE_DEFAULT = 4;
    @BindView(R.id.get_verify_code_btn)
    TextView getVerifyCodeBtn;
    @BindView(R.id.report_check_box)
    CheckBox mIsConfirm;
    @BindView(R.id.verify_code_input_box)
    EditText verifyCode;
    @BindView(R.id.num_key_pad)
    LoginKeyBoardView numKeyPad;
    @BindView(R.id.phone_num_input_box)
    EditText phoneNumInputBox;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    private int mMaincolor;
    private int mVerifySize = SIZE_VERIFY_CODE_DEFAULT;
    private boolean isPhoneNumOK = false;
    private boolean isAgreementOK = false;
    private boolean isVerifyCodeOK = false;

    public LoginPageView(Context context) {
        this(context, null);
    }

    public LoginPageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView();
        disableEditFocus2keypad();
        initEvent();
    }

    private void disableEditFocus2keypad() {
        phoneNumInputBox.setShowSoftInputOnFocus(false);
        verifyCode.setShowSoftInputOnFocus(false);
    }

    private void initEvent() {
        numKeyPad.setOnKeyPressListener(new LoginKeyBoardView.OnKeyPressListener() {
            @Override
            public void onKeyPressListener(int num) {
                //数字被点击
                //插入数字
                EditText focusEdit = getFocusEdit();
                if (focusEdit != null) {
                    Editable text = focusEdit.getText();
                    int index = focusEdit.getSelectionEnd();
                    text.insert(index, String.valueOf(num));
                }
            }

            @Override
            public void onBackPress() {
                //退格键被点击
                EditText focusEdit = getFocusEdit();
                if (focusEdit != null) {
                    Editable text = focusEdit.getText();
                    int index = focusEdit.getSelectionEnd();
                    if (index > 0)
                        text.delete(index - 1, index);
                }
            }
        });

        getVerifyCodeBtn.setOnClickListener(view -> {
            if (mLoginPageActionListener != null) {
                String phonenum = phoneNumInputBox.getText().toString().trim();
                mLoginPageActionListener.onGetVerifyCodeClick(phonenum);
            }

        });
        phoneNumInputBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //变化时验证电话号码是否正确
                //匹配电话号成功返回true
//                if (成功){...}
                isPhoneNumOK = true;
                upDateALLBtnState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        verifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isVerifyCodeOK = s.length() == 4;
                upDateALLBtnState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mIsConfirm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAgreementOK = isChecked;
            upDateALLBtnState();
        });
        loginBtn.setOnClickListener(view -> {

        });
    }

    private void upDateALLBtnState() {
        getVerifyCodeBtn.setEnabled(isPhoneNumOK);
        loginBtn.setEnabled(isPhoneNumOK && isAgreementOK && isVerifyCodeOK);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.login_page_view, this);
        ButterKnife.bind(view, this);
        if (mMaincolor != -1) {
            mIsConfirm.setTextColor(mMaincolor);
        }
        verifyCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(SIZE_VERIFY_CODE_DEFAULT)});
        upDateALLBtnState();
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginPageView);
        mMaincolor = typedArray.getColor(R.styleable.LoginPageView_mainColor, -1);
        mVerifySize = typedArray.getInteger(R.styleable.LoginPageView_verifySize, SIZE_VERIFY_CODE_DEFAULT);
        typedArray.recycle();
    }

    /**
     * 获取当前有焦点的输入框
     *
     * @return null or edittext instance.
     */
    private EditText getFocusEdit() {
        View view = this.findFocus();
        if (view instanceof EditText) {
            return (EditText) view;
        }
        return null;
    }

    public int getmVerifySize() {
        return mVerifySize;
    }

    public void setmVerifySize(int mVerifySize) {
        this.mVerifySize = mVerifySize;
    }

    public int getmMaincolor() {
        return mMaincolor;
    }

    public void setmMaincolor(int mMaincolor) {
        this.mMaincolor = mMaincolor;
    }

    private OnLoginPageActionListener mLoginPageActionListener;

    public void setOnLoginPageActionListener(OnLoginPageActionListener loginPageActionListener) {
        mLoginPageActionListener = loginPageActionListener;
    }

    interface OnLoginPageActionListener {
        void onGetVerifyCodeClick(String phoneNum);

        void onOpenAgreementClick();

        void onConfirmClick(String verifyCode, String phoneNum);
    }
}
