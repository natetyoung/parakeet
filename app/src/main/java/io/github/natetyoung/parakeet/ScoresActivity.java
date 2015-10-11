package io.github.natetyoung.parakeet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        TextView score1 = (TextView) findViewById(R.id.scoreNum1);
        TextView score2 = (TextView) findViewById(R.id.scoreNum2);
        TextView score3 = (TextView) findViewById(R.id.scoreNum3);
        TextView score4 = (TextView) findViewById(R.id.scoreNum4);
        TextView score5 = (TextView) findViewById(R.id.scoreNum5);
        TextView score6 = (TextView) findViewById(R.id.scoreNum6);
        TextView score7 = (TextView) findViewById(R.id.scoreNum7);
        TextView score8 = (TextView) findViewById(R.id.scoreNum8);
        TextView score9 = (TextView) findViewById(R.id.scoreNum9);
        TextView score10 = (TextView) findViewById(R.id.scoreNum10);

        SharedPreferences scores = getSharedPreferences("Scores", 0);
        score1.setText(scores.getInt("1", 0));
        score2.setText(scores.getInt("2", 0));
        score3.setText(scores.getInt("3", 0));
        score4.setText(scores.getInt("4", 0));
        score5.setText(scores.getInt("5", 0));
        score6.setText(scores.getInt("6", 0));
        score7.setText(scores.getInt("7", 0));
        score8.setText(scores.getInt("8", 0));
        score9.setText(scores.getInt("9", 0));
        score10.setText(scores.getInt("10", 0));
    }

    public void onBackPressed()
    {
        Intent back = new Intent(ScoresActivity.this, MainActivity.class);
        startActivity(back);
    }

}
