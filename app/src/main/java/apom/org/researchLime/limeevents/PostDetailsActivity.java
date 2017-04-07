package apom.org.researchLime.limeevents;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import apom.org.researchLime.limeevents.customViews.CustomFontTextView;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.MultipleScreen;

public class PostDetailsActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;
    private PostObject post = null;
    private CustomFontTextView tv_title = null;
    private ImageView image = null;

    private CustomFontTextView tv_description = null;
    private CustomFontTextView tv_location = null;
    private CustomFontTextView tv_date = null;
    private CustomFontTextView tv_time = null;
    private CustomFontTextView tv_rate = null;
    DisplayImageOptions option = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true)
            .showImageOnLoading(R.color.line_config)
            .showImageOnFail(R.color.line_config)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        tv_title = (CustomFontTextView) findViewById(R.id.tv_title);
        tv_description = (CustomFontTextView) findViewById(R.id.tv_description);
        tv_location = (CustomFontTextView) findViewById(R.id.tv_location);
        tv_date = (CustomFontTextView) findViewById(R.id.tv_date);
        tv_time = (CustomFontTextView) findViewById(R.id.tv_time);
        tv_rate = (CustomFontTextView) findViewById(R.id.tv_rate);
        image = (ImageView) findViewById(R.id.image);

        try {
            post = getIntent().getParcelableExtra(PostObject.class.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (post != null) {
            showInfo(post);
        }
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();

    }

    private void showInfo(PostObject post) {
        tv_title.setText(post.getPost_title());

        tv_description.setText(post.getPost_description());
        tv_location.setText(post.getPost_address());
        tv_date.setText(post.getPost_date());
        tv_time.setText(post.getPost_time());
        tv_rate.setText(post.getPost_rate());

        if (post.getPost_image() != null && !post.getPost_image().equals("")) {
            ImageLoader.getInstance().displayImage(post.getPost_image(), image, option);
        }
    }


    public void back_pressed(View view) {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_right);
    }

    public void share_pressed(View view) {
        String googlPlaystoreLink = "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName();
        String shareText = post.getPost_title()+" event is happening "+post.getPost_date()+" in "+post.getPost_address()+". Download this app and keep up to date with the detail. "+googlPlaystoreLink;

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
