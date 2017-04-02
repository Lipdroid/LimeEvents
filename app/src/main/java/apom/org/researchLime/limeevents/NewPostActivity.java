package apom.org.researchLime.limeevents;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.customViews.EditTextWithFont;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.GlobalUtils;
import apom.org.researchLime.limeevents.utils.ImageUtils;

public class NewPostActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;
    private EditTextWithFont et_post_title = null;
    private EditTextWithFont et_post_location = null;
    private EditTextWithFont et_rate = null;
    private EditTextWithFont et_contactInfo = null;

    private LinearLayout btn_image = null;
    private LinearLayout btn_date = null;

    public static final int TYPE_UPLOAD_PHOTO = 999;
    private int MY_REQUEST_CODE = 111;
    private Context mContext = null;
    private TextView tv_image_label = null;
    private TextView tv_date_label = null;

    private Bitmap image = null;


    private String title = null;
    private String location = null;
    private String rate = null;
    private String contact = null;
    private Button btn_post_submit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mContext = NewPostActivity.this;

        et_post_title = (EditTextWithFont) findViewById(R.id.et_post_title);
        et_post_location = (EditTextWithFont) findViewById(R.id.et_post_location);
        et_rate = (EditTextWithFont) findViewById(R.id.et_rate);
        et_contactInfo = (EditTextWithFont) findViewById(R.id.et_contactInfo);

        btn_image = (LinearLayout) findViewById(R.id.btn_image);
        btn_date = (LinearLayout) findViewById(R.id.btn_date);
        tv_image_label = (TextView) findViewById(R.id.tv_image_label);
        tv_date_label = (TextView) findViewById(R.id.tv_date_label);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterClickDate();
            }
        });
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterClickImage();
            }
        });
        btn_post_submit = (Button) findViewById(R.id.btn_submit);
        btn_post_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterClickPost();
            }
        });

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void afterClickPost() {
        String dialogBody = "";

        title = et_post_title.getText().toString().trim();
        location = et_post_location.getText().toString().trim();
        rate = et_rate.getText().toString().trim();
        contact = et_contactInfo.getText().toString().trim();

        if (image == null) {
            dialogBody = getResources().getString(R.string.dialog_body_name_empty_image);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if (title == null || title.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_name_empty_title);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if (location == null || location.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_no_location);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if (rate == null || rate.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_rate_empty);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        } else if (contact == null || contact.equals("")) {
            dialogBody = getResources().getString(R.string.dialog_body_contact_empty);
            GlobalUtils.showInfoDialog(mContext, null, dialogBody, null, null);
            return;
        }

        reguestForPost();
    }

    private void reguestForPost() {
    }

    private void afterClickDate() {
        new SingleDateAndTimePickerDialog.Builder(mContext)
                //.bottomSheet()
                //.curved()
                //.minutesStep(15)
                .title("Set Date & Time")
                .mainColor(getResources().getColor(R.color.cetagory_item_more_bg))
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        tv_date_label.setText("Date Added " + " ✓");

                    }
                }).display();
    }

    private void afterClickImage() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_REQUEST_CODE);
            }
        }
        //start your camera
        Intent intentChooseImage = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");

        if (Build.VERSION.SDK_INT < 19) {
            intentChooseImage = new Intent();
            intentChooseImage.setAction(Intent.ACTION_GET_CONTENT);
            intentChooseImage.setType("image/*");

        } else {
            intentChooseImage = new Intent(Intent.ACTION_GET_CONTENT);
            intentChooseImage.addCategory(Intent.CATEGORY_OPENABLE);
            intentChooseImage.setType("image/*");
        }
        Intent chooserIntent = Intent.createChooser(takePictureIntent, getResources().getString(R.string.dialog_choose_image_title));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, TYPE_UPLOAD_PHOTO);
    }

    private void afterClickBack() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_right);
    }

    public void back_btn_pressed(View view) {
        afterClickBack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == TYPE_UPLOAD_PHOTO) {
                InputStream stream = null;
                Bitmap bitmap = null;

                try {
                    Uri uri = data.getData();
                    Bundle bundle = data.getExtras();
                    if (uri == null) {
                        // case nexus device
                        Bitmap imageBitmap = (Bitmap) bundle.get("data");
                        // mImgProfile.setImageBitmap(imageBitmap);
                        bitmap = imageBitmap;
                    } else {
                        stream = mContext.getContentResolver().openInputStream(data.getData());
                        bitmap = BitmapFactory.decodeStream(stream);

                        String absPath = ImageUtils.getPath(mContext, uri);
                        bitmap = ImageUtils.rotateBitmap(bitmap, Uri.parse(absPath));
                    }
                    // thumb bitmap
                    int bmHeight = bitmap.getHeight();
                    int bmWith = bitmap.getWidth();

                    float ratio = bmWith * 1.0f / bmHeight;

                    Bitmap thumbBitmap = ImageUtils.getBitmapThumb(bitmap, 1080, Math.round(1080 / ratio));
                    getBitmap(thumbBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void getBitmap(Bitmap thumbBitmap) {
        tv_image_label.setText("Image Added " + " ✓");
        image = thumbBitmap;
    }
}
