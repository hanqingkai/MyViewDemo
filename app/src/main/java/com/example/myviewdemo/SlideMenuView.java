package com.example.myviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * @Description:
 * @Author: 韩庆凯
 * @CreateDate: 2020/6/10 8:54
 * @UpdateUser:
 * @UpdateDate: 2020/6/10 8:54
 * @UpdateRemark:
 * @Version:
 */
public class SlideMenuView extends ViewGroup {

    private int mFunction;
    private View mContentView;
    private View mEditView;
    private OnEditClickListener mOnEditClickListener = null;
    private TextView tvRead;
    private TextView tvDel;
    private TextView tvTop;
    private float downX;
    private float downY;
    private Scroller mScroller;
    private float interceptX;
    private float interceptY;

    public SlideMenuView(Context context) {
        this(context, null);
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideMenuView);
        mFunction = typedArray.getInt(R.styleable.SlideMenuView_function, 0x30);
        typedArray.recycle();
        mScroller = new Scroller(context);
    }

    /**
     * 布局加载以后可以获取到包裹的子view
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new IllegalArgumentException("no more then one child.");
        }
        mContentView = getChildAt(0);
        mEditView = LayoutInflater.from(getContext()).inflate(R.layout.item_slide_action, this, false);
        initEditView();
        addView(mEditView);
    }

    private void initEditView() {
        tvRead = mEditView.findViewById(R.id.tv_read);
        tvDel = mEditView.findViewById(R.id.tv_del);
        tvTop = mEditView.findViewById(R.id.tv_top);
        tvRead.setOnClickListener(view -> {
            if (mOnEditClickListener != null) {
                //先关闭后点击
                close();
                mOnEditClickListener.onReadClick();
            }
            ;
        });
        tvDel.setOnClickListener(view -> {
            if (mOnEditClickListener != null) {
                close();
                mOnEditClickListener.onDeleteClick();
            }
            ;
        });
        tvTop.setOnClickListener(view -> {
            if (mOnEditClickListener != null) {
                close();
                mOnEditClickListener.onTopClick();
            }
            ;
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //测量第一个子view,也就是内容部分
        //宽度：和父控件一样宽。 高度：三种情况，指定大小就获取大小，直接测量
        //包裹内容  at_most  填充内容 match_parent 给他大小
        //获取内容view的高度
        LayoutParams mContentViewLayoutParams = mContentView.getLayoutParams();
        int contentViewHeight = mContentViewLayoutParams.height;
        int contentHeightMeaseureSpec;
        if (contentViewHeight == LayoutParams.MATCH_PARENT) {
            contentHeightMeaseureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        } else if (contentViewHeight == LayoutParams.WRAP_CONTENT) {
            contentHeightMeaseureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
        } else {
            //指定大小
            contentHeightMeaseureSpec = MeasureSpec.makeMeasureSpec(contentViewHeight, MeasureSpec.EXACTLY);
        }
        mContentView.measure(widthMeasureSpec, contentHeightMeaseureSpec);
        //测量编辑部分
        //高度与内容部分相同
        int contentViewMeasereHeight = mContentView.getMeasuredHeight();
        //宽度 为整个宽度的3/4 
        int editWidth = widthSize * 3 / 4;
        mEditView.measure(MeasureSpec.makeMeasureSpec(editWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(contentViewMeasereHeight, MeasureSpec.EXACTLY));
        //测量自己
        //宽度=总和   高度和内容高度一样
        setMeasuredDimension(widthSize, contentViewMeasereHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //布局内容
//        int contentLeft = -800;
        int contentRight = contentLeft + mContentView.getMeasuredWidth();
        int contentTop = 0;
        int contentBottom = contentTop + mContentView.getMeasuredHeight();
        mContentView.layout(contentLeft, contentTop, contentRight, contentBottom);
        //布局 编辑部分
        int editLeft = contentRight;
        int editTop = contentTop;
        int editRight = editLeft + mEditView.getMeasuredWidth();
        int editBottom = editTop + mEditView.getMeasuredHeight();
        mEditView.layout(editLeft, editTop, editRight, editBottom);
    }

    private int contentLeft = 0;
    private boolean isOpen = false;
    private Direction mCurrentDirection = Direction.NONE;

    enum Direction {
        LEFT, RIGHT, NONE;
    }

    //mDuration  走完mEditView的宽度的*4/5需要的时间
    private int mMaxDurciton = 800;
    private int mMinDurciton = 300;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        选择性拦截
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                interceptX = event.getX();
                interceptY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                if (Math.abs(x - interceptX) > 0) {
                    //给自己消费，不向下传递给子view
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:


                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getRawX();
                float moveY = event.getRawY();
                int dx = (int) (moveX - downX);
                //确定移动方向
                if (dx > 0) mCurrentDirection = Direction.RIGHT;
                else mCurrentDirection = Direction.LEFT;

//                contentLeft += dx;
//                requestLayout();
                //scrollx:view的左上角x坐标距离原点的距离
                int scrollX = getScrollX();
                int resultX = -dx + scrollX;
                if (resultX <= 0) {
                    scrollTo(0, 0);
                } else if (resultX > mEditView.getMeasuredWidth()) {
                    scrollTo(mEditView.getMeasuredWidth(), 0);
                } else {
                    scrollBy(-dx, 0);
                }
                downX = moveX;
                downY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getRawX();
                float upY = event.getRawY();
                //添加抬起后的效果
                int hasBeenScroll = getScrollX();
                int mEditViewWidth = mEditView.getMeasuredWidth();
                if (isOpen) {
                    //打开状态
                    if (mCurrentDirection == Direction.RIGHT) {
                        //向左滑动 判断滑动都距离
                        if (hasBeenScroll <= mEditViewWidth * 4 / 5) {
                            close();
                        } else {
                            open();
                        }
                    } else if (mCurrentDirection == Direction.LEFT) {
                        open();
                    }
                } else {
                    //关闭状态
                    if (mCurrentDirection == Direction.LEFT) {
                        //向左滑动 判断滑动都距离
                        if (hasBeenScroll > mEditViewWidth / 5) {
                            open();
                        } else {
                            close();
                        }
                    } else if (mCurrentDirection == Direction.RIGHT) {
                        //向右滑动 直接关闭
                        close();
                    }
                }
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            //滑动到指定位置即可
            scrollTo(currX, 0);
        }
    }


    public void close() {
        //                    scrollTo(0, 0);
        int dx = -getScrollX();
        int duration = (int) (dx / (mEditView.getMeasuredWidth() * 4 / 5f) * mMaxDurciton);
        int absDur = Math.abs(duration);
        if (absDur < mMinDurciton) {
            absDur = mMinDurciton;
        }
        mScroller.startScroll(getScrollX(), 0, dx, 0, absDur);
        isOpen = false;
        invalidate();
    }


    public void open() {
        //                    scrollTo(mEditView.getMeasuredWidth(), 0);
        int dx = mEditView.getMeasuredWidth() - getScrollX();
        int duration = (int) (dx / (mEditView.getMeasuredWidth() * 4 / 5f) * mMaxDurciton);
        int absDur = Math.abs(duration);
        if (absDur < mMinDurciton) {
            absDur = mMinDurciton;
        }
        mScroller.startScroll(getScrollX(), 0, dx, 0, absDur);
        isOpen = true;
        invalidate();
    }

    public void setOnEditClickListener(OnEditClickListener onEditClickListener) {
        mOnEditClickListener = onEditClickListener;
    }

    public interface OnEditClickListener {
        void onReadClick();

        void onDeleteClick();

        void onTopClick();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
