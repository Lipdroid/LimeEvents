package apom.org.researchLime.limeevents;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.customViews.EditTextWithFont;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.GlobalUtils;

public class LoginActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;
    EditTextWithFont et_mail = null;
    EditTextWithFont et_password = null;
    private Button btn_login = null;
    private String mail = null;
    private String password = null;
    Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;

        et_mail = (EditTextWithFont) findViewById(R.id.et_mail);
        et_password = (EditTextWithFont) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                afterClickLogin();
            }
        });


        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }


    private void afterClickLogin() {
        String dialogBody = "";

        mail = et_mail.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (mail == null || mail.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_email_empty);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if (password == null || password.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_email_empty_password);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        }
        requestForLogin();

    }

    private void requestForLogin() {

        //Api call to server

        gotoHomePage();
    }

    public void afterClickSignUp(View view) {

        showGenderdialog();
    }

    private void gotoSignUpPage() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left);
    }


    private void gotoHomePage() {
        startActivity(new Intent(LoginActivity.this, WallActivity.class));

    }


    private void showGenderdialog() {
        // TODO Auto-generated method stub

        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_user_type_picker);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout organizer = (LinearLayout) dialog.findViewById(R.id.male);
        LinearLayout user = (LinearLayout) dialog.findViewById(R.id.female);


        organizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                GlobalUtils.user_type = Constants.TYPE_ORGANIZER;
                gotoSignUpPage();


            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                GlobalUtils.user_type = Constants.TYPE_GENERAL_USER;
                gotoSignUpPage();

            }
        });

        dialog.show();
    }


}
