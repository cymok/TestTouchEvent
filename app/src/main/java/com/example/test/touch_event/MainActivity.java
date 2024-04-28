package com.example.test.touch_event;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    private final String message = "（此处的 View 特指最里层的 View）\n" +
            "\n" +
            "1. 事件从 Activity 的 dispatchTouchEvent 开始分发，逐层往里传递，默认都返回 super 分发事件且由本层处理，重写返回 true 消费事件且流程结束，重写返回 false 不消费事件且往外传递（Activity 不分发则流程结束）；\n" +
            "\n" +
            "2. ViewGroup 的 onInterceptTouchEvent 可以拦截事件，默认不拦截，一旦拦截，事件由拦截层处理，消费或往外层传递；\n" +
            "\n" +
            "3. onTouchEvent 默认只有 View 层的是 true 会消费事件，setOnTouchListener 的 onTouch 重写返回 true 可以消费掉后续 View 的 onTouchEvent 和 setOnClickListener 的 onClick 事件；\n" +
            "如果 View 不消费事件，将逐层往外传递，ViewGroup 的 onTouchEvent 重写返回 true 消费掉事件；\n" +
            "如果都不消费事件，无论 Activity 的 onTouchEvent 重写返回什么，事件最终都会由 Activity 执行函数消费。";

    public ViewHolder mViewHolder;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewHolder = new ViewHolder(this);

        CustomView view = findViewById(R.id.view);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!MainActivity.this.isInside(event, MainActivity.this)) {
                    return false;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        MainActivity.sendLog(MainActivity.this, TAG, "view.setOnTouchListener: down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        MainActivity.sendLog(MainActivity.this, TAG, "view.setOnTouchListener:  move");
                        break;
                    case MotionEvent.ACTION_UP:
                        MainActivity.sendLog(MainActivity.this, TAG, "view.setOnTouchListener: up");
                        break;
                    default:
                }
                // Activity 里 View.setOnTouchListener 严格来说不属于事件传递机制里, 只是在传递过程插入一个监听函数,
                // 但是可以返回 true 表示消费了, View 的 onTouchEvent 就不会收到事件
                int checked = mViewHolder.viewEventListener.isChecked();
                if (checked == RadioGroupView.ITEM_TRUE) {
                    return true;
                } else if (checked == RadioGroupView.ITEM_FALSE) {
                    return false;
                } else {
                    return v.onTouchEvent(event);
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.sendLog(MainActivity.this, TAG, "view.setOnClickListener: click");
            }
        });

        IntentFilter filter = new IntentFilter(getPackageName() + ".log");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "说明");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); // 始终显示在标题栏
        item.setIcon(android.R.drawable.ic_menu_info_details);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("事件传递机制")
                        .setMessage(message)
                        .show();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView = findViewById(R.id.tv_log);
            String log = intent.getStringExtra("log");
            if (TextUtils.isEmpty(log)) {
                return;
            }
            // 每次流程开始前清空日志
            if (log != null && log.contains("START")) {
                textView.setText(null);
            }
            String string = textView.getText().toString();
            if (!TextUtils.isEmpty(string)) {
                string += "\n";
            }
            textView.setText(string + log);
            textView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ScrollView) findViewById(R.id.sv_log)).fullScroll(ScrollView.FOCUS_DOWN);
                }
            }, 200);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!this.isInside(event, this)) {
            return super.dispatchTouchEvent(event);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            MainActivity.sendLog(this, TAG, "--- START ---");
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainActivity.sendLog(this, TAG, "dispatchTouchEvent: down");
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.sendLog(this, TAG, "dispatchTouchEvent: move");
                break;
            case MotionEvent.ACTION_UP:
                MainActivity.sendLog(this, TAG, "dispatchTouchEvent: up");
                break;
            default:
        }
//        return super.dispatchTouchEvent(event);

        int checked = this.mViewHolder.activityDispatch.isChecked();
        if (checked == RadioGroupView.ITEM_TRUE) {
            return true;
        } else if (checked == RadioGroupView.ITEM_FALSE) {
            return false;
        } else {
            return super.dispatchTouchEvent(event);
        }
    }

    public static void sendLog(Context context, String tag, String log) {
//        Log.d(tag, log);

        //测试设备没有root权限 不能直接通过adb获取logcat内容

        Intent intent = new Intent(context.getPackageName() + ".log");
        intent.putExtra("log", tag + ": " + log);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isInside(event, this)) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainActivity.sendLog(this, TAG, "onTouchEvent: down");
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.sendLog(this, TAG, "onTouchEvent: move");
                break;
            case MotionEvent.ACTION_UP:
                MainActivity.sendLog(this, TAG, "onTouchEvent: up");
                break;
            default:
        }
//        return super.onTouchEvent(event);

        int checked = this.mViewHolder.activityEvent.isChecked();
        if (checked == RadioGroupView.ITEM_TRUE) {
            return true;
        } else if (checked == RadioGroupView.ITEM_FALSE) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private static final String TAG = "Activity";

    public static class ViewHolder {

        RadioGroupView activityDispatch;
        RadioGroupView viewGroupDispatch;
        RadioGroupView viewGroupIntercept;
        RadioGroupView viewDispatch;
        RadioGroupView viewEvent;
        RadioGroupView viewGroupEvent;
        RadioGroupView activityEvent;
        RadioGroupView viewEventListener;

        public ViewHolder(MainActivity activity) {

            activityDispatch = activity.findViewById(R.id.activity_dispatch);
            viewGroupDispatch = activity.findViewById(R.id.view_group_dispatch);
            viewGroupIntercept = activity.findViewById(R.id.view_group_intercept);
            viewDispatch = activity.findViewById(R.id.view_dispatch);
            viewEvent = activity.findViewById(R.id.view_event);
            viewGroupEvent = activity.findViewById(R.id.view_group_event);
            activityEvent = activity.findViewById(R.id.activity_event);
            viewEventListener = activity.findViewById(R.id.view_event_listener);

        }
    }

    /**
     * 触摸点是否在 Activity-ViewGroup-View 区域内
     */
    public boolean isInside(MotionEvent event, MainActivity activity) {
        View lineHorizontal = activity.findViewById(R.id.line_center_horizontal);
        View lineVertical = activity.findViewById(R.id.line_center_vertical);
        //目标区域
        int[] tempXY = new int[2];
        lineVertical.getLocationOnScreen(tempXY);
        int right = tempXY[0];
        lineHorizontal.getLocationOnScreen(tempXY);
        int top = tempXY[1];
        //
        return event.getRawX() < right && event.getRawY() > top;
    }
}
