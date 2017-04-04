package apom.org.researchLime.limeevents.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import apom.org.researchLime.limeevents.PostDetailsActivity;
import apom.org.researchLime.limeevents.R;
import apom.org.researchLime.limeevents.adapters.holders.PostHolder;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.MultipleScreen;

/**
 * Created by lipuhossain on 4/5/17.
 */

public class SearchPostAdapter extends BaseAdapter implements Filterable {
    private Activity mActivity = null;
    CorrectSizeUtil mCorrectSizeUtil = null;


    private Context mContext = null;
    private ArrayList<PostObject> suggestions = new ArrayList<>();
    private Filter filter = new CustomFilter();

    private List<PostObject> mListData = null;
    private List<PostObject> mListData_all = null;

    private PostHolder mHolder = null;
    private CorrectSizeUtil mCorrectSize = null;

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true).cacheOnDisc(true).cacheOnDisk(true).resetViewBeforeLoading(true)
            .displayer(new FadeInBitmapDisplayer(400))
//            .showImageOnLoading(R.color.text_dark)
            .showImageForEmptyUri(R.color.text_dark)
            .showImageOnFail(R.color.text_dark)
            .build();
    private View.OnClickListener mOnClickListener;

    public SearchPostAdapter(Context context, List<PostObject> mListData) {
        mContext = context;
        this.mListData = mListData;
        mListData_all = mListData;
        mCorrectSize = CorrectSizeUtil.getInstance((Activity) context);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.item_post, null);
            mHolder = new PostHolder();

            mHolder.postTitle = (TextView) convertView.findViewById(R.id.post_title);
            mHolder.postOrganizer = (TextView) convertView.findViewById(R.id.post_organizer);
            mHolder.postRate = (TextView) convertView.findViewById(R.id.post_rate);
            mHolder.postLocation = (TextView) convertView.findViewById(R.id.post_address);
            mHolder.tvFooterViewAll = (TextView) convertView.findViewById(R.id.notification_footer_tv_show_all);
            mHolder.postImage = (ImageView) convertView.findViewById(R.id.post_image);
            mHolder.btnMain = (CardView) convertView.findViewById(R.id.card_view);

            mCorrectSize.correctSize(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder = (PostHolder) convertView.getTag();
        }
        mHolder.resetView();
        new MultipleScreen(mContext);
        MultipleScreen.resizeAllView((ViewGroup) convertView);

        Object item = mListData.get(position);
        PostObject post = null;

        if (item instanceof PostObject) {
            post = (PostObject) item;
            showPost(post, position);
        }

        mHolder.btnMain.setContentDescription("main");
        mHolder.tvFooterViewAll.setVisibility(View.GONE);


        // set listener
        initListener(position);
        setListenerForView();

        return convertView;
    }

    private void showPost(PostObject post, int position) {
        mHolder.postTitle.setText(post.getPost_title());
        mHolder.postLocation.setText(post.getPost_address());
        mHolder.postRate.setText(post.getPost_rate());
        mHolder.postOrganizer.setText(post.getPost_organizer());

        if (post.getPost_image() != null && !post.getPost_image().equals("")) {
            ImageLoader.getInstance().displayImage(post.getPost_image(), mHolder.postImage, options);
        }
    }

    private void setListenerForView() {
        mHolder.btnMain.setOnClickListener(mOnClickListener);
    }

    private void initListener(final int position) {
        mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getContentDescription() == null) {
                    return;
                } else if (v.getContentDescription().equals("main")) {
                    afterClickMain(position);
                }

            }
        };
    }

    private void afterClickMain(int position) {
        PostObject postObj = mListData.get(position);
        Intent intent = new Intent(mContext, PostDetailsActivity.class);
        intent.putExtra(PostObject.class.toString(), postObj);
        ((Activity) mContext).startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();
            try {
                if (mListData != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                    for (int i = 0; i < mListData_all.size(); i++) {
                        if (mListData_all.get(i).getPost_title().toLowerCase().startsWith(constraint.toString().toLowerCase()) || (mListData_all.get(i).getPost_organizer() != null && mListData_all.get(i).getPost_organizer().toLowerCase().startsWith(constraint.toString().toLowerCase()))) { // Compare item in original list if it contains constraints.
                            suggestions.add(mListData_all.get(i)); // If TRUE add item in Suggestions.
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                mListData = suggestions;
                notifyDataSetChanged();
            } else {
                if(constraint != null){
                    mListData = suggestions;
                }else {
                    mListData = mListData_all;
                }
                notifyDataSetInvalidated();
            }
        }
    }
}
