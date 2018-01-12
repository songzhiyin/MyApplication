package com.szy.lib.network.Glide;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by songzhiiyn on 2017/3/23.
 */

public class OkHttpUrlLoader implements ModelLoader {

    @Override
    public DataFetcher getResourceFetcher(Object model, int width, int height) {
        return new OkHttpStreamFetcher(client, (GlideUrl) model);
    }

    /**
     * The default factory for {@link OkHttpUrlLoader}s.
     */
    public static class Factory implements ModelLoaderFactory {
        private static volatile OkHttpClient internalClient;
        private OkHttpClient client;
        private static OkHttpClient getInternalClient() {
            if (internalClient == null) {
                synchronized (Factory.class) {
                    if (internalClient == null) {
                        internalClient = new OkHttpClient();
                    }
                }
            }
            return internalClient;
        }
        /**
         * Constructor for a new Factory that runs requests using a static singleton client.
         */
        public Factory() {
            this(getInternalClient());
        }
        /**
         * Constructor for a new Factory that runs requests using given client.
         */
        public Factory(OkHttpClient client) {
            this.client = client;
        }

        @Override
        public ModelLoader build(Context context, GenericLoaderFactory factories) {
            return new OkHttpUrlLoader(client);
        }

        @Override
        public void teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
    private final OkHttpClient client;
    public OkHttpUrlLoader(OkHttpClient client) {
        this.client = client;
    }


}
