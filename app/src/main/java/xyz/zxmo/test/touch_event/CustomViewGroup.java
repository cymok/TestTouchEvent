package xyz.zxmo.test.touch_event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomViewGroup extends ConstraintLayout {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        MainActivity mainActivity = (MainActivity) getContext();
        if (!mainActivity.isInside(event, mainActivity)) {
            return super.dispatchTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainActivity.sendLog(getContext(), TAG, "dispatchTouchEvent: down");
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
        int checked = activity.mViewHolder.viewGroupDispatch.isChecked();
        if (checked == RadioGroupView.ITEM_TRUE) {
            return true;
        } else if (checked == RadioGroupView.ITEM_FALSE) {
            return false;
        } else {
            return super.dispatchTouchEvent(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        MainActivity mainActivity = (MainActivity) getContext();
        if (!mainActivity.isInside(event, mainActivity)) {
            return super.onInterceptTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainActivity.sendLog(getContext(), TAG, "onInterceptTouchEvent: down");
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.sendLog(getContext(), TAG, "onInterceptTouchEvent: move");
                break;
            case MotionEvent.ACTION_UP:
                MainActivity.sendLog(getContext(), TAG, "onInterceptTouchEvent: up");
                break;
            default:
        }
//        return super.onInterceptTouchEvent(event);

        MainActivity activity = (MainActivity) getContext();
        int checked = activity.mViewHolder.viewGroupIntercept.isChecked();
        if (checked == RadioGroupView.ITEM_TRUE) {
            return true;
        } else if (checked == RadioGroupView.ITEM_FALSE) {
            return false;
        } else {
            return super.onInterceptTouchEvent(event);
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
        int checked = activity.mViewHolder.viewGroupEvent.isChecked();
        if (checked == RadioGroupView.ITEM_TRUE) {
            return true;
        } else if (checked == RadioGroupView.ITEM_FALSE) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private static final String TAG = "ViewGroup";
}
