package com.cog.Dropinn.Utils.Retrofit;

/**
 * Created by test on 13/2/18.
 */

import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pallavahooja on 16/05/16.
 */

public abstract class RetryableCallback<T> implements Callback<T> {

    private int totalRetries = 3;
    private static final String TAG = RetryableCallback.class.getSimpleName();
    private final Call<T> call;
    private int retryCount = 0;

    public RetryableCallback(Call<T> call, int totalRetries) {
        this.call = call;
        this.totalRetries = totalRetries;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.i(TAG, "onFailure: OOOSOSOS");

        if (!APIHelper.isCallSuccess(response))
            if (retryCount++ < totalRetries) {
                Log.v(TAG, "Retrying API Call -  (" + retryCount + " / " + totalRetries + ")");
                retry();
            } else
                onFinalResponse(call, response);
        else
            onFinalResponse(call,response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        if (retryCount++ < totalRetries) {
            Log.v(TAG, "Retrying API Call -  (" + retryCount + " / " + totalRetries + ")");
            retry();
        } else
            onFinalFailure(call, t);
    }

    public void onFinalResponse(Call<T> call, Response<T> response) {

    }

    public void onFinalFailure(Call<T> call, Throwable t) {
    }

    private void retry() {
        call.clone().enqueue(this);
    }
}