

package com.zncm.easysc.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.zncm.component.ormlite.DatabaseHelper;
import com.zncm.easysc.R;
import com.zncm.easysc.data.base.ScData;
import com.zncm.easysc.global.SharedApplication;
import com.zncm.utils.DeviceUtil;
import com.zncm.utils.L;
import com.zncm.utils.ViewUtils;
import com.zncm.utils.exception.CheckedExceptionHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Callable;

//欢迎界面
public class SplashActivity extends Activity {
    private Handler handler;
    private RuntimeExceptionDao<ScData, Integer> dao = null;
    private TextView tvProgress;
    private DatabaseHelper databaseHelper = null;

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(SharedApplication.getInstance().ctx, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        ViewUtils.setTextView(this, R.id.tvAppInfo,
                getResources().getString(R.string.app_name) + " " + DeviceUtil.getVersionName(SplashActivity.this));
        try {
            dao = getHelper().getScDataDao();
            if (dao.countOf() == 0) {
                tvProgress.setVisibility(View.VISIBLE);
                tvProgress.setText("诗词数据初始化中...");
                InsertData insertData = new InsertData();
                insertData.execute("");
            } else {
                tvProgress.setVisibility(View.GONE);
                handler = new Handler();
                handler.postDelayed(startAct, 1500);
            }

        } catch (Exception e) {
            CheckedExceptionHandler.handleException(e);
        }


    }


    Runnable startAct = new Runnable() {

        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this, MainTabsPager.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

    };


    class InsertData extends AsyncTask<String, Integer, Integer> {


        protected Integer doInBackground(String... params) {
            int status = 0;
            String str = readFile();
            L.i("str>>>" + str);
            ArrayList<String> list = new ArrayList<String>();
            final String[] temp = str.split("\n");
            if (dao == null) {
                dao = getHelper().getScDataDao();
            }
            dao.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    int size = temp.length;
                    for (int i = 0; i < size; i++) {
                        publishProgress((int) ((i / (float) size) * 100));
                        String[] fields = temp[i].split(",");
                        ScData data = new ScData();
                        data.setType(fields[0]);
                        data.setAuthor(fields[1]);
                        data.setTitle(fields[2]);
                        data.setContent(fields[3]);
                        data.setComment(fields[4]);
                        data.setStatus(0);
//                        for (int j = 0; j < fields.length; j++) {
//                            L.i("data>>" + fields[j]);
//                        }
                        dao.create(data);
                    }
                    return null;
                }
            });

            return status;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvProgress.setText("诗词数据初始化中...(" + values[0] + "%)");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            handler = new Handler();
            handler.postDelayed(startAct, 500);

        }
    }


    public String readFile() {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(
                    R.raw.scdata);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String lineTXT = null;
            while ((lineTXT = reader.readLine()) != null) {
                sb.append(lineTXT);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
