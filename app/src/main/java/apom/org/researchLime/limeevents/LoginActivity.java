package apom.org.researchLime.limeevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;

public class LoginActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    public void afterClickSignUp(View view) {
        gotoSignUpPage();
    }

    private void gotoSignUpPage() {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left);
    }


    public void login_pressed(View view) {
        gotoHomePage();
    }

    private void gotoHomePage() {
        startActivity(new Intent(LoginActivity.this,WallActivity.class));

    }


}
