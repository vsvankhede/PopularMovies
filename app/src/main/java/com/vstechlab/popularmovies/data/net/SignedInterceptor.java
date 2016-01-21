package com.vstechlab.popularmovies.data.net;

import android.support.annotation.NonNull;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Interceptor to prepare header parameter ready with api key.
 */
public class SignedInterceptor implements Interceptor {
    private final String mApiKey;

    public SignedInterceptor(@NonNull String apiKey) {
        // Todo Null check
        this.mApiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        HttpUrl.Builder authorizedUrlBuilder = oldRequest.httpUrl().newBuilder()
                .scheme(oldRequest.httpUrl().scheme())
                .host(oldRequest.httpUrl().host());

        authorizedUrlBuilder.addQueryParameter(MoviesApi.PARAM_API_KEY, mApiKey);

        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
