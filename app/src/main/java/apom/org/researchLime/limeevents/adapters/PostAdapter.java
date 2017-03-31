package apom.org.researchLime.limeevents.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import apom.org.researchLime.limeevents.R;
import apom.org.researchLime.limeevents.adapters.holders.PostHolder;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.models.SectionObject;
import apom.org.researchLime.limeevents.utils.MultipleScreen;
import apom.org.researchLime.limeevents.widgets.SectionedBaseAdapter;

/**
 * Created by lipuhossain on 3/31/17.
 */

public class PostAdapter extends SectionedBaseAdapter {

    private Context mContext = null;
    private Activity mActivity = null;
    private ArrayList<SectionObject> mListSection = null;
    private PostHolder mHolder = null;

    DisplayImageOptions option = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true)
            .showImageOnLoading(R.color.line_config)
            .showImageOnFail(R.color.line_config)
            .build();

    private int NEXT_WEEK_POST_MAX_SIZE = 3;
    private int CURRENT_WEEK_POST_MAX_SIZE = 5;
    private TextView tvHeaderTitle = null;
    private View.OnClickListener mOnClickListener = null;

    public PostAdapter(Context context, ArrayList<SectionObject> listSection) {

        mContext = context;
        mListSection = listSection;
        mActivity = (Activity) context;
    }

    @Override
    public Object getItem(int section, int position) {
        return mListSection.get(section).getmListData().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return mListSection.size();
    }

    @Override
    public int getCountForSection(int section) {

        if (section == 0) {
            if (mListSection.get(section).getmListData().size() < CURRENT_WEEK_POST_MAX_SIZE) {
                CURRENT_WEEK_POST_MAX_SIZE = mListSection.get(section).getmListData().size();
            }
            return CURRENT_WEEK_POST_MAX_SIZE;
        } else if (section == 1) {
            if (mListSection.get(section).getmListData().size() < NEXT_WEEK_POST_MAX_SIZE) {
                NEXT_WEEK_POST_MAX_SIZE = mListSection.get(section).getmListData().size();
            }
            return NEXT_WEEK_POST_MAX_SIZE;
        }

        return mListSection.get(section).getmListData().size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {

        LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflator.inflate(R.layout.item_post, null);
        mHolder = new PostHolder();

        mHolder.postTitle = (TextView) convertView.findViewById(R.id.post_title);
        mHolder.postOrganizer = (TextView) convertView.findViewById(R.id.post_organizer);
        mHolder.postRate = (TextView) convertView.findViewById(R.id.post_rate);
        mHolder.postLocation = (TextView) convertView.findViewById(R.id.post_address);
        mHolder.postImage = (ImageView) convertView.findViewById(R.id.post_image);

        new MultipleScreen(mContext);
        MultipleScreen.resizeAllView((ViewGroup) convertView);

        Object item = mListSection.get(section).getmListData().get(position);
        PostObject post = null;

        if (item instanceof PostObject) {
            post = (PostObject) item;
            showPost(post, position);
        }

        mHolder.btnMain.setContentDescription("main");
        mHolder.tvFooterViewAll.setContentDescription("viewAll");


        // set listener
        initListener(section, position);
        setListenerForView();

        return convertView;
    }

    private void setListenerForView() {
        mHolder.btnMain.setOnClickListener(mOnClickListener);
        mHolder.tvFooterViewAll.setOnClickListener(mOnClickListener);
    }

    private void initListener(final int section, final int position) {
        mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getContentDescription() == null) {
                    return;
                } else if (v.getContentDescription().equals("main")) {
                    // afterClickMain(section, position);
                } else if (v.getContentDescription().equals("viewAll")) {
                    // afterClickViewAll(section, position);
                }

            }
        };
    }

    private void showPost(PostObject post, int position) {
        mHolder.postTitle.setText(post.getPost_title());
        mHolder.postLocation.setText(post.getPost_address());
        mHolder.postRate.setText(post.getPost_rate());
        mHolder.postOrganizer.setText(post.getPost_organizer());
        ImageLoader.getInstance().displayImage(post.getPost_image(), mHolder.postImage, option);

    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

        LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflator.inflate(R.layout.list_section_header, null);

        tvHeaderTitle = (TextView) convertView.findViewById(R.id.notification_header_tv_title);

        new MultipleScreen(mContext);
        MultipleScreen.resizeAllView((ViewGroup) convertView);


        String title = mListSection.get(section).getmSectionLabel();

        // set header text
        tvHeaderTitle.setText(title);

        return convertView;
    }
}
