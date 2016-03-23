package com.pigottlaura.www.wishiwashere;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import processing.data.XML;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new Sketch();
        fragmentManager.beginTransaction()
                .replace(R.id.processingSketch, fragment)
                .commit();
    }
}
