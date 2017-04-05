package apom.org.researchLime.limeevents;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apom.org.researchLime.limeevents.adapters.AdminAdapter;
import apom.org.researchLime.limeevents.adapters.SearchPostAdapter;
import apom.org.researchLime.limeevents.apis.RequestAsyncTask;
import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.customViews.CustomFontTextView;
import apom.org.researchLime.limeevents.interfaces.AsyncCallback;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.models.UserObject;
import apom.org.researchLime.limeevents.utils.APIUtils;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.GlobalUtils;

public class AdminActivity extends AppCompatActivity {

    private PullToRefreshListView listView = null;
    CorrectSizeUtil mCorrectSize = null;
    private RequestAsyncTask mRequestAsync = null;
    private Context mContext = null;
    private AdminAdapter mAdapter = null;
    List<UserObject> mListData = null;
    CustomFontTextView count = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mContext = AdminActivity.this;
        listView = (PullToRefreshListView) findViewById(R.id.list_user);
        count = (CustomFontTextView) findViewById(R.id.count);

        getUsers(null);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getUsers(refreshView);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void getUsers(final PullToRefreshBase<ListView> refresh) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_TAG, Constants.GET_USER_TAG);


        mRequestAsync = new RequestAsyncTask(mContext, Constants.GET_CONTENT_USER, params, new AsyncCallback() {
            @Override
            public void done(String result) {
                Log.e("ShowAllActivity", result);
                if (refresh != null) {
                    refresh.onRefreshComplete();
                } else {
                    GlobalUtils.dismissLoadingProgress();
                }
                mListData = new ArrayList<UserObject>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has(Constants.TAG_USER)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constants.TAG_USER);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                            UserObject user = parseJSONUser(jsonObjectItem);
                            mListData.add(user);
                        }


                    }
                    populateList();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            count.setText(mListData.size()+"");

                        }
                    });
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

    private void populateList() {

        mAdapter = new AdminAdapter(mContext, mListData);
        listView.setAdapter(mAdapter);
    }

    private UserObject parseJSONUser(JSONObject src) {
        UserObject userObj = new UserObject();
        try {
            if (((JSONObject) src).has(Constants.TAG_USER_ID)) {
                if (!((JSONObject) src).getString(Constants.TAG_USER_ID).equals("null")) {
                    userObj.setUserId(((JSONObject) src).getString(Constants.TAG_USER_ID));
                } else {
                    userObj.setUserId("");
                }

            }

            if (((JSONObject) src).has(Constants.TAG_USER_NAME)) {
                if (!((JSONObject) src).getString(Constants.TAG_USER_NAME).equals("null")) {
                    userObj.setUserName(((JSONObject) src).getString(Constants.TAG_USER_NAME));
                } else {
                    userObj.setUserName("");
                }
            }

            if (((JSONObject) src).has(Constants.TAG_EMAIL)) {
                if (!((JSONObject) src).getString(Constants.TAG_EMAIL).equals("null")) {
                    userObj.setUserEmail(((JSONObject) src).getString(Constants.TAG_EMAIL));
                } else {
                    userObj.setUserEmail("");
                }
            }

            if (((JSONObject) src).has(Constants.TAG_PHONE)) {
                if (!((JSONObject) src).getString(Constants.TAG_PHONE).equals("null")) {
                    userObj.setUserPhone(((JSONObject) src).getString(Constants.TAG_PHONE));
                } else {
                    userObj.setUserPhone("");
                }
            }


            if (!((JSONObject) src).getString(Constants.TAG_CATEGORY).equals("null")) {
                userObj.setUserCategory(((JSONObject) src).getString(Constants.TAG_CATEGORY));
            } else {
                userObj.setUserCategory("");
            }

            if (!((JSONObject) src).getString(Constants.TAG_ORGANIZATION).equals("null")) {
                userObj.setUserOrganization(((JSONObject) src).getString(Constants.TAG_ORGANIZATION));
            } else {
                userObj.setUserOrganization("");
            }
            if (!((JSONObject) src).getString(Constants.TAG_POSITION).equals("null")) {
                userObj.setUserPosition(((JSONObject) src).getString(Constants.TAG_POSITION));
            } else {
                userObj.setUserPosition("");
            }
            if (!((JSONObject) src).getString(Constants.TAG_ACTIVE).equals("null")) {
                userObj.setUserActive(((JSONObject) src).getString(Constants.TAG_ACTIVE));
            } else {
                userObj.setUserActive("");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return userObj;
    }

    public void back_admin(View view) {
        finish();
    }
}
