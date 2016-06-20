package com.fire.scorecounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private TextView mScoreTextView_A;
    private TextView mScoreTextView_B;

    private DatabaseReference teamARef;
    private DatabaseReference teamBRef;
    private long mCounter_A;
    private long mCounter_B;

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

        teamARef = FirebaseDatabase.getInstance().getReference("teamA");
        teamBRef = FirebaseDatabase.getInstance().getReference("teamB");

        teamARef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    mCounter_A = (long) dataSnapshot.getValue();
                    mScoreTextView_A.setText(mCounter_A + "");
                } else {
                    mScoreTextView_A.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        teamBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    mCounter_B = (long) dataSnapshot.getValue();
                    mScoreTextView_B.setText(mCounter_B + "");
                }else{
                    mScoreTextView_B.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //SingleValueEventListener
        //ValueEventListener

    }

    private void addScore_A(long score) {
        for (int i = 0; i < score; i++) {
            teamARef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    if (mutableData.getValue() != null) {
                        long num = (long) mutableData.getValue();
                        mutableData.setValue(num + 1);
                        return Transaction.success(mutableData);
                    } else {
                        teamARef.setValue(1);
                        return Transaction.success(mutableData);
                    }
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                }
            });
        }
    }

    private void addScore_B(final long score) {
        teamBRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                long num = (long) mutableData.getValue();
                mutableData.setValue(num + score);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
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