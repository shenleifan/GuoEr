package com.example.lifan.drawanddrag;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lifan.drawanddrag.fragments.DrawAndDragFragment;

public class DrawAndDragActivity extends SimpleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DrawAndDragFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_draw_and_drag);
    }
}
