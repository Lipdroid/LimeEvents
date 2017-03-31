package apom.org.researchLime.limeevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;

public class WallActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    public void logout_Pressed(View view) {
        afterClickLogout();
    }

    private void afterClickLogout() {

        finish();
        overridePendingTransition(R.anim.anim_nothing,
                R.anim.anim_slide_out_bottom);
    }

    private void goToAddPostActivity(){
        startActivity(new Intent(WallActivity.this,NewPostActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left);
    }

    public void add_new_post_pressed(View view) {
        goToAddPostActivity();

    }
}
