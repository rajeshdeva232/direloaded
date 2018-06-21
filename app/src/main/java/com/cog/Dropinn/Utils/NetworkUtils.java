package com.cog.Dropinn.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 17/07/17.
 */

public class NetworkUtils {
    private static final String host = "api.linkedin.com";
    public static final String LINKEDIN_URL = "https://" + host
            + "/v1/people/~:" +
            "(email-address,formatted-name,phone-numbers,picture-urls::(original))";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
