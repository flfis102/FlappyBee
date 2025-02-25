package com.example.firstgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;


class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private CharacterSprite characterSprite;

    private Wall wall1;
    private Wall wall2;


    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private int distance = screenWidth/3;

    private Context myContext;
    private int speedup = 1;

    private boolean wentToScore = false;
    private MainActivity mainActivity;

    private Bitmap bgImage;
    private Bitmap smImage;

    private Bitmap bgImage2;
    private Bitmap smImage2;

    private Bitmap bgImageBrick;
    private Bitmap smImageBrick;

    private int widthWalls = 250;

    private MediaPlayer mp;
    private int counter  = 0;
    private int background = 0;

    public GameView(Context context, MainActivity mainActivity) {
        super(context);
        myContext = context;
        this.mainActivity = mainActivity;
        getHolder().addCallback(this);
        setFocusable(true);
        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.background_new);
        smImage = Bitmap.createScaledBitmap(bgImage,screenWidth,screenHeight,true);

        bgImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.background_new_2);
        smImage2 = Bitmap.createScaledBitmap(bgImage2,screenWidth,screenHeight,true);

        bgImageBrick = BitmapFactory.decodeResource(getResources(), R.drawable.bricks_new);
        smImageBrick = Bitmap.createScaledBitmap(bgImageBrick,widthWalls,screenHeight,true);
        mp = MediaPlayer.create(getContext(), R.raw.music_background);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        allNew();
        thread = new GameThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
        if(!mp.isPlaying()){
            mp.start();
            mp.setLooping(true);
        }

    }



    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while (retry) {
            try {
                mp.stop();
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }





    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        if(!mp.isPlaying()){
            mp.start();
            mp.setLooping(true);
        }

        if (canvas != null) {



            if(counter > 30 ){
                counter = 0;
                if(background == 0){
                    background = 1;
                } else if(background == 1){
                    background = 0;
                }

            }

            if(background == 0){
                canvas.drawBitmap(smImage,0, 0, null);
            }
            if(background == 1){
                canvas.drawBitmap(smImage2,0, 0, null);
            }

            counter++;

            Paint paint = new Paint();

            if(wall1.check()){
                if((screenWidth - wall2.getRight())> distance) {
                    wall1 = new Wall(getResources(), smImageBrick);
                }else {
                    wall1 = new Wall(getResources(),distance - (Math.abs(screenWidth - wall2.getRight())), smImageBrick);
                }
            }
            if(wall2.check()){
                if((screenWidth - wall1.getRight())> distance) {
                    wall2 = new Wall(getResources(), smImageBrick);
                }else {
                    wall2 = new Wall(getResources(),distance - (Math.abs(screenWidth - wall1.getRight())), smImageBrick);
                }
            }

            wall1.update();
            wall2.update();



            characterSprite.addScore(wall1.getPoint(characterSprite));
            characterSprite.addScore(wall2.getPoint(characterSprite));


            if(characterSprite.getScore() != 0 && characterSprite.getScore() % 10 == 0) {
                speedup = characterSprite.getScore() / 10;
            }
                wall1.speedup(speedup);
                wall2.speedup(speedup);


            characterSprite.update();
            characterSprite.draw(canvas);

            wall1.draw(canvas,paint);
            wall2.draw(canvas,paint);

            if(wall1.hit(characterSprite) || wall2.hit(characterSprite) || characterSprite.fell()){
                characterSprite.kill();
                wall1.end();
                wall2.end();

                paint.setTextSize(70);
                paint.setColor(Color.rgb(255,0,0));
                canvas.drawText("GAME OVER", screenWidth/2 - 200, screenHeight/2, paint);



                if(wentToScore == false) {
                    thread.setRunning(false);
                    System.out.println(thread.getState());
                    mainActivity.goToScore(characterSprite.getScore());
                    wentToScore = true;
                }

            }

            paint.setColor(Color.rgb(0,0,0));
            paint.setTextSize(50);
            canvas.drawText("Score:  " + characterSprite.getScore(), 10, screenHeight - 60, paint);

         }

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            characterSprite.press();
            restart();
            return true;
        }
        return false;
    }

    public void restart() {
        if(!characterSprite.isAlive()){

            allNew();
        }
    }

    public void allNew(){
        wentToScore = false;
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(), R.drawable.bee_final_new));
        wall1 = new Wall(getResources(), smImageBrick);
        wall2 = new Wall(getResources(),screenWidth/2, smImageBrick);

    }

    public void unpause(){
        characterSprite.unpause();
        wall1.unpause();
        wall2.unpause();
    }

    public void pause() {
        characterSprite.pause();
        wall1.pause();
        wall2.pause();
    }

}

