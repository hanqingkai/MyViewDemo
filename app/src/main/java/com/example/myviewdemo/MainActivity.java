package com.example.myviewdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ColorTrackTextView xiaoming;
    @BindView(R.id.flow_layout)
    FlowLayout flowLayout;
    @BindView(R.id.slideMenu)
    SlideMenuView slideMenu;
    @BindView(R.id.shapeView)
    ShapeView shapeView;
    @BindView(R.id.letterSlideBar)
    LetterSlideBar letterSlideBar;
    @BindView(R.id.letterSlideBarShow)
    TextView letterSlideBarShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initQQStepView();
        xiaoming = findViewById(R.id.xiaoming);
        initInputNumberView();
//        tologinpage();

        initFlowLayout();
        initSlideMenuView();
        inintProgressBar();
        initShapeView();
        initSlideBar();
        initMoveAxises();
    }

    private void initSlideBar() {
        letterSlideBar.setOnLetterTouchListener(letter -> {
            if (TextUtils.isEmpty(letter)) {
                letterSlideBarShow.setVisibility(View.GONE);
                return;
            }
            letterSlideBarShow.setText(letter);
            letterSlideBarShow.setVisibility(View.VISIBLE);
        });
    }

    private void initShapeView() {
        new Thread(() -> {
            while (true) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shapeView.exCheange();
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void inintProgressBar() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setmMax(4000);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 4000);
        animator.setDuration(2000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                progressBar.setmProgress(progress);
            }
        });
    }

    private void initSlideMenuView() {
        slideMenu.setOnEditClickListener(new SlideMenuView.OnEditClickListener() {
            @Override
            public void onReadClick() {
                ToastUtils.showShort("read");
            }

            @Override
            public void onDeleteClick() {
                ToastUtils.showShort("delete");
            }

            @Override
            public void onTopClick() {
                ToastUtils.showShort("top");
            }
        });
    }

    private void initFlowLayout() {
        List<String> list = new ArrayList<>();
        list.add("贫僧自东土大唐而来");
        list.add("鲲");
        list.add("土地公");
        list.add("白龙马蹄朝西");
        list.add("高老庄我的家");
        list.add("流沙河我爱你");
        list.add("花果山的葡萄大有多啊");
        list.add("师傅被妖怪抓走了");
        list.add("大王叫我来巡山");
        list.add("奔波霸霸波奔");
        flowLayout.setData(list);
        flowLayout.setOnFlowLayoutTabClickListener(ToastUtils::showShort);
    }

    public void tologinpage() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void initInputNumberView() {
        InputNumberView inputnumber = findViewById(R.id.inputnumber);
        inputnumber.setOnNumberChangeListener(value -> {
            Log.i(TAG, "value=" + value);
        });
    }

    /**
     * QQStepView
     */
    private void initQQStepView() {
        QQStepView stepView = findViewById(R.id.stepView);
        stepView.setStepMax(4000);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            float currentStep = (float) animation.getAnimatedValue();
            stepView.setCurrentStep((int) currentStep);
        });
        valueAnimator.start();
    }


    /**
     * ColorTrackTextView
     */
    public void leftToRight(View view) {
        xiaoming.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            float currentStep = (float) animation.getAnimatedValue();
            xiaoming.setCurrentProgress(currentStep);
        });
        valueAnimator.start();
    }

    /**
     * ColorTrackTextView
     */
    public void rightToLeft(View view) {
        xiaoming.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            float currentStep = (float) animation.getAnimatedValue();
            xiaoming.setCurrentProgress(currentStep);
        });
        valueAnimator.start();
    }

    private void initMoveAxises() {
        MyMoveAxisesView myMoveAxisesView = findViewById(R.id.myView);
        getLifecycle().addObserver(myMoveAxisesView);
    }
}
