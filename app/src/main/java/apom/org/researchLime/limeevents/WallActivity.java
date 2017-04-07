package apom.org.researchLime.limeevents;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import apom.org.researchLime.limeevents.adapters.PostAdapter;
import apom.org.researchLime.limeevents.apis.RequestAsyncTask;
import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.customViews.CustomFontTextView;
import apom.org.researchLime.limeevents.customViews.EditTextWithFont;
import apom.org.researchLime.limeevents.interfaces.AsyncCallback;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.models.SectionObject;
import apom.org.researchLime.limeevents.models.UserObject;
import apom.org.researchLime.limeevents.utils.APIUtils;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.GlobalUtils;
import apom.org.researchLime.limeevents.utils.SharedPreferencesUtils;

public class WallActivity extends AppCompatActivity {
    CorrectSizeUtil mCorrectSize = null;
    private PullToRefreshListView mLvPost = null;
    private CustomFontTextView btn_new_post = null;

    private FloatingActionsMenu actionsMenu = null;

    private FloatingActionButton logout = null;
    private FloatingActionButton search = null;
    private FloatingActionButton admin = null;
    private FrameLayout mInterceptorFrame = null;
    private RelativeLayout contactBar = null;
    private Context mContext = null;
    private String TAG = "WallActivity";
    private RequestAsyncTask mRequestAsync = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        mContext = WallActivity.this;

        mLvPost = (PullToRefreshListView) findViewById(R.id.list_post);
        btn_new_post = (CustomFontTextView) findViewById(R.id.btn_new_post);
        actionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        logout = (FloatingActionButton) findViewById(R.id.logout);
        search = (FloatingActionButton) findViewById(R.id.search);
        admin = (FloatingActionButton) findViewById(R.id.admin_panel);
        contactBar = (RelativeLayout) findViewById(R.id.contactBar);

        search.setIcon(R.drawable.search_gray);
        logout.setIcon(R.drawable.logout_gray);
        admin.setIcon(R.drawable.admin);


        String user_Type = SharedPreferencesUtils.getString(mContext, Constants.LOGGED_IN_USER_TYPE, Constants.TYPE_GENERAL_USER);

        if (user_Type.equals(Constants.TYPE_GENERAL_USER)) {
            btn_new_post.setVisibility(View.INVISIBLE);
            actionsMenu.removeButton(admin);
        } else if (user_Type.equals(Constants.TYPE_ORGANIZER)) {
            btn_new_post.setVisibility(View.VISIBLE);
            actionsMenu.removeButton(admin);
        }

        getContents(null);
        mLvPost.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // populateList();
                refreshView.onRefreshComplete();
            }

        });

        contactBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+223 205897681"));
                if (ActivityCompat.checkSelfPermission(WallActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
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

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterClickAdmin();
            }
        });

        btn_new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddPostActivity();
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
                // TODO Auto-generated method stub-
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    Log.i("a", "scrolling stopped...");
                    actionsMenu.setVisibility(View.VISIBLE);
                    //animate the view from btom to top
                    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(actionsMenu, "scaleX", 0f, 1f);
                    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(actionsMenu, "scaleY", 4f, 1f);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
                    animatorSet.setDuration(500);
                    animatorSet.setInterpolator(new DecelerateInterpolator(2));
                    actionsMenu.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                    animatorSet.start();
                } else {
                    actionsMenu.setVisibility(View.INVISIBLE);
                }
            }
        });

        mLvPost.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getContents(refreshView);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void afterClickAdmin() {
        actionsMenu.collapse();
        startActivity(new Intent(WallActivity.this, AdminActivity.class));
    }

    private void afterClickSearch() {
        actionsMenu.collapse();
        startActivity(new Intent(WallActivity.this, ShowAllActivity.class));
    }

    private void populateList() {

        ArrayList<SectionObject> listSection = new ArrayList<SectionObject>();

        Date date = new Date();
        List<PostObject> mListThisWeek = getSegmentedList(GlobalUtils.StartAndEndDateOfThisWeek(date)[0], GlobalUtils.StartAndEndDateOfThisWeek(date)[1], GlobalUtils.mListPost);


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date newDate = cal.getTime();

        List<PostObject> mListNextWeek = getSegmentedList(GlobalUtils.StartAndEndDateOfThisWeek(newDate)[0], GlobalUtils.StartAndEndDateOfThisWeek(newDate)[1], GlobalUtils.mListPost);


        ArrayList<Object> mListSectionOne = new ArrayList<Object>();
        ArrayList<Object> mListSectionTwo = new ArrayList<Object>();

        mListSectionOne.addAll(mListThisWeek);
        mListSectionTwo.addAll(mListNextWeek);

        SectionObject s1 = new SectionObject();
        if (mListThisWeek.size() > 0) {
            s1.setmSectionLabel("This Week Posts");
        } else {
            s1.setmSectionLabel("No posts for this week");
        }
        s1.setmSectionSize(mListThisWeek.size());
        s1.setmListData(mListSectionOne);
        listSection.add(s1);

        SectionObject s2 = new SectionObject();
        if (mListNextWeek.size() > 0) {
            s2.setmSectionLabel("Next Week Posts");
        } else {
            s2.setmSectionLabel("No posts for next week");
        }
        s2.setmSectionSize(mListNextWeek.size());
        s2.setmListData(mListSectionTwo);
        listSection.add(s2);

        PostAdapter mAdapter = new PostAdapter(WallActivity.this, listSection);
        mLvPost.setAdapter(mAdapter);
    }

    public void logout_Pressed(View view) {
        afterClickLogout();
    }

    private void afterClickLogout() {
        SharedPreferencesUtils.clearPreference(mContext);
        GlobalUtils.setCurrentUserObj(null);
        actionsMenu.collapse();
        finish();
        startActivity(new Intent(WallActivity.this, LoginActivity.class));
    }

    private void goToAddPostActivity() {
        startActivity(new Intent(WallActivity.this, NewPostActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left);
    }


    private void getContents(final PullToRefreshBase<ListView> refresh) {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_TAG, Constants.GET_CONTENT_TAG);


        mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_CONTENT, params, new AsyncCallback() {
            @Override
            public void done(String result) {
                Log.e(TAG, result);
                if (refresh != null) {
                    refresh.onRefreshComplete();
                } else {
                    GlobalUtils.dismissLoadingProgress();
                }
                GlobalUtils.mListPost = new ArrayList<PostObject>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has(Constants.RESULT_GET_CONTENT_TYPE_POST)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constants.RESULT_GET_CONTENT_TYPE_POST);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                            PostObject post = new PostObject();
                            post.parseJSONPost(jsonObjectItem);
                            GlobalUtils.mListPost.add(post);
                        }


                    }
                    populateList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void progress() {
                if (refresh == null) {
                    GlobalUtils.showLoadingProgress(mContext);
                }
            }

            @Override
            public void onInterrupted(Exception e) {
                if (refresh != null) {
                    refresh.onRefreshComplete();
                } else {
                    GlobalUtils.dismissLoadingProgress();
                }
            }

            @Override
            public void onException(Exception e) {
                if (refresh != null) {
                    refresh.onRefreshComplete();
                } else {
                    GlobalUtils.dismissLoadingProgress();
                }
            }
        });

        mRequestAsync.execute();

    }


    private List<PostObject> getSegmentedList(String startDate_str, String endDate_str, List<PostObject> lList) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        Date convertedDate = new Date();
        Date startDate = new Date();
        Date endDate = new Date();

        List<PostObject> filteredDate = new ArrayList<PostObject>();
        Iterator<PostObject> iterator = lList.iterator();

        for (PostObject temp : lList) {
            try {
                convertedDate = dateFormat.parse(temp.getPost_date());
                startDate = dateFormat.parse(startDate_str);
                endDate = dateFormat.parse(endDate_str);
                if (temp.getPost_date().equals(startDate_str) || temp.getPost_date().equals(endDate_str) || (convertedDate.before(endDate)) && (convertedDate.after(startDate))) //here "date2" and "date1" must be converted to dateFormat
                {
                    filteredDate.add(temp); // You can use these filtered ArrayList
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return filteredDate;
    }

}
