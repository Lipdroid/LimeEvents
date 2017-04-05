package apom.org.researchLime.limeevents.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apom.org.researchLime.limeevents.R;
import apom.org.researchLime.limeevents.adapters.holders.PostHolder;
import apom.org.researchLime.limeevents.adapters.holders.UserHolder;
import apom.org.researchLime.limeevents.customViews.CustomFontTextView;
import apom.org.researchLime.limeevents.models.PostObject;
import apom.org.researchLime.limeevents.models.UserObject;
import apom.org.researchLime.limeevents.utils.CorrectSizeUtil;
import apom.org.researchLime.limeevents.utils.MultipleScreen;

/**
 * Created by lipuhossain on 4/5/17.
 */

public class AdminAdapter extends BaseAdapter {

    private Context mContext = null;
    private List<UserObject> mListData = null;
    private UserHolder mHolder = null;
    private CorrectSizeUtil mCorrectSize = null;

    public AdminAdapter(Context context, List<UserObject> mListData) {
        this.mContext = context;
        this.mListData = mListData;
        mCorrectSize = CorrectSizeUtil.getInstance((Activity) context);

    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.item_user, null);
            mHolder = new UserHolder();

            mHolder.name = (CustomFontTextView) convertView.findViewById(R.id.name);
            mHolder.organization = (CustomFontTextView) convertView.findViewById(R.id.organization);
            mHolder.email = (CustomFontTextView) convertView.findViewById(R.id.email);
            mHolder.phone = (CustomFontTextView) convertView.findViewById(R.id.phone);


            mCorrectSize.correctSize(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (UserHolder) convertView.getTag();
        }
        mHolder.resetView();
        new MultipleScreen(mContext);
        MultipleScreen.resizeAllView((ViewGroup) convertView);

        Object item = mListData.get(position);
        UserObject user = null;

        if (item instanceof UserObject) {
            user = (UserObject) item;
            showUSer(user);
        }


        return convertView;
    }

    private void showUSer(UserObject user) {

        if (user.getUserName() != null) {
            mHolder.name.setText(user.getUserName());
        }

        if (user.getUserOrganization() != null) {
            mHolder.organization.setText(user.getUserOrganization());

        }
        if (user.getUserEmail() != null) {
            mHolder.email.setText(user.getUserEmail());

        }
        if (user.getUserPhone() != null) {
            mHolder.phone.setText(user.getUserPhone());

        }
    }
}
