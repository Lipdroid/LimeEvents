package apom.org.researchLime.limeevents.models;

/**
 * Created by lipuhossain on 3/31/17.
 */

public class PostObject {

    private String post_title = null;
    private String post_address = null;
    private String post_rate= null;
    private String post_organizer= null;
    private String post_image = null;
    private String post_time = null;
    private String post_date = null;

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
}
