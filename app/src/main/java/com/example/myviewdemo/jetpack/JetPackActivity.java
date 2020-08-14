package com.example.myviewdemo.jetpack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.myviewdemo.R;
import com.example.myviewdemo.databinding.ActivityJetPackBinding;

public class JetPackActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJetPackBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_jet_pack);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        ViewModelWithLiveData viewModelWithLiveData = viewModelProvider.get(ViewModelWithLiveData.class);
        binding.setScoreData(viewModelWithLiveData);
        binding.setLifecycleOwner(this);
    }

}
