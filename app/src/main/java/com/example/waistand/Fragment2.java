/*
 * Fragment2 : 오늘 하루치 통계 페이지
 * */

package com.example.waistand;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.TextView;

import com.example.waistand.flight.GameActivity;
import com.example.waistand.player.Player;
import com.example.waistand.player.PlayerImpl;
import com.example.waistand.tetrisgame.ChoboTetrisActivity;
import com.example.waistand.tetrisgame.TetrisViewForN8;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment  {


    private boolean isMute;
    private BluetoothSPP bt;
    public static Context context_main;

    public TextView tetrisHigh;


    private TetrisViewForN8 twN8;


    public Fragment2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_2, container, false);





        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //게임 뷰 띄우기
        rootView.findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);

               onStop();
            }

        });

        rootView.findViewById(R.id.tetrisPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(getActivity(), ChoboTetrisActivity.class);
                startActivity(mintent);

                onStop();
            }
        });

        TextView highScoreTxt = rootView.findViewById(R.id.highScoreTxt);

        final SharedPreferences prefs = getActivity().getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("HighScore: " + prefs.getInt("highscore", 0));

        tetrisHigh = rootView.findViewById(R.id.testrisHighScoreTxt);

        context_main= getContext();
        int BOARD_WIDTH = 10;
        int BOARD_HEIGHT = 20;

        Player player = new PlayerImpl(BOARD_WIDTH, BOARD_HEIGHT);

        twN8 = new TetrisViewForN8(getContext(), player);
        setValue(twN8.returnValue());

        return rootView;
    }

    public void setValue(int num){

        tetrisHigh.setText("HighScore: "+ String.valueOf(num));
    }








}
