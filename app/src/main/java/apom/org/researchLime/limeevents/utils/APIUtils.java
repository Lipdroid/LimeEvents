package apom.org.researchLime.limeevents.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import apom.org.researchLime.limeevents.constants.Constants;
import apom.org.researchLime.limeevents.models.UserObject;


public class APIUtils {
    public static Context sContext = null;

    public APIUtils(Context context) {
        sContext = context;
    }

    public static HashMap<String, Object> parseJSON(int type, String jsonString) {
        HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
        try {
            JSONObject mainJsonObj = new JSONObject(jsonString);
            JSONObject jsonObj = new JSONObject();
            JSONArray jsonArr = new JSONArray();
            String error = new String();
            String result = new String();
            int code = 0;

            switch (type) {
                case Constants.REQUEST_GET_USER_BY_MAIL:
                    if (mainJsonObj.has(Constants.TAG_USER)) {
                        if(mainJsonObj.getString("success").equals("1")){
                            UserObject userObj = null;
                            userObj = (UserObject) parseUser(mainJsonObj.getJSONObject(Constants.TAG_USER));
                            returnHashMap.put(Constants.TAG_USER, userObj);
                        }
                    }else{
                        if(mainJsonObj.getString("success").equals("0")){
                            returnHashMap.put(Constants.TAG_ERROR, mainJsonObj.getString(Constants.TAG_ERROR_MSG));
                        }
                    }
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
                 e.printStackTrace();
        }

        return returnHashMap;
    }

    private static UserObject parseUser(JSONObject src) {
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


}
