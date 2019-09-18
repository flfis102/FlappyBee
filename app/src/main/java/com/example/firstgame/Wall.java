package com.example.firstgame;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Wall {
    private TopPillar topP;
    private BotPillar botP;
    private int gap;
    private boolean end = false;




    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Wall(){

        gap = (int)((Math.random() * 800) + 500);
        int rand = (int)((Math.random() * screenHeight/3) + screenHeight/6 );
        topP = new TopPillar(rand);
        botP = new BotPillar(rand + gap);
    }

    public Wall(int left){

        gap = (int)((Math.random() * 600) + 300);
        int rand = (int)((Math.random() * screenHeight/3) + screenHeight/6 );
        topP = new TopPillar(rand, screenWidth + left);
        botP = new BotPillar(rand + gap, screenWidth + left);
    }

    public void draw(Canvas canvas, Paint paint){
        topP.draw(canvas, paint);
        botP.draw(canvas, paint);
    }

    public void update(){
        if(!end){
            topP.update();
            botP.update();
        }
    }

    public boolean check(){
        if(topP.check() &&botP.check()){
            return true;
        }
        return false;
    }

    public boolean hit(CharacterSprite bird){
        if(topP.hit(bird) || botP.hit(bird)){
            return true;
        }
        return false;
    }

    public int getRight() {
        return topP.getRight();
    }

    public int getPoint(CharacterSprite bird){
        if(topP.passed(bird)) {
            return 1;
        }
        return 0;
    }

    public void end() {
        end = true;
    }

    public void pause() {
        topP.pause();
        botP.pause();
    }

    public void unpause() {
        topP.unpause();
        botP.unpause();
    }
}
