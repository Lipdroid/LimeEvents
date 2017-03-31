package apom.org.researchLime.limeevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import apom.org.researchLime.limeevents.adapters.PostAdapter;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.models.SectionObject;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;

public class WallActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;
    private PullToRefreshListView mLvPost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        mLvPost = (PullToRefreshListView) findViewById(R.id.list_post);
        populateList();
        mLvPost.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
               // populateList();
                refreshView.onRefreshComplete();
            }

        });
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void populateList() {
        ArrayList<Object> mListPost = new ArrayList<Object>();
        ArrayList<SectionObject> listSection = new ArrayList<SectionObject>();

        PostObject p = new PostObject();
        p.setPost_title("test Name");
        p.setPost_address("east avenue ditach");
        p.setPost_organizer("Amazon");
        p.setPost_rate("5");

        PostObject p1 = new PostObject();
        p1.setPost_title("test Name");
        p1.setPost_address("east avenue ditach");
        p1.setPost_organizer("Amazon");
        p1.setPost_rate("5");


        PostObject p2 = new PostObject();
        p2.setPost_title("test Name");
        p2.setPost_address("east avenue ditach");
        p2.setPost_organizer("Amazon");
        p2.setPost_rate("5");


        mListPost.add(p1);
        PostObject p3 = new PostObject();
        p3.setPost_title("test Name");
        p3.setPost_address("east avenue ditach");
        p3.setPost_organizer("Amazon");
        p3.setPost_rate("5");

        mListPost.add(p2);
        PostObject p4 = new PostObject();
        p4.setPost_title("test Name");
        p4.setPost_address("east avenue ditach");
        p4.setPost_organizer("Amazon");
        p4.setPost_rate("5");

        mListPost.add(p3);
        mListPost.add(p4);
        mListPost.add(p);

        SectionObject s1 = new SectionObject();
        s1.setmSectionLabel("This Week Posts");
        s1.setmSectionSize(mListPost.size());
        s1.setmListData(mListPost);
        listSection.add(s1);

        SectionObject s2 = new SectionObject();
        s2.setmSectionLabel("Next week posts");
        s2.setmSectionSize(mListPost.size());
        s2.setmListData(mListPost);
        listSection.add(s2);

        PostAdapter mAdapter = new PostAdapter(WallActivity.this, listSection);

        mLvPost.setAdapter(mAdapter);

    }

    public void logout_Pressed(View view) {
        afterClickLogout();
    }

    private void afterClickLogout() {

        finish();
        overridePendingTransition(R.anim.anim_nothing,
                R.anim.anim_slide_out_bottom);
    }

    private void goToAddPostActivity() {
        startActivity(new Intent(WallActivity.this, NewPostActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left);
    }

    public void add_new_post_pressed(View view) {
        goToAddPostActivity();

    }
}
