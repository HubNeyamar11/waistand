package com.example.waistand.tetrisgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.waistand.R;
import com.example.waistand.SubActivity;
import com.example.waistand.player.*;
import com.example.waistand.tetris.Score;

import static android.content.Context.MODE_PRIVATE;

public class TetrisViewForN8 extends View implements PlayerObserver {
    private String LOG_TAG = this.getClass().getName();
    private Context mContext;
    private Player player;
    private PlayerInput playerInput;
    private PlayerUI playerUI;
    private Score playerScore;

    private int highScore = 0;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private boolean isSetScale = false;
    private boolean needScaleCanvas = false;

    private static final int EMPTY_MESSAGE = 0;
    private HandlerThread playerHandlerThread;
    private Handler playerHandler;

    private SoundPool soundPool;
    private static MediaPlayer mp;



    public TetrisViewForN8(Context context, Player player) {
        super(context);
        this.mContext = context;
        isSetScale = false;
        needScaleCanvas = false;

        createPlayerThread();

        loadHIghScore();

        this.player = player;
        playerInput = new PlayerInputImplForN8();
        playerUI = new PlayerUIForN8(mContext);
        playerScore = new PlayerScoreImpl();
        playerScore.setHighScore(this.highScore);


        player.setInputDevice(playerInput);
        player.setView(playerUI);
        player.setSCore(playerScore);
        player.register(this);
        player.init();
    }

    private void createPlayerThread() {
        mp = MediaPlayer.create(getContext() , R.raw.soundtetris);
        Log.d(LOG_TAG,"createPlayerThread");
        playerHandlerThread = new HandlerThread("Player Processing Thread");
        playerHandlerThread.start();
        playerHandler = new Handler(playerHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg){
                int getArray [] = ((SubActivity)SubActivity.context_sub).getarr;
                int left1 =0; int right=0; int front=0; int back=0;


               /* for (int l=0; l<14 ; l++){
                    left1= left1 +getArray[l];
                }
                for (int r=14; r<28;r++){
                    right = right + getArray[r];
                    //Log.d("왼쪽", "? "+left1);
                }
                for(int f=9; f<19; f++){
                    front = front +getArray[f];
                }
                for(int b=24; b<28; b++){
                    back = back + getArray[b];
                }*/

                for (int l=0; l<10 ; l++){
                    left1= left1 +getArray[l];

                }
                // Log.i("왼쪽1", "테스트: "+left1);
                for (int r=10; r<20;r++){
                    right = right +getArray[r];
                }
                for (int f=7; f<13; f++){
                    front = front +getArray[f];
                }
                for(int b=0; b<3 ; b++){
                    back = back+ getArray[b];
                }
                for(int b=17; b<20; b++){
                    back = back + getArray[b];
                }

                if (player != null && player.isPlayState()) {
                    player.MoveDown();
                    int gameSpeed = 700 - (player.getScore() / 10000);

                    if(front>back && front>200){
                        player.rotate();
                    }
                    else if (left1>right && left1>450){
                        player.MoveLeft();
                    }
                    else if (left1<right && right>1000){
                        player.MoveRight();
                    }

                    if (playerHandler.hasMessages(EMPTY_MESSAGE)) {
                        playerHandler.removeMessages(EMPTY_MESSAGE);
                    }


                    playerHandler.sendEmptyMessageDelayed(EMPTY_MESSAGE, gameSpeed);
                }
            }
        };
    }


    public void setScreenSize(int w, int h) {
        playerUI.setScreenSize(w, h);
    }

    public void startGame() {
        mp.start();
        playerHandler.sendEmptyMessage(EMPTY_MESSAGE);
    }

    public void pauseGame() {
        Log.d(LOG_TAG, "pauseGame");
        if (playerHandler.hasMessages(EMPTY_MESSAGE)) {
            playerHandler.removeMessages(EMPTY_MESSAGE);
            Log.d(LOG_TAG, "Removed event");
        }
        if (player != null) {
            player.pause();
            mp.stop();
        }

        if (playerHandlerThread != null) {
            playerHandlerThread.quit();
            mp.stop();
        }
        saveScore();
    }

    public void resumeGame() {
        Log.d(LOG_TAG, "resumeGame");
        createPlayerThread();
    }

    public void update() {
        Log.d(LOG_TAG, "View.update()");
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        if (playerUI == null) {
            return;
        }

        if (!isSetScale) {
            scaleX = canvas.getWidth() / 1080f;
            scaleY = canvas.getHeight() / 1920f;
            isSetScale = true;

            if (scaleX <= 0.999f) {
                needScaleCanvas = true;
                Log.d(LOG_TAG, "Resolution of device is smaller than 1080");
            }
        }

        if (needScaleCanvas) {
            canvas.scale(scaleX, scaleY);
        }

        playerUI.onDraw(canvas);
    }




    public boolean onTouchEvent(MotionEvent event) {
        Log.d(LOG_TAG, ">> X: " + event.getX() + " Y: " + event.getY());

        if (playerInput == null) {
            return false;
        }
        if (MotionEvent.ACTION_DOWN != event.getAction()) {
            return false;
        }

        Log.d(LOG_TAG, ">> scaleX: " + scaleX + " scaleY: " + scaleY);

        int x = (int) (event.getX());
        int y = (int) (event.getY());

        if (needScaleCanvas) {
            x = (int) (x / scaleX);
            y = (int) (y / scaleY);
        }

        Log.d(LOG_TAG, ">> X: " + x + " Y: " + y);
        Log.i("s", "onKeydown: ");


        Log.i("array", "onTouchEvent: " );
        return playerInput.touch(x, y);

    }

    private void loadHIghScore() {
        Log.d(LOG_TAG, "load()");
        SharedPreferences pref = mContext.getSharedPreferences("choboTetris", MODE_PRIVATE);
        this.highScore = pref.getInt("highscore", 0);
    }

    private void saveScore() {
        Log.d(LOG_TAG, "saveScore()");
        if (this.highScore > player.getHighScore()) {
            return;
        }
        SharedPreferences pref = mContext.getSharedPreferences("choboTetris", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        edit.putInt("highscore", player.getHighScore());
        Log.d("TAG", "saveScore: " + player.getHighScore());
        edit.commit();
    }


    public int returnValue() {
        Log.d(LOG_TAG, "pressBack()" + player.getHighScore());
        return player.getHighScore();
    }


}
