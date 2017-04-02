package apom.org.researchLime.limeevents;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.customViews.EditTextWithFont;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.GlobalUtils;

public class RegistrationActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;

    private EditTextWithFont et_name = null;
    private EditTextWithFont et_mail = null;
    private EditTextWithFont et_password = null;
    private EditTextWithFont et_organization = null;
    private EditTextWithFont et_position = null;
    private EditTextWithFont et_phone = null;

    private Button btn_signup = null;

    private String mail = null;
    private String password = null;
    private String name = null;
    private String position = null;
    private String organization = null;
    private String phone = null;

    private Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mContext = RegistrationActivity.this;

        et_name = (EditTextWithFont) findViewById(R.id.et_name);
        et_mail = (EditTextWithFont) findViewById(R.id.et_email);
        et_password = (EditTextWithFont) findViewById(R.id.et_password);
        et_organization = (EditTextWithFont) findViewById(R.id.et_organization);
        et_position = (EditTextWithFont) findViewById(R.id.et_position);
        et_phone = (EditTextWithFont) findViewById(R.id.et_phone);


        if(GlobalUtils.user_type.equals(Constants.TYPE_GENERAL_USER)){

            et_organization.setVisibility(View.GONE);
            et_position.setVisibility(View.GONE);
        }

        btn_signup = (Button) findViewById(R.id.btn_submit);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterClickSignUp();
            }
        });
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void afterClickSignUp() {
        String dialogBody = "";

        mail = et_mail.getText().toString().trim();
        password = et_password.getText().toString().trim();
        name = et_name.getText().toString().trim();
        position = et_position.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        organization = et_organization.getText().toString().trim();

        if (name == null || name.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_name_empty_password);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if (mail == null || mail.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_email_empty);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if (password == null || password.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_email_empty_password);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if ((organization == null || organization.equals("")) && GlobalUtils.user_type.equals(Constants.TYPE_ORGANIZER)) {
            dialogBody = getResources().getString(R.string.dialog_body_organization_empty_password);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if ((position == null || position.equals("")) && GlobalUtils.user_type.equals(Constants.TYPE_ORGANIZER)){
            dialogBody = getResources().getString(R.string.dialog_body_position_empty_password);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if (phone == null || phone.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_phone_empty_password);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        }
        requestForSignUp();
    }

    private void requestForSignUp() {
        //request to server
    }

    private void afterClickBack() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_right);
    }

    public void back(View view) {
        afterClickBack();
    }
}
