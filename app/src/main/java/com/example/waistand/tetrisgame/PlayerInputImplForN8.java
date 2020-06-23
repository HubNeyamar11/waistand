package com.example.waistand.tetrisgame;

import android.util.Log;

import com.example.waistand.SubActivity;
import com.example.waistand.player.PlayerInput;

public class PlayerInputImplForN8 extends PlayerInput {
    private final String TAG = this.getClass().getName();

    public PlayerInputImplForN8() {
        startX = 80;
        startY = 80;
    }



    public boolean touch(int touchX, int touchY  ) {
        if (player == null) {
            return true;
        }




        if ((touchX > 190) && (touchY > 400)
                && (touchX < 500) && (touchY < 500)) {
            Log.d(TAG, "touch: ");
            clickStartButton();
            return true;
        }

        if ((touchX > 700) && (touchY > 50)
                && (touchY < 250)) {
            Log.d(TAG, "Round button: play()");
            play();
            return true;
        }

        final int BOARD_HEIGHT = 20;

        if (touchX > startX + 750 &&
                touchY > startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT + 100 &&
                touchX < startX + 950 &&
                touchY < startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT + 100 + 200

        ) {
            Log.d(TAG, "rotate()회전회전회전회전회전회전회전회전회전회전회전회전회전회전회전회전회전회전");
            rotate();
            return true;
        }

        if (touchX > startX &&
                touchY > startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT + 100 &&
                touchX < startX + 200 &&
                touchY < startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT + 100 + 200


        ) {
            Log.d(TAG, "left()왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼왼");
            left();
            return true;
        }

        if (touchX > startX + 250 &&
                touchY > startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT + 100 &&
                touchX < startX + 450 &&
                touchY < startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT + 100 + 200) {
            Log.d(TAG, "bottom() down()");
            bottom();
            down();
            return true;
        }

        if (touchX > startX + 500 &&
                touchY > startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT + 100 &&
                touchX < startX + 700 &&
                touchY < startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT + 100 + 200

            ) {
            Log.d(TAG, "right()오른오른오른오른오른오른오른오른오른오른오른오른오른오른오른오른오른오른");
            right();
            return true;
        }

        if (touchX > startX + 750 &&
                touchY > startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT - 200 &&
                touchX < startX + 950 &&
                touchY < startY + BLOCK_IMAGE_SIZE * BOARD_HEIGHT) {
            Log.d(TAG, "down()");
            down();
            return true;
        }

        return false;
    }



   /* public void mob(){
        if (player == null) {

        }
        int getArray [] = ((SubActivity)SubActivity.context_sub).getarr;

        int left1 =0; int right=0; int front=0; int back=0;

        for (int l=0; l<14 ; l++){
            left1= left1 +getArray[l];

        }

        for (int r=14; r<28;r++){
            right = right + getArray[r];
            Log.d("왼쪽", "? "+left1);

        }



        for(int f=9; f<19; f++){
            front = front +getArray[f];
        }

        for(int b=24; b<28; b++){
            back = back + getArray[b];
        }

        if (left1>right && left1>450){
            left();

        }
        else if (left1<right && right>1000){
            right();
        }

        else if(front>back  ){

            rotate();

            //newBullet();


        }
    }*/


}
