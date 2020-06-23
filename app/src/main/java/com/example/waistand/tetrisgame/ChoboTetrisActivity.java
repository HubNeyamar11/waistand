package com.example.waistand.tetrisgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.waistand.Fragment2;
import com.example.waistand.SubActivity;
import com.example.waistand.player.Player;
import com.example.waistand.player.PlayerImpl;

import javax.security.auth.Subject;

public class ChoboTetrisActivity extends Activity {
	private TetrisViewForN8 twN8;



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

	    Log.e("Test", "W" + screenWidth + " H" + screenHeight);

		int BOARD_WIDTH = 10;
		int BOARD_HEIGHT = 20;

		Player player = new PlayerImpl(BOARD_WIDTH, BOARD_HEIGHT);

		twN8 = new TetrisViewForN8(this, player);
		twN8.setScreenSize(screenWidth,screenHeight);
		setContentView(twN8);

	}

	@Override
	public void onPause() {
		super.onPause();
		if (twN8 != null) {
			twN8.pauseGame();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (twN8 != null) {
			twN8.resumeGame();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//((Fragment2) Fragment2.context_main).fragment2();




	}
}