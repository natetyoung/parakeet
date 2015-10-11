package io.github.natetyoung.parakeet;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class PlayActivity extends AppCompatActivity {
    private ParakeetView pv;
    private GestureDetectorCompat gdc;
    private MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        bgm = MediaPlayer.create(this, R.raw.dearly_beloved);

        bgm.start();

        pv = (ParakeetView) findViewById(R.id.pv);
        final ParakeetView pvf = pv;
        pvf.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                pvf.resume();
                Log.d("SurfaceCreated", "surface created");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        gdc = new GestureDetectorCompat(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                pvf.activityScrolled(e1, e2, distanceX, distanceY);
                return super.onScroll(e1, e2, distanceX, distanceY);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        return gdc.onTouchEvent(e);

    }

    public void onPause()
    {
        super.onPause();
        bgm.pause();
    }

    public void onResume()
    {
        super.onResume();
        bgm.start();
    }

    public void onBackPressed()
    {
        Intent back = new Intent(PlayActivity.this, MainActivity.class);
        startActivity(back);
    }
}
