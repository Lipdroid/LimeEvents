package apom.org.researchLime.limeevents.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by lipuhossain on 3/31/17.
 */

public class PostObject implements Parcelable{

    private String post_id = null;
    private String post_title = null;
    private String post_address = null;
    private String post_rate= null;
    private String post_organizer= null;
    private String organizer_id = null;
    private String post_image = null;
    private String post_time = null;
    private String post_date = null;

    public PostObject(Parcel in) {
        this();
        readFromParcel(in);
    }

    public PostObject() {

    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    private String post_contact_info = null;
    private String post_description = null;


    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_contact_info() {
        return post_contact_info;
    }

    public void setPost_contact_info(String post_contact_info) {
        this.post_contact_info = post_contact_info;
    }

    public String getOrganizer_id() {
        return organizer_id;
    }

    public void setOrganizer_id(String organizer_id) {
        this.organizer_id = organizer_id;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_address() {
        return post_address;
    }

    public void setPost_address(String post_address) {
        this.post_address = post_address;
    }

    public String getPost_rate() {
        return post_rate;
    }

    public void setPost_rate(String post_rate) {
        this.post_rate = post_rate;
    }

    public String getPost_organizer() {
        return post_organizer;
    }

    public void setPost_organizer(String post_organizer) {
        this.post_organizer = post_organizer;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }


    public void parseJSONPost(JSONObject jsonObject) {

        try {
            if(jsonObject.has("post_id")) {
                setPost_id(jsonObject.optString("post_id"));
            }
            if(jsonObject.has("post_title")) {
                setPost_title(jsonObject.optString("post_title"));
            }
            if(jsonObject.has("post_address")) {
                setPost_address(jsonObject.optString("post_address"));
            }
            if(jsonObject.has("post_rate")) {
                setPost_rate(jsonObject.optString("post_rate"));
            }
            if(jsonObject.has("post_organizer")) {
                setPost_organizer(jsonObject.optString("post_organizer"));
            }
            if(jsonObject.has("organizer_id")) {
                setOrganizer_id(jsonObject.optString("organizer_id"));
            }
            if(jsonObject.has("post_image")) {
                setPost_image(jsonObject.optString("post_image"));
            }
            if(jsonObject.has("post_time")) {
                setPost_time(jsonObject.optString("post_time"));
            }
            if(jsonObject.has("post_date")) {
                setPost_date(jsonObject.optString("post_date"));
            }
            if(jsonObject.has("post_contact_info")) {
                setPost_contact_info(jsonObject.optString("post_contact_info"));
            }
            if(jsonObject.has("post_description")) {
                setPost_description(jsonObject.optString("post_description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static final Creator<PostObject> CREATOR = new Creator<PostObject>() {
        @Override
        public PostObject createFromParcel(Parcel in) {
            return new PostObject(in);
        }

        @Override
        public PostObject[] newArray(int size) {
            return new PostObject[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(post_id);
        dest.writeString(post_title);
        dest.writeString(post_address);
        dest.writeString(post_rate);
        dest.writeString(post_organizer);
        dest.writeString(organizer_id);
        dest.writeString(post_image);
        dest.writeString(post_time);
        dest.writeString(post_date);
        dest.writeString(post_contact_info);
        dest.writeString(post_description);

    }
    public void readFromParcel(Parcel in) {
        this.post_id = in.readString();
        this.post_title = in.readString();
        this.post_address = in.readString();
        this.post_rate = in.readString();
        this.post_organizer = in.readString();
        this.organizer_id = in.readString();
        this.post_image = in.readString();
        this.post_time = in.readString();
        this.post_date = in.readString();
        this.post_contact_info = in.readString();
        this.post_description = in.readString();

    }
}
