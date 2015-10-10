package io.github.natetyoung.parakeet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Nate on 10/10/2015.
 */
public class ParakeetView extends SurfaceView implements Runnable {
    private SurfaceHolder holder;
    private volatile boolean running;

    public ParakeetView(Context context) {
        super(context);
        holder = getHolder();
    }

    @Override
    public void run() {
        while(running){
            if(!holder.getSurface().isValid())
                continue;
            Canvas c = holder.lockCanvas();
            Paint p = new Paint();
            p.setColor(Color.BLUE);
            c.drawCircle(0,0,100,p);
            holder.unlockCanvasAndPost(c);
        }
    }

    public void resume(){
        running = true;
    }

    public void pause(){
        running = false;
    }
}
