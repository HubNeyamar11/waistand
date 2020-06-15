package com.example.waistand;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Bird[] birds;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private int sound;
    private Flight flight;
    private GameActivity activity;
    private Background background1, background2;
    private Object Fragment1;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;
        Log.i("게임뷰 생성2222222", "onCreate: ");
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
            Log.i("이프문", "GameView: ");

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.shoot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioY = 1920f / screenY;
        screenRatioX = 1080f / screenX;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        flight = new Flight(this, screenY, screenX, getResources());

        bullets = new ArrayList<>();

//        background2.y = -screenY;     // 배경 세로로 움직이게 할때 바꿔주면 됨
        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        birds = new Bird[4];

        for (int i = 0;i < 4;i++) {

            Bird bird = new Bird(getResources());   // 새 새로 생성
            birds[i] = bird;


        }

        random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying) {
            Log.i("Run", "draw: ");
            //moving();
            update ();
            draw ();
            sleep ();


        }
    }
    private void update () {

//

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }
        // 배경 움직임 (가로)

        //moving();


        if (flight.go_left) {
            flight.x -= 30 * screenRatioX;  // 왼쪽터치 시 왼쪽으로 이동
        }
        else if (flight.go_right){
            flight.x += 30 * screenRatioX;  // 오른쪽터치 시 오른쪽으로 이동
        }
        else
            flight.x += 0;



        if (flight.y < 0)
            flight.y = 0;         // 비행기가 화면 위로 안넘어가게 하는거 (삭제 하면 됨)

        if (flight.y >= screenY - flight.height)
            flight.y = screenY - flight.height;      // 비행기가 화면 아래로 안넘어가게 하는거

        if (flight.x >= screenX - flight.width)
            flight.x = screenX - flight.width;      // 비행기가 오른화면 넘어로 안넘어가게 하는거

        if (flight.x < 0)
            flight.x = 0;                           // 비행기가 왼쪽화면 넘어로 안넘어가게 하는거



        for (Bird bird : birds) {
            if (bird.y >= screenY - bird.height) {  // 장해물이 밑에 닿았을 때
                if(!Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())) {
                    // 장해물과 비행기가 닿지 않았으면
                    score++;         // 장해물이 밑으로 넘어갔을때 점수 +
                    bird.y = 0;
                    return;
                }
            }
        }

        for (Bird bird : birds) {

            bird.y += bird.speed;      // 새가 아래로 움직이게 하는거

//            if (bird.x + bird.width < 0) {
            if (bird.y >= screenY - bird.height) {  // 새가 밑에 닿았을 때

                int bound = (int) (30 * screenRatioY);
                bird.speed = random.nextInt(bound);          // 새 내려오는 속도 조절

                if (bird.speed < 10 * screenRatioY)
                    bird.speed = (int) (10 * screenRatioY);

                bird.y = screenY;
                bird.x = random.nextInt(screenX - bird.width);   // 새 복제 좌표인듯?

                bird.wasShot = false;
            }

            if (Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())) {
                // 새랑 비행기랑 부딫히면 게임오버
                isGameOver = true;
                return;
            }

        }

    }
    private void draw () {

        if (getHolder().getSurface().isValid()) {
            Log.i("그려졌나11111", "draw: ");
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);


            for (Bird bird : birds)
                canvas.drawBitmap(bird.mercury, bird.x, bird.y, paint);   // 행성 장애물 그려냄
//                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);  // 기존 새 그려냄

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver) {
                isPlaying = false;
//                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();
                return;
            }

            //           canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);  // 기존 비행기 그려냄
            canvas.drawBitmap(flight.spaceship, flight.x, flight.y, paint);   // 우주선 그려냄

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(1000);
            activity.startActivity(new Intent(activity, GameActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    public void moving(){

        int getArray [] = ((Fragment1)Fragment1).array;
        int left1 =0; int right=0;

        for (int l=0; l<14 ; l++){
            left1= left1 +getArray[l];

        }

        for (int r=14; r<28;r++){
            right = right + getArray[r];
            Log.d("왼쪽", "? "+left1);

        }
        if (left1>right){
            flight.go_right=false;
            flight.go_left=true;

        }
        else if (left1<right){
            flight.go_left=false;
            flight.go_right=true;
        }
        Log.d("왼쪽", "left: "+left1);
        Log.d("오른쪽", "right: "+right);


    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //        flight.go_right = false;
                if (event.getX() < screenX / 2) {
                    flight.go_right = false;
                    flight.go_left = true;              // 왼쪽 터치하면 왼쪽으로 이동

                }

                if (event.getX() > screenX / 2) {
                    flight.go_left = false;
                    flight.go_right = true;             // 오른쪽 터치하면 오른쪽으로 이동
                }
//                        flight.go_left = false;
//                        flight.go_right = false;

//                    Handler mHandler = new Handler();
//                    mHandler.postDelayed(new Runnable()  {
//                        public void run() {
//                            flight.go_left = false;// 시간 지난 후 실행할 코딩
//                        }
//                    }, 200); // 0.5초후

                break;
        }
        return true;
    }


    public void newBullet() {       // 필없

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);

        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);

    }
}
