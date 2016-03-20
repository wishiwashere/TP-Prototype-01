package com.pigottlaura.www.wishiwashere;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting the XML file which will determine the layout of this
        // activity i.e. make the sketch appear full screen etc.
        setContentView(R.layout.activity_main);

        // Adding the Processing Sketch as a new Fragment (as in Processing
        // 3.0, PApplet extends Fragment)
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new Sketch();
        fragmentManager.beginTransaction()
                .replace(R.id.processingSketch, fragment)
                .commit();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Main Activity", "Resumed - myNum1 = " + Sketch.myNum1);
    }

}
