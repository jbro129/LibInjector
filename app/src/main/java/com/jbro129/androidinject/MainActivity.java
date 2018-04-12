package com.jbro129.androidinject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jbro129 on 4/2/2018.
 */

public class MainActivity extends AppCompatActivity
{

    protected void OnCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);

        String dataDir = getApplicationInfo().dataDir;
        String libPath = "/lib/libil2cpp.so";
        Inject.InjectNow(dataDir + libPath);
    }
}
