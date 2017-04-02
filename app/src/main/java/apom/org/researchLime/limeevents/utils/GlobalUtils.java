package apom.org.researchLime.limeevents.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import apom.org.researchLime.limeevents.LimeApplication;
import apom.org.researchLime.limeevents.R;
import apom.org.researchLime.limeevents.customViews.CustomDialog;
import apom.org.researchLime.limeevents.customViews.CustomProgressDialog;
import apom.org.researchLime.limeevents.interfaces.DialogCallback;
import apom.org.researchLime.limeevents.interfaces.DialogForValueCallback;

/**
 * Created by lipuhossain on 1/29/17.
 */

public class GlobalUtils {
    private static CustomProgressDialog sPdLoading = null;
    public static String user_type = "";


    public static void showLoadingProgress(Context context) {
        if (CustomProgressDialog.sPdCount <= 0) {
            CustomProgressDialog.sPdCount = 0;
            sPdLoading = null;
            sPdLoading = new CustomProgressDialog(context, R.style.CustomDialogTheme);
            sPdLoading.show();
            if (Build.VERSION.SDK_INT > 10) {
                View loadingV = LayoutInflater.from(context).inflate(R.layout.layout_pd_loading, null);
                new MultipleScreen(context);
                MultipleScreen.resizeAllView((ViewGroup) loadingV);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(MultipleScreen.getValueAfterResize(340),
                        MultipleScreen.getValueAfterResize(340));
                sPdLoading.addContentView(loadingV, lp);
            } else {
                String message = context.getResources().getString(R.string.common_loading);
                sPdLoading.setMessage(message);
            }
            CustomProgressDialog.sPdCount++;
        } else {
            CustomProgressDialog.sPdCount++;
        }
    }

    public static void dismissLoadingProgress() {
        if (CustomProgressDialog.sPdCount <= 1) {
            if (sPdLoading != null && sPdLoading.isShowing())
                sPdLoading.dismiss();
            CustomProgressDialog.sPdCount--;
        } else {
            CustomProgressDialog.sPdCount--;
        }
    }

    public static void showInfoDialog(Context context, String title, String body, String action, final DialogCallback dialogCallback) {
        final CustomDialog infoDialog = new CustomDialog(context, R.style.CustomDialogTheme);
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.layout_show_info_dialog, null);

        new MultipleScreen(context);
        MultipleScreen.resizeAllView((ViewGroup) v);

        infoDialog.setContentView(v);

        Button btnOK = (Button) infoDialog.findViewById(R.id.dialog_btn_positive);
        TextView tvTitle = (TextView) infoDialog.findViewById(R.id.dialog_tv_title);
        TextView tvBody = (TextView) infoDialog.findViewById(R.id.dialog_tv_body);

        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }

        if (body == null) {
            tvBody.setVisibility(View.GONE);
        } else {
            tvBody.setText(body);
        }

        if (action != null) {
            btnOK.setText(action);
        }
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //your business logic
                if (dialogCallback != null) {
                    dialogCallback.onAction1();
                }
                infoDialog.dismiss();
            }
        });

        infoDialog.show();
    }


    public static void showDialogRating(final Context context, final DialogForValueCallback dialogCallback) {
        final CustomDialog infoDialog = new CustomDialog(context, R.style.CustomDialogTheme);
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.dialog_ratings, null);

        new MultipleScreen(context);
        MultipleScreen.resizeAllView((ViewGroup) v);

        infoDialog.setContentView(v);

        Button btnOK = (Button) infoDialog.findViewById(R.id.dialog_btn_positive);
        Button btnDismiss = (Button) infoDialog.findViewById(R.id.dialog_btn_cancel);
        final RatingBar ratingBar = (RatingBar) infoDialog.findViewById(R.id.ratingBar);


        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //your business logic
                if (dialogCallback != null) {
                    String rate = String.valueOf(ratingBar.getRating());
                    dialogCallback.onAction1(rate);
                }


                infoDialog.dismiss();
            }
        });


        btnDismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //your business logic
                if (dialogCallback != null) {
                    dialogCallback.onAction2();
                }

                infoDialog.dismiss();

            }
        });


        infoDialog.show();
    }


    public static boolean isNetworkConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) LimeApplication.LimeApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void StartAndEndDateOfThisWeek(Date fromDate) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fromDate);
        //first day of week
        c1.set(Calendar.DAY_OF_WEEK, 1);

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);

        //last day of week
        c1.set(Calendar.DAY_OF_WEEK, 7);

        int year7 = c1.get(Calendar.YEAR);
        int month7 = c1.get(Calendar.MONTH)+1;
        int day7 = c1.get(Calendar.DAY_OF_MONTH);

        Log.e("Startof this week",day1+"/"+month1+"/"+year1);
        Log.e("last day",day7+"/"+month7+"/"+year7);


    }

}
