package apom.org.researchLime.limeevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;

public class NewPostActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void afterClickBack(){
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_right);
    }

    public void back_btn_pressed(View view) {
        afterClickBack();
    }
}
