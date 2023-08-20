package xyz.zxmo.test.touch_event;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RadioGroupView extends ConstraintLayout {

    public static final int ITEM_SUPER = 0;
    public static final int ITEM_TRUE = 1;
    public static final int ITEM_FALSE = 2;

    private RadioButton rbSuper;
    private RadioButton rbTrue;
    private RadioButton rbFalse;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({ITEM_SUPER, ITEM_TRUE, ITEM_FALSE})
    public @interface WHICH_ITEM {
    }

    public RadioGroupView(Context context) {
        this(context, null);
    }

    public RadioGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

        View.inflate(context, R.layout.layout_radio_group_view, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadioGroupView);
        String title = typedArray.getString(R.styleable.RadioGroupView_title);
        int whichChecked = typedArray.getInt(R.styleable.RadioGroupView_whichChecked, -1);
        int defaultItem = typedArray.getInt(R.styleable.RadioGroupView_defaultItem, -1);
        boolean hideSuper = typedArray.getBoolean(R.styleable.RadioGroupView_hideSuper, false);
        typedArray.recycle();

        ((TextView) findViewById(R.id.tv_title)).setText(title);

        // RadioGroup 里 最多只允许一个 RadioButton 是 true 后者会覆盖前者
        rbSuper = findViewById(R.id.rb_super);
        rbTrue = findViewById(R.id.rb_true);
        rbFalse = findViewById(R.id.rb_false);

        setChecked(whichChecked);

        if (defaultItem == ITEM_TRUE) {
            rbTrue.setTextColor(Color.BLUE);
        } else if (defaultItem == ITEM_FALSE) {
            rbFalse.setTextColor(Color.BLUE);
        } else if(defaultItem == ITEM_SUPER) {
            rbSuper.setTextColor(Color.BLUE);
        }

        if (hideSuper) {
            rbSuper.setVisibility(View.INVISIBLE);
        }
    }

    public void setChecked(@WHICH_ITEM int whichChecked) {
        switch (whichChecked) {
            case ITEM_SUPER:
                rbSuper.setChecked(true);
                break;
            case ITEM_TRUE:
                rbTrue.setChecked(true);
                break;
            case ITEM_FALSE:
                rbFalse.setChecked(true);
                break;
            default:
                break;
        }
    }

    @WHICH_ITEM
    public int isChecked() {

        if (rbFalse.isChecked()) {
            return ITEM_FALSE;
        } else if (rbTrue.isChecked()) {
            return ITEM_TRUE;
        } else if (rbSuper.isChecked()) {
            return ITEM_SUPER;
        } else {
            //default:super.XX
            return ITEM_SUPER;
        }

    }

}
