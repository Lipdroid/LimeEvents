package apom.org.researchLime.limeevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import apom.org.researchLime.limeevents.adapters.PostAdapter;
import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.customViews.CustomFontTextView;
import apom.org.researchLime.limeevents.customViews.EditTextWithFont;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.models.SectionObject;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.GlobalUtils;

public class WallActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;
    private PullToRefreshListView mLvPost = null;
    private CustomFontTextView btn_new_post = null;

    private FloatingActionsMenu actionsMenu = null;

    private FloatingActionButton logout = null;
    private FloatingActionButton search = null;
    private FrameLayout mInterceptorFrame = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        mLvPost = (PullToRefreshListView) findViewById(R.id.list_post);
        btn_new_post = (CustomFontTextView) findViewById(R.id.btn_new_post);
        actionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        logout = (FloatingActionButton) findViewById(R.id.logout);
        search = (FloatingActionButton) findViewById(R.id.search);

        search.setIcon(R.drawable.search_gray);
        logout.setIcon(R.drawable.logout_gray);

        if (GlobalUtils.user_type.equals(Constants.TYPE_GENERAL_USER)) {
            btn_new_post.setVisibility(View.INVISIBLE);
        } else if (GlobalUtils.user_type.equals(Constants.TYPE_ORGANIZER)) {
            btn_new_post.setVisibility(View.VISIBLE);
        }


        populateList();
        mLvPost.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // populateList();
                refreshView.onRefreshComplete();
            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterClickLogout();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterClickSearch();
            }
        });

        mInterceptorFrame = (FrameLayout) findViewById(R.id.fl_interceptor);
        mInterceptorFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("", "onTouch" + "");
                if (actionsMenu.isExpanded()) {
                    actionsMenu.collapse();
                    return true;
                }
                return false;
            }
        });

        mLvPost.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    Log.i("a", "scrolling stopped...");
                    actionsMenu.setVisibility(View.VISIBLE);
                }else {
                    actionsMenu.setVisibility(View.INVISIBLE);
                }
            }
        });
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void afterClickSearch() {
        actionsMenu.collapse();
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
        actionsMenu.collapse();
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
