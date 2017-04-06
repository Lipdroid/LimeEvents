package apom.org.researchLime.limeevents;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.HashMap;

import apom.org.researchLime.limeevents.apis.RequestAsyncTask;
import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.customViews.CustomFontTextView;
import apom.org.researchLime.limeevents.customViews.EditTextWithFont;
import apom.org.researchLime.limeevents.interfaces.AsyncCallback;
import apom.org.researchLime.limeevents.models.UserObject;
import apom.org.researchLime.limeevents.utils.APIUtils;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.GlobalUtils;
import apom.org.researchLime.limeevents.utils.SharedPreferencesUtils;

public class LoginActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;
    EditTextWithFont et_mail = null;
    EditTextWithFont et_password = null;
    private Button btn_login = null;
    private String mail = null;
    private String password = null;
    Context mContext = null;
    private RequestAsyncTask mRequestAsync = null;
    private String TAG = "LoginActivity";
    private UserObject userObject = null;
    CustomFontTextView btn_signup = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;

        et_mail = (EditTextWithFont) findViewById(R.id.et_mail);
        et_password = (EditTextWithFont) findViewById(R.id.et_password);
        btn_signup = (CustomFontTextView) findViewById(R.id.btn_signup);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                afterClickLogin();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderdialog();
            }
        });

        mail = SharedPreferencesUtils.getString(mContext, Constants.MAIL_ADDRESS);
        password = SharedPreferencesUtils.getString(mContext, Constants.MAIL_PASS);
        et_mail.setText(mail);
        et_password.setText(password);

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

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_TAG, "login_by_mail");
        params.put(Constants.PARAM_EMAIL, mail);
        params.put(Constants.PARAM_PASSWORD, password);


        mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_USER_BY_MAIL, params, new AsyncCallback() {
            @Override
            public void done(String result) {
                Log.e(TAG, result);
                GlobalUtils.dismissLoadingProgress();

                HashMap<String, Object> returnHash = APIUtils.parseJSON(Constants.REQUEST_GET_USER_BY_MAIL, result);

                if (returnHash.containsKey(Constants.TAG_USER)) {
                    userObject = (UserObject) returnHash.get(Constants.TAG_USER);
                    if (userObject != null) {
                        SharedPreferencesUtils.putString(mContext, Constants.MAIL_ADDRESS, mail);
                        SharedPreferencesUtils.putString(mContext, Constants.MAIL_PASS, password);
                        GlobalUtils.setCurrentUserObj(userObject);
                        SharedPreferencesUtils.putBoolean(mContext, Constants.ALREADY_LOGGED_IN, true);
                        SharedPreferencesUtils.putString(mContext, Constants.LOGGED_IN_USER_TYPE, userObject.getUserCategory());
                        SharedPreferencesUtils.putString(mContext, Constants.USER_NAME, userObject.getUserName());
                        SharedPreferencesUtils.putString(mContext, Constants.USER_ID, userObject.getUserId());

                        gotoHomePage();
                        finish();
                    }
                }

                if (returnHash.containsKey(Constants.TAG_ERROR)) {
                    String title = getResources().getString(R.string.dialog_error_title);
                    String action = getResources().getString(R.string.common_ok_label);
                    String body = (String) returnHash.get(Constants.TAG_ERROR);
                    ;
                    if (body != null) {
                        GlobalUtils.showInfoDialog(mContext, title, body, action, null);
                    }
                }

            }

            @Override
            public void progress() {
                GlobalUtils.showLoadingProgress(mContext);
            }

            @Override
            public void onInterrupted(Exception e) {
                GlobalUtils.dismissLoadingProgress();
            }

            @Override
            public void onException(Exception e) {
                GlobalUtils.dismissLoadingProgress();
            }
        });

        mRequestAsync.execute();

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
