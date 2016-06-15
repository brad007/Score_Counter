package com.fire.scorecounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private int mCounter_A;
    private int mCounter_B;
    private TextView mScoreTextView_A;
    private TextView mScoreTextView_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseScreen();
    }

    private void initialiseScreen() {
        //instantiating views
        findViewById(R.id.one_point_A).setOnClickListener(this);
        findViewById(R.id.three_points_A).setOnClickListener(this);
        findViewById(R.id.five_points_A).setOnClickListener(this);

        findViewById(R.id.one_point_B).setOnClickListener(this);
        findViewById(R.id.three_points_B).setOnClickListener(this);
        findViewById(R.id.five_points_B).setOnClickListener(this);

        findViewById(R.id.clear_all).setOnClickListener(this);

        mScoreTextView_A = (TextView) findViewById(R.id.score_A);
        mScoreTextView_B = (TextView) findViewById(R.id.score_B);
    }

    private void addScore_A(int score) {
        mCounter_A += score;
        mScoreTextView_A.setText(mCounter_A + "");
    }

    private void addScore_B(int score) {
        mCounter_B += score;
        mScoreTextView_B.setText(mCounter_B + "");
    }

    private void clearAll() {
        addScore_A(-1 * mCounter_A);
        addScore_B(-1 * mCounter_B);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one_point_A:
                addScore_A(1);
                break;
            case R.id.three_points_A:
                addScore_A(3);
                break;
            case R.id.five_points_A:
                addScore_A(5);
                break;
            case R.id.one_point_B:
                addScore_B(1);
                break;
            case R.id.three_points_B:
                addScore_B(3);
                break;
            case R.id.five_points_B:
                addScore_B(5);
                break;
            case R.id.clear_all:
                clearAll();
                break;
        }
    }
}