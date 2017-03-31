package apom.org.researchLime.limeevents.apis;

import android.app.Activity;
import android.content.Context;

import java.util.HashMap;

import apom.org.researchLime.limeevents.R;
import apom.org.researchLime.limeevents.classes.MySafeAsyncTask;
import apom.org.researchLime.limeevents.interfaces.AsyncCallback;
import apom.org.researchLime.limeevents.utils.GlobalUtils;


public class RequestAsyncTask extends MySafeAsyncTask<String> {
	private HashMap<String, Object> mParameters = null;
	private AsyncCallback mAsyncCallback = null;
	private int mTypeOfRequest = 0;
	private Context mContext = null;
	
	public RequestAsyncTask(Context context, int typeOfRequest, HashMap<String, Object> parameters, AsyncCallback asyncCallback) {
		mTypeOfRequest = typeOfRequest;
		mParameters = parameters;
		mAsyncCallback = asyncCallback;
		mContext = context;
	}
	
	@Override
	protected void onPreExecute() throws Exception {
		mAsyncCallback.progress();
	}
	
	@Override
	public String call() throws Exception {
		if(GlobalUtils.isNetworkConnected()) {
			RequestData getDataObj = new RequestData(mContext);
			return getDataObj.getData(mTypeOfRequest, mParameters);
		} else {
			((Activity)mContext).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					String title = mContext.getResources().getString(R.string.dialog_error_title);
					String body = mContext.getResources().getString(R.string.dialog_no_internet_connection);
					GlobalUtils.showInfoDialog(mContext, title, body, null, null);
				}
			});
			return null;
		}
	}
	
	@Override
	protected void onSuccess(String result) throws Exception {
		mAsyncCallback.done(result);
	}
	
	@Override
	protected void onException(Exception e) throws RuntimeException {
		mAsyncCallback.onException(e);
	}
	
	@Override
	protected void onInterrupted(Exception e) {
		mAsyncCallback.onInterrupted(e);
	}

}
