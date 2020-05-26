package com.example.waistand;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.waistand.GameView.screenRatioX;
import static com.example.waistand.GameView.screenRatioY;

public class Flight {
    int toShoot = 0;
    boolean isGoingUp = false;
    boolean go_left = false;
    boolean go_right = false;

    int x, y, width, height, wingCounter = 0, shootCounter = 1;
    Bitmap flight1, flight2, shoot1, shoot2, shoot3, shoot4, shoot5, dead, spaceship;
    private GameView gameView;

    Flight (GameView gameView, int screenY, int screenX, Resources res) {

        this.gameView = gameView;

        spaceship = BitmapFactory.decodeResource(res, R.drawable.spaceship);

        width = spaceship.getWidth();
        height = spaceship.getHeight();

        width /= 16;
        height /= 16;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        spaceship = Bitmap.createScaledBitmap(spaceship, width, height, false);



        y = screenY / 1;

        x = screenX / 2;

    }

    Bitmap getFlight () {

        if (toShoot != 0) {

            if (shootCounter == 1) {
                shootCounter++;
                return shoot1;
            }

            if (shootCounter == 2) {
                shootCounter++;
                return shoot2;
            }

            if (shootCounter == 3) {
                shootCounter++;
                return shoot3;
            }

            if (shootCounter == 4) {
                shootCounter++;
                return shoot4;
            }

            shootCounter = 1;
            toShoot--;
            gameView.newBullet();

            return shoot5;
        }

        if (wingCounter == 0) {
            wingCounter++;
            return flight1;
        }
        wingCounter--;

        return flight2;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead () {
        return dead;
    }
}
