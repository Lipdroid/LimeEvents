package apom.org.researchLime.limeevents.adapters.holders;

import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lipuhossain on 3/31/17.
 */

public class PostHolder {

    public TextView postTitle = null;
    public TextView postOrganizer = null;
    public TextView postRate = null;
    public TextView postLocation = null;
    public ImageView postImage = null;
    public TextView tvFooterViewAll = null;
    public CardView btnMain = null;



    public void resetView() {
        postTitle.setText("");
        postOrganizer.setText("");
        postRate.setText("");
        postLocation.setText("");
        postImage.setImageResource(android.support.v7.appcompat.R.color.material_blue_grey_800);

    }
}
