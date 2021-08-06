package com.example.myviewdemo.kuaishou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myviewdemo.R

/**
 * 参考学习地址 https://mp.weixin.qq.com/s/3IAPd53rMOrLiIUDT520-w
 */
class MotionActivity : AppCompatActivity() {

    /**
     * 1、将ConstraintLayout升级到2.0或以上。

    2、将布局转化为MotionLayout。

    3、创建MotionScene文件并在MotionLayout的 app:layoutDescription属性中指明。

    4、在MotionScene文件中编辑<ConstrainSet>分别设置动画开始和结束时控件的状态。编辑<Transition>元素指明动画开始和结束对应的<ConstrainSet>是哪个。
    Transtion指定了动画要使用的ConstrainSet，动画的触发方式等。
    ConstrainSet描述了开始或结束时页面控件的状态。
    在MotionLayout标签添加一个新的属性showPaths=true,添加这个属性MotionLayout会为每个有动画的控件绘制一条轨迹线方便我们调试动画效果。
    <OnSwipe>标签表示拖动执行动画。motion:dragDirection="dragUp"表示向上边拖动执行动画。
    响应点击事件可以用<OnClick>标签
    我们在Transition下添加KeyFrameSet并在其中在添加两个KeyPosition。KeyPosition用来指定动画序列中特定时刻的位置。该属性用于调整默认的运动路径。

    其中motion:motionTarget表示受影响的控件id，iv_share和iv_like分别是分享和点赞的按钮id。

    motion:framePosition是这个关键帧的位置取值为1 到 99 之间的整数。这里取值50就是指动画进行到一半的位置。

    motion:percentX和motion:percentY是控件到达motion:framePosition点时的位置，是个float值。这两个属性的具体意义需要根据motion:keyPositionType的类型来定，其中包括parentRelative，deltaRelative，pathRelative这三种类型
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion)
    }
}