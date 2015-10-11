package io.github.natetyoung.parakeet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Nate on 10/10/2015.
 */
public class ParakeetView extends SurfaceView implements Runnable {
    private SurfaceHolder holder;
    private Thread renderThread;
    private float dialPosition;
    private volatile boolean running;
    private byte[] song = new byte[225];
    private float score;

    public ParakeetView(Context context) {
        super(context);
        holder = getHolder();
    }

    public ParakeetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        for(int i=0; i<song.length; i++){
            song[i] = (byte)(Math.random()*5);
        }
        score = 0;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        while(running){
            if(!holder.getSurface().isValid()) {
                //Log.w("ParakeetView", "surface not valid");
                continue;
            }
            long time = (System.nanoTime()-startTime);
            Canvas c = holder.lockCanvas();
            Paint white = new Paint();
            white.setColor(Color.WHITE);
            white.setTextSize(30);
            c.drawColor(Color.BLACK);
            c.drawRect(0,348,2000,352,white);
            c.save();
            c.rotate(dialPosition, 400, 350);
            drawDial(400, 350, c);
            c.restore();
            drawTape(750, 1000 - time / 6000000, c);
            c.drawText("" + score, 0, 50, white);
            //Log.i("run","time: "+time);
            holder.unlockCanvasAndPost(c);
            score+=.0001*(Math.min(Math.abs(song[(int)(time*.83/1000000000)]*72-dialPosition),Math.abs(song[(int)(time*.83/1000000000)]*72+360-dialPosition))-90);
        }
    }

    public void drawDial(float x, float y, Canvas c){
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        Paint red = new Paint();
        red.setColor(Color.RED);
        Paint green = new Paint();
        green.setColor(Color.GREEN);
        c.drawCircle(x, y, 300, blue);
        c.drawCircle(x, y, 30, green);
        drawNotes(x, y, 0, c);
        c.rotate(72, x, y);
        drawNotes(x, y, 1, c);
        c.rotate(72, x, y);
        drawNotes(x, y, 2, c);
        c.rotate(72, x, y);
        drawNotes(x, y, 3, c);
        c.rotate(72, x, y);
        drawNotes(x, y, 4, c);
    }

    public void drawTape(float x, float y, Canvas c){
        Paint cyan = new Paint();
        cyan.setColor(Color.CYAN);
        c.drawRect(x,y,x+300,y+48000,cyan);
        for(int i=0; i<song.length; i++){
            drawNotes(x,y+40+i*48000f/song.length, song[i], c);
        }
    }

    public void drawNotes(float x, float y, int noteId, Canvas c){
        Paint magenta = new Paint();
        magenta.setColor(Color.MAGENTA);
        Paint red = new Paint();
        red.setColor(Color.RED);
        switch(noteId){
            case 0:
                c.drawCircle(x + 250, y, 20, magenta);
                c.drawCircle(x + 150, y, 20, magenta);
                break;
            case 1:
                c.drawCircle(x + 200, y, 20, magenta);
                c.drawCircle(x + 100, y, 20, magenta);
                break;
            case 2:
                c.drawCircle(x + 100, y, 20, magenta);
                break;
            case 3:
                c.drawCircle(x + 250, y, 20, magenta);
                break;
            case 4:
                c.drawCircle(x + 250, y, 20, magenta);
                c.drawCircle(x + 200, y, 20, magenta);
                c.drawCircle(x + 150, y, 20, magenta);
                c.drawCircle(x + 100, y, 20, magenta);
        }
        c.drawRect(x,y-2,x+300,y+2,red);
    }

    public void activityScrolled(MotionEvent e1, MotionEvent e2, float dx, float dy){
        float x2 = e2.getX();
        float y2 = e2.getY();
        float x1 = x2 - dx;
        float y1 = y2 - dy;
        dialPosition -= 360/Math.PI/2*(Math.atan2(y2 - 350, x2 - 400) - Math.atan2(y1 - 350, x1 - 400));
    }

    public void resume(){
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void pause(){
        running = false;
        boolean retry = true;
        while(retry) {
            try {
                renderThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //retry
            }
        }
    }
}
