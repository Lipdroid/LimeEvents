package apom.org.researchLime.limeevents.adapters.holders;

import apom.org.researchLime.limeevents.customViews.CustomFontTextView;

/**
 * Created by lipuhossain on 4/5/17.
 */

public class UserHolder {

    public CustomFontTextView name = null;
    public CustomFontTextView organization = null;
    public CustomFontTextView email = null;
    public CustomFontTextView phone = null;



    public void resetView() {
        name.setText("");
        organization.setText("");
        email.setText("");
        phone.setText("");

    }
}
