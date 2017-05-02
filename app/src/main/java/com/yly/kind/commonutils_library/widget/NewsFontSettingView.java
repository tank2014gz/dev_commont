package com.yly.kind.commonutils_library.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class NewsFontSettingView extends View {

    public static final int TEXT_SIZE_SMALL = 14;
    public static final int TEXT_SIZE_NORMAL = 16;
    public static final int TEXT_SIZE_LARGE = 18;
    public static final int TEXT_SIZE_X_LARGE = 20;

    /**
     * 自定义改变字体的View
     * <p/>
     */

    public NewsFontSettingView(Context context) {
        super(context);
    }

    private Paint mPaint;
    private Handler mHandler;

    public NewsFontSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setTextSize(25);

        this.bounds = new Rect();
        mHandler = new InvalidateHandler(this);
    }

    public NewsFontSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setTextSize(25);
        this.bounds = new Rect();

        mHandler = new InvalidateHandler(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSpecModel = MeasureSpec.getMode(widthMeasureSpec);
        widthMeasureSpec = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureSpecModel = MeasureSpec.getMode(heightMeasureSpec);
        heightMeasureSpec = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    // View的宽度
    private float width;
    // View的高度

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        float height = h;
        this.t_y = (int) (height*2 / 3);
        this.padding = (int) (this.width / 10);
        this.fix_len = (int) (this.width - 2 * this.padding) / 3;
        this.fix_height = this.fix_len / 8;

        this.radio = this.fix_height * 1;

        this.mPaint.setTextSize(this.fix_height);
        fix_ani = currentPosition * fix_len;

    }

    // 水平横线的相对坐标
    private int t_y;

    // 水平横线的左右内边距
    private int padding;

    // 水平横线切分的平均值
    private int fix_len;

    // 小竖线的高度
    private int fix_height;

    private List<String> titles = Arrays.asList("小", "中", "大", "特大");

    private Rect bounds;

    private String main_color = "#99000000";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.parseColor(main_color));
        mPaint.setStrokeWidth(2);
        canvas.drawLine(padding, t_y, width - padding, t_y, mPaint);
        mPaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < 4; i++) {
            mPaint.setColor(Color.parseColor(main_color));
            canvas.drawLine(padding + i * this.fix_len, t_y - this.fix_height, this.padding + i * this.fix_len, t_y,
                    this.mPaint);

            String title = this.titles.get(i);
            mPaint.getTextBounds(title, 0, title.length(), bounds);
            mPaint.setColor(Color.parseColor(main_color));
            canvas.drawText(title, padding + i * this.fix_len - bounds.width() / 2,
                    t_y - this.fix_height - bounds.height() / 2 - bounds.height(), mPaint);
        }

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(this.padding + fix_ani, t_y, this.radio, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(main_color));
        canvas.drawCircle(this.padding + fix_ani, t_y, this.radio, mPaint);
    }

    float fix_ani = 0;

    class TouchState {
        public int None = 0, Down = 1, Move = 2, Up = 0, Single = 3;
        public int state = None;
    }

    public TouchState touchState = new TouchState();

    private int currentPosition = 0;

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    float radio;

    float click_rote = 1.5f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // log(event.getX() + "---" + event.getY());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (touchState.state == touchState.None) {
                float x = event.getX();
                float y = event.getY();// event.getRawY();

                for (int i = 0; i < 4; i++) {
                    if (x > padding + i * this.fix_len - radio * click_rote
                            && x < padding + i * this.fix_len + radio * click_rote && y > t_y - radio * click_rote
                            && y < t_y + radio * click_rote) {
                        if (i == this.currentPosition) {
                            touchState.state = touchState.Down;
                        } else {
                            touchState.state = touchState.Single;
                        }
                        // touchState.state = touchState.Down;
                        break;
                    }
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (touchState.state == touchState.Down) {
                touchState.state = touchState.Move;
            } else if (touchState.state == touchState.Single) {
                touchState.state = touchState.Move;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (touchState.state == touchState.Single || touchState.state == touchState.Down
                    || touchState.state == touchState.Move) {
                touchState.state = touchState.Up;
                float x = event.getX();
                for (int i = 0; i < 4; i++) {
                    if (x >= padding + i * this.fix_len - this.fix_len / 2 && x <= padding + i * this.fix_len) {
                        this.target_x = padding + i * this.fix_len;
                        this.currentPosition = i;
                        directState.state = directState.Right;
                        new AniThread().start();
                        break;
                    } else if (x <= padding + i * this.fix_len + this.fix_len / 2 && x >= padding + i * this.fix_len) {
                        this.target_x = padding + i * this.fix_len;
                        this.currentPosition = i;
                        directState.state = directState.Left;
                        new AniThread().start();
                        break;
                    }
                }
            }
        }

        if (touchState.state == touchState.Down || touchState.state == touchState.Move) {
            this.fix_ani = event.getX() - this.padding;
            if (this.fix_ani < 0) {
                this.fix_ani = 0;
            } else if (this.fix_ani > this.width - 2 * this.padding) {
                this.fix_ani = this.width - 2 * this.padding;
            }
            invalidate();// postInvalidata();
        }
        super.onTouchEvent(event);
        return true;
    }

    float target_x;

    class DirectState {
        public int None = 0, Left = 1, Right = 2;
        public int state = None;
    }

    private DirectState directState = new DirectState();

    static class InvalidateHandler extends Handler {
        WeakReference<NewsFontSettingView> mReference;

        InvalidateHandler(NewsFontSettingView view) {
            mReference = new WeakReference<NewsFontSettingView>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mReference.get().invalidate();
            }
        }
    }


    private class AniThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    if (directState.state == directState.Right) {
                        fix_ani = fix_ani + 3;
                        if (fix_ani > target_x - padding) {
                            fix_ani = target_x - padding;
                            directState.state = directState.None;
                        }
                        mHandler.sendEmptyMessage(1);
                        sleep(5);
                    } else if (directState.state == directState.Left) {
                        fix_ani = fix_ani - 3;
                        if (fix_ani < target_x - padding) {
                            fix_ani = target_x - padding;
                            directState.state = directState.None;
                        }
                        mHandler.sendEmptyMessage(1);
                        sleep(5);
                    } else {
                        if (mOnselectChangeListener != null) {
                            ((Activity)getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mOnselectChangeListener.change(currentPosition);
                                }
                            });

                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private OnSelectChangeListener mOnselectChangeListener;

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.mOnselectChangeListener = onSelectChangeListener;
    }

    public interface OnSelectChangeListener {
        void change(int position);
    }

    private void log(String message) {
        Log.e("NewsFontSettingView", message);
    }
}
