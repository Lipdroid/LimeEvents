package apom.org.researchLime.limeevents;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import apom.org.researchLime.limeevents.adapters.SearchPostAdapter;
import apom.org.researchLime.limeevents.apis.RequestAsyncTask;
import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.interfaces.AsyncCallback;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.GlobalUtils;

public class ShowAllActivity extends AppCompatActivity {
    private PullToRefreshListView listView = null;
    CorrectSizeUtil mCorrectSize = null;
    private RequestAsyncTask mRequestAsync = null;
    private Context mContext = null;
    private SearchPostAdapter mAdapter = null;
    private AutoCompleteTextView auto_search = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        mContext = ShowAllActivity.this;
        listView = (PullToRefreshListView) findViewById(R.id.list_view_fam);
        auto_search = (AutoCompleteTextView) findViewById(R.id.auto_search);
        auto_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                auto_search.dismissDropDown();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                auto_search.dismissDropDown();

            }

            @Override
            public void afterTextChanged(Editable s) {
                auto_search.dismissDropDown();

            }
        });
        getContents(null);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getContents(refreshView);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void getContents(final PullToRefreshBase<ListView> refresh) {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_TAG, Constants.GET_CONTENT_TAG);


        mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_CONTENT, params, new AsyncCallback() {
            @Override
            public void done(String result) {
                Log.e("ShowAllActivity", result);
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

    private void populateList() {
        mAdapter = new SearchPostAdapter(mContext, GlobalUtils.mListPost);
        listView.setAdapter(mAdapter);
        auto_search.setAdapter(mAdapter);
    }

    public void back_press(View view) {
        finish();
    }
}
