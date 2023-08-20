package xyz.zxmo.test.touch_event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class CustomView extends TextView {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        MainActivity mainActivity = (MainActivity) getContext();
        if (!mainActivity.isInside(event, mainActivity)) {
            return super.dispatchTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainActivity.sendLog(getContext(), TAG, "dispatchTouchEvent:  down");
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.sendLog(getContext(), TAG, "dispatchTouchEvent: move");
                break;
            case MotionEvent.ACTION_UP:
                MainActivity.sendLog(getContext(), TAG, "dispatchTouchEvent: up");
                break;
            default:
        }
//        return super.dispatchTouchEvent(event);

        MainActivity activity = (MainActivity) getContext();
        int checked = activity.mViewHolder.viewDispatch.isChecked();
        if (checked == RadioGroupView.ITEM_TRUE) {
            return true;
        } else if (checked == RadioGroupView.ITEM_FALSE) {
            return false;
        } else {
            return super.dispatchTouchEvent(event);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MainActivity mainActivity = (MainActivity) getContext();
        if (!mainActivity.isInside(event, mainActivity)) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainActivity.sendLog(getContext(), TAG, "onTouchEvent: down");
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.sendLog(getContext(), TAG, "onTouchEvent: move");
                break;
            case MotionEvent.ACTION_UP:
                MainActivity.sendLog(getContext(), TAG, "onTouchEvent: up");
                break;
            default:
        }
//        return super.onTouchEvent(event);

        MainActivity activity = (MainActivity) getContext();
        int checked = activity.mViewHolder.viewEvent.isChecked();
        if (checked == RadioGroupView.ITEM_TRUE) {
            return true;
        } else if (checked == RadioGroupView.ITEM_FALSE) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private static final String TAG = "View";
}
