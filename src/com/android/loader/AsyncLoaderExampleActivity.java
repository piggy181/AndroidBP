package com.android.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA. User: darakok Date: 3/5/12 Time: 3:22 PM To change this template use File | Settings | File
 * Templates.
 */
public class AsyncLoaderExampleActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Integer> {

    private final int DEMO_LOADER_ID = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_async);

        // Call initLoader to attach to running loader only works when called from within onCreate method.
        if (savedInstanceState != null && null != getSupportLoaderManager().getLoader(DEMO_LOADER_ID)
                && getSupportLoaderManager().getLoader(DEMO_LOADER_ID).isStarted()) {

            getSupportLoaderManager().initLoader(DEMO_LOADER_ID, null, AsyncLoaderExampleActivity.this);
        }

        findViewById(R.id.start_loader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loader<Integer> loader = getSupportLoaderManager().getLoader(DEMO_LOADER_ID);

                if (null == loader) {
                    getSupportLoaderManager().initLoader(DEMO_LOADER_ID, null, AsyncLoaderExampleActivity.this);
                }
                else if (loader != null) {
                    getSupportLoaderManager().restartLoader(DEMO_LOADER_ID, null, AsyncLoaderExampleActivity.this);
                }
                else {
                    getSupportLoaderManager().restartLoader(DEMO_LOADER_ID, null, AsyncLoaderExampleActivity.this);
                }
            }
        });

        findViewById(R.id.check_status).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Loader<Integer> loader = getSupportLoaderManager().getLoader(DEMO_LOADER_ID);

                final TextView statusText = (TextView) findViewById(R.id.status);
                if (loader == null) {
                    statusText.setText("Loader is null");
                }
                else if (loader.isStarted()) {
                    statusText.setText("Loader is still running");
                }
                else {
                    statusText.setText("not started and not reset");
                }
            }
        });
    }

    @Override
    public Loader<Integer> onCreateLoader(int i, Bundle bundle) {
        final TextView statusText = (TextView) findViewById(R.id.status);
        statusText.setText("Loader started");

        return new DemoAsyncLoader(AsyncLoaderExampleActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<Integer> integerLoader, Integer integer) {
        final TextView statusText = (TextView) findViewById(R.id.status);
        statusText.setText("Loader finished");

        integerLoader.reset();
    }

    @Override
    public void onLoaderReset(Loader<Integer> integerLoader) {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    static private class DemoAsyncLoader extends AsyncTaskLoader<Integer> {

        public DemoAsyncLoader(Context context) {
            super(context);
        }

        @Override
        public Integer loadInBackground() {
            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException e) {
                e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
            }

            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading(); // To change body of overridden methods use File | Settings | File Templates.
            forceLoad();
        }
    }
}