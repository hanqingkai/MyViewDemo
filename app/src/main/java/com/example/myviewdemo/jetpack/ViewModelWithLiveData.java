package com.example.myviewdemo.jetpack;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.security.Key;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/17 14:39
 * @UpdateUser:
 * @UpdateDate: 2020/6/17 14:39
 * @UpdateRemark:
 * @Version:
 */
public class ViewModelWithLiveData extends ViewModel {


    private final SavedStateHandle handle;
    private MutableLiveData<Integer> bScore;
    private int aBack;
    private int bBack;
    public static final String KEY_NUMBER = "key_number";

    public ViewModelWithLiveData(SavedStateHandle handle) {
        this.handle = handle;
    }

    public MutableLiveData<Integer> getAScore() {
        if (!handle.contains(KEY_NUMBER)) {
            handle.set(KEY_NUMBER, 0);
        }
        return handle.getLiveData(KEY_NUMBER);
    }

    public MutableLiveData<Integer> getBScore() {
        if (bScore == null) {
            bScore = new MutableLiveData<>();
            bScore.setValue(0);
        }
        return bScore;
    }

    public void addAScore(int n) {
        aBack = getAScore().getValue();
        bBack = bScore.getValue();
        getAScore().setValue(getAScore().getValue() + n);
    }

    public void addBScore(int n) {
        aBack = getAScore().getValue();
        bBack = bScore.getValue();
        bScore.setValue(bScore.getValue() + n);
    }

    public void revokeScore() {
        getAScore().setValue(aBack);
        bScore.setValue(bBack);
    }

    public void resetScore() {
        getAScore().setValue(0);
        if (bScore != null) {
            bScore.setValue(0);
        }
        aBack = getAScore().getValue();
        bBack = bScore.getValue();
    }
}
