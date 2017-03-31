package apom.org.researchLime.limeevents.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Sabbir Ahmed on 1/21/2017.
 */

public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context) {
        super(context);
        init();
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "light.ttf");
        setTypeface(tf);
    }
}
