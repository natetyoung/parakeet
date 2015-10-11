package io.github.natetyoung.parakeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onBackPressed()
    {
        Intent back = new Intent(HelpActivity.this, MainActivity.class);
        startActivity(back);
    }

}
