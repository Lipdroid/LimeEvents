package apom.org.researchLime.limeevents;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import apom.org.researchLime.limeevents.utils.HttpClientImageDownloader;
import apom.org.researchLime.limeevents.utils.ImageUtils;
import apom.org.researchLime.limeevents.utils.NutraBaseImageDecoder;


/**
 * Created by lipuhossain on 1/25/17.
 */

public class LimeApplication extends MultiDexApplication {

    private final static String LOG_TAG = Application.class.getSimpleName();
    private static Context sContext = null;

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Application.onCreate - Initializing application...");
        super.onCreate();
        initializeApplication();
        sContext = LimeApplication.this;
        new ImageUtils(sContext);
        Log.d(LOG_TAG, "Application.onCreate - Application initialized OK");
        initImageLoader(getApplicationContext());

    }

    public static Context LimeApplication() {
        return sContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(base);
    }

    private void initializeApplication() {

        // Initialize the AWS Mobile Client

        // ... Put any application-specific initialization logic here ...
    }

    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        HttpParams params = new BasicHttpParams();
        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        // Default connection and socket timeout of 10 seconds. Tweak to taste.
        HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
        HttpConnectionParams.setSoTimeout(params, 10 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        // Don't handle redirects -- return them to the caller. Our code
        // often wants to re-POST after a redirect, which we must do ourselves.
        HttpClientParams.setRedirecting(params, false);
        // Set the specified user agent and register standard protocols.
        HttpProtocolParams.setUserAgent(params, "some_randome_user_agent");
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager manager = new ThreadSafeClientConnManager(params, schemeRegistry);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(1)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheExtraOptions(480, 320, null)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .imageDownloader(new HttpClientImageDownloader(context, new DefaultHttpClient(manager, params)))
                .imageDecoder(new NutraBaseImageDecoder(true))
                .imageDownloader(new BaseImageDownloader(context))
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    // uncaught exception handler variable
    private Thread.UncaughtExceptionHandler defaultUEH;

    // handler listener
//    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler =
//            new Thread.UncaughtExceptionHandler() {
//                @Override
//                public void uncaughtException(Thread thread, Throwable ex) {
//
//                    // here I do logging of exception to a db
//                    PendingIntent myActivity = PendingIntent.getActivity(getApplicationContext(),
//                            192837, new Intent(getApplicationContext(), SplashActivity.class),
//                            PendingIntent.FLAG_ONE_SHOT);
//
//                    AlarmManager alarmManager;
//                    alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                            15000, myActivity );
//                    System.exit(2);
//
//                    // re-throw critical exception further to the os (important)
//                    defaultUEH.uncaughtException(thread, ex);
//                }
//            };
//
//    public LimeApplication() {
//        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
//        // setup handler for uncaught exception
//        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
//    }


}