package xyz.zxmo.test.touch_event;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RadioGroupView extends ConstraintLayout {

    public static final int CHECK_SUPER = 0;
    public static final int CHECK_TRUE = 1;
    public static final int CHECK_FALSE = 2;

    private RadioButton rbSuper;
    private RadioButton rbTrue;
    private RadioButton rbFalse;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({CHECK_SUPER, CHECK_TRUE, CHECK_FALSE})
    public @interface WHICH_CHECKED {
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
        typedArray.recycle();

        ((TextView) findViewById(R.id.tv_title)).setText(title);

        // RadioGroup 里 最多只允许一个 RadioButton 是 true 后者会覆盖前者
        rbSuper = findViewById(R.id.rb_super);
        rbTrue = findViewById(R.id.rb_true);
        rbFalse = findViewById(R.id.rb_false);

        setChecked(whichChecked);

    }

    public void setChecked(@WHICH_CHECKED int whichChecked) {
        switch (whichChecked) {
            case CHECK_SUPER:
                rbSuper.setChecked(true);
                break;
            case CHECK_TRUE:
                rbTrue.setChecked(true);
                break;
            case CHECK_FALSE:
                rbFalse.setChecked(true);
                break;
            default:
                break;
        }
    }

    @WHICH_CHECKED
    public int isChecked() {

        if (rbFalse.isChecked()) {
            return CHECK_FALSE;
        } else if (rbTrue.isChecked()) {
            return CHECK_TRUE;
        } else if (rbSuper.isChecked()) {
            return CHECK_SUPER;
        } else {
            //default:super.XX
            return CHECK_SUPER;
        }

    }

}
