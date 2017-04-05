package apom.org.researchLime.limeevents.apis;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apom.org.researchLime.limeevents.constants.ConstantURLS;
import apom.org.researchLime.limeevents.constants.Constants;


/**
 * Request and get data from API
 *
 * @author PhanTri
 */
public class RequestData {
    private JsonParser mJsonParser = null;
    private String REQUEST_DATA_URL = null;
    private int mRestType = 0;
    private Context mContex = null;

    public RequestData(Context context) {
        mJsonParser = new JsonParser();
        mContex = context;
    }

    /**
     * TODO <br>
     * Function to get data
     *
     * @return json in string
     * @author Phan Tri
     * @date Oct 15, 2014
     */
    @SuppressWarnings("unchecked")
    public String getData(int typeOfRequest, final HashMap<String, Object> parameters) {
        ArrayList<Object> listParams = new ArrayList<Object>();
        ArrayList<NameValuePair> nameValueParams = new ArrayList<NameValuePair>();
        ArrayList<Map.Entry<String, Bitmap>> bitmapParams = new ArrayList<Map.Entry<String, Bitmap>>();
        JSONObject returnJson = null;

        switch (typeOfRequest) {

            case Constants.REQUEST_GET_USER_BY_MAIL:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = ConstantURLS.BASE_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_TAG,
                        (String) parameters.get(Constants.PARAM_TAG)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_EMAIL,
                        (String) parameters.get(Constants.PARAM_EMAIL)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD,
                        (String) parameters.get(Constants.PARAM_PASSWORD)));

                break;

            case Constants.REQUEST_REGISTER_BY_MAIL:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = ConstantURLS.BASE_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_TAG,
                        (String) parameters.get(Constants.PARAM_TAG)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_EMAIL,
                        (String) parameters.get(Constants.PARAM_EMAIL)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD,
                        (String) parameters.get(Constants.PARAM_PASSWORD)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_NAME,
                        (String) parameters.get(Constants.PARAM_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_CATEGORY,
                        (String) parameters.get(Constants.PARAM_CATEGORY)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ORGANIZATION,
                        (String) parameters.get(Constants.PARAM_ORGANIZATION)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PHONE,
                        (String) parameters.get(Constants.PARAM_PHONE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POSITION,
                        (String) parameters.get(Constants.PARAM_POSITION)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACTIVE,
                        (String) parameters.get(Constants.PARAM_ACTIVE)));
                break;

            case Constants.REQUEST_SUBMIT_POST:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = ConstantURLS.BASE_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_TAG,
                        (String) parameters.get(Constants.PARAM_TAG)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_TITLE,
                        (String) parameters.get(Constants.PARAM_POST_TITLE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ADDRESS,
                        (String) parameters.get(Constants.PARAM_ADDRESS)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_RATE,
                        (String) parameters.get(Constants.PARAM_RATE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_ORGANIZER,
                        (String) parameters.get(Constants.PARAM_POST_ORGANIZER)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_ORGANIZER_ID,
                        (String) parameters.get(Constants.PARAM_POST_ORGANIZER_ID)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_TIME,
                        (String) parameters.get(Constants.PARAM_POST_TIME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_DATE,
                        (String) parameters.get(Constants.PARAM_POST_DATE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_CONTACT_INFO,
                        (String) parameters.get(Constants.PARAM_POST_CONTACT_INFO)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_DESCRIPTION,
                        (String) parameters.get(Constants.PARAM_POST_DESCRIPTION)));

                if (parameters.containsKey(Constants.PARAM_POST_IMAGE)) {
                    // create hash map to save avatar bitmap
                    Map.Entry<String, Bitmap> hashIcon = new Map.Entry<String, Bitmap>() {

                        @Override
                        public String getKey() {
                            // TODO Auto-generated method stub
                            return Constants.PARAM_POST_IMAGE;
                        }

                        @Override
                        public Bitmap getValue() {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_POST_IMAGE);
                        }

                        @Override
                        public Bitmap setValue(Bitmap object) {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_POST_IMAGE);
                        }
                    };

                    bitmapParams.add(hashIcon);
                }
                break;
            case Constants.REQUEST_GET_CONTENT:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = ConstantURLS.BASE_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_TAG,
                        (String) parameters.get(Constants.PARAM_TAG)));
                break;

            case Constants.GET_CONTENT_USER:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = ConstantURLS.BASE_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_TAG,
                        (String) parameters.get(Constants.PARAM_TAG)));
                break;
            default:
                break;
        }

        listParams.add(nameValueParams);
        listParams.add(bitmapParams);
        // Building Parameters
        Log.e("Request URL:", REQUEST_DATA_URL);
        returnJson = mJsonParser.getJSONFromUrl(REQUEST_DATA_URL, mRestType, listParams);

        return (returnJson != null) ? returnValues(returnJson) : null;
    }

    /**
     * TODO <br>
     * Function to return values from server after check <br>
     *
     * @param returnObj
     * @return
     * @author Phan Tri
     * @date Oct 15, 2014
     */
    public String returnValues(JSONObject returnObj) {
        return returnObj.toString();
    }
}
