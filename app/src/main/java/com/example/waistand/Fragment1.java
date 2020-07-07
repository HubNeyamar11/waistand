package com.example.waistand;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;

import android.content.Context;
import android.content.Intent;


import android.os.Build;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    public static Context context_frag1;



    private BluetoothSPP btSpp;
    private Button btnConnect;
    private Button btnSend;


    int value=0;

    NotificationManager manager;
    NotificationCompat.Builder builder;


    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";

    public String arrayNums[] = new String[20]; //string 배열
    public int array[] = new int[arrayNums.length]; //int 배열

    public ToggleButton vibrate;
    //String[] array;
    //ArrayList<Integer> idList = new ArrayList<>();

    //public static Context context_main;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);


       //context_main = getContext();

        vibrate = rootView.findViewById(R.id.toggleButton);

        context_frag1= getActivity();



        Button stop = rootView.findViewById(R.id.btnStop);

        //Button request = rootView.findViewById(R.id.btnRequest);

        btnConnect = rootView.findViewById(R.id.btnConnect);
        btnSend = rootView.findViewById(R.id.btnSend);

        btSpp = new BluetoothSPP(getContext());
        Log.i("BLUETOOTHENABLE", ""+btSpp);

        if(!btSpp.isBluetoothAvailable()){ // 블루투스 사용 불가
            Toast.makeText(getActivity().getApplicationContext(), "블루투스를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vibrate.isChecked()){
                    vibrate.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.vibration)
                    );
                }else{

                    vibrate.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.silent)
                    );
                }
            }
        });


        // 데이터 수신 이벤트 리스너
        btSpp.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataReceived(byte[] data, String message) { // 아두이노에서 넘어오는 데이터를 수신
                //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                Log.i("수신?", "onDataReceived: ");

                /*String[] array;
                ArrayList<Integer> idList = new ArrayList<>();

                //텍스트 레이아웃 배치 값 받아오기
                for (int i=0 ; i<28 ; i++) {
                    idList.add(getResources().getIdentifier("tv" + i, "id", Objects.requireNonNull(getActivity()).getPackageName()));
                }
                array = message.split(",");

                ArrayList<Integer> arrayInt = new ArrayList<>();
                int sensorVal;

                for (int i=1; i<array.length; i++) {
                    arrayInt.add(Integer.parseInt(array[i]));

                    sensorVal = arrayInt.get(i-1);

                    //값에따라 색상
                    if (sensorVal<70){
                        ((TextView) getView().findViewById(k)).setBackgroundResource(R.color.mood);
                    }
                    else if (sensorVal[i]>70 && array[i]<151){
                        ((TextView) getView().findViewById(k)).setBackgroundResource(R.color.tomato);
                    }else if (array[i]>150 && array[i]<231){
                        ((TextView) getView().findViewById(k)).setBackgroundResource(R.color.colorOrange);
                    }else if (array[i]>1231 ){
                        ((TextView) getView().findViewById(k)).setBackgroundColor(Color.RED);
                    }

                    ((TextView) getView().findViewById(k)).setText(arrayNums[i]);
                }*/
                //---------------------------------------------------------------------------
                //텍스트 레이아웃 배치 값 받아오기

                for (int i = 0; i < 20; i++) {


                    int k = getResources().getIdentifier("tv" + i, "id", getActivity().getPackageName());
                    arrayNums = message.split(",");

                    // int배열로 변환
                    array[i]=Integer.parseInt(arrayNums[i]);

                    ((SubActivity)SubActivity.context_sub).getarr[i]= array[i];



                    //값에따라 색상

                    //기존
                    /*if (array[i]<70){
                        ((TextView) rootView.findViewById(k)).setBackgroundResource(R.color.mood);
                    }
                    else if (array[i]>70 && array[i]<151){
                        ((TextView) rootView.findViewById(k)).setBackgroundResource(R.color.tomato);
                    }else if (array[i]>150 && array[i]<231){
                        ((TextView) rootView.findViewById(k)).setBackgroundResource(R.color.colorOrange);
                    }else if (array[i]>230 ){
                        ((TextView) rootView.findViewById(k)).setBackgroundColor(Color.RED);
                    }

                    ((TextView) rootView.findViewById(k)).setText(arrayNums[i]);

                }*/

                    if (array[i]<101){
                        ((TextView) rootView.findViewById(k)).setBackgroundResource(R.color.mood);

                    }
                    else if (array[i]>100 && array[i]<221){
                        ((TextView) rootView.findViewById(k)).setBackgroundResource(R.color.tomato);
                    }else if (array[i]>220 && array[i]<351){
                        ((TextView) rootView.findViewById(k)).setBackgroundResource(R.color.colorOrange);
                    }else if (array[i]>350 ){
                        ((TextView) rootView.findViewById(k)).setBackgroundResource(R.color.colorte);
                    }

                    ((TextView) rootView.findViewById(k)).setText(arrayNums[i]);

                }






                //왼쪽 오른 쪽 값
                /*TextView tvright = getView().findViewById(R.id.tvRight);
                TextView tvrleft = getView().findViewById(R.id.tvLeft);*/
                int left1 =0;  int left2 =0; int left3=0;
                int right=0; int right2=0; int rifht3=0;

                int front=0;
                int back=0;


                //--------------기존 방석 -------------
                /*for (int l=0; l<14 ; l++){
                    left1= left1 +array[l];

                }
                Log.i("왼쪽1", "테스트: "+left1);
                Log.i("왼쪽2", "테스트: "+left2);
                Log.i("왼쪽3", "테스트: "+left3);

                for (int r=14; r<28;r++){
                    right = right + array[r];
                }
                for (int f=9; f<19; f++){
                    front = front +array[f];
                }
                for(int b=0; b<4 ; b++){
                    back = back+ array[b];
                }
                for(int b=24; b<28; b++){
                    back = back + array[b];
                }
                Log.i("front", "앞에값 "+ front);
                Log.i("back", "뒤에값 "+back);

                if (left1>right&& left1>450 && vibrate.isChecked()  ){
                    //푸시 알림
                    showNotiL();
                    //btSpp.send("qwer",true);
                    //Toast.makeText(getActivity().getApplicationContext(),"바른 자세로 앉아 주세요! ",Toast.LENGTH_SHORT).show();
                }
                else if (left1<right  && right>1000 &&vibrate.isChecked() ){
                    showNotiR();
                    //btSpp.send("qwer",true);
                    //Toast.makeText(getActivity().getApplicationContext(),"바른 자세로 앉아주세요! ",Toast.LENGTH_SHORT).show();
                }
                else if(front>50  && front<150 && back>50 && vibrate.isChecked()) {
                    showNotiF();
                    //btSpp.send("qwer",true);
                }
                else if(back<10 && front>150 &&vibrate.isChecked()){
                    showNotiB();
                    //btSpp.send("100",true);
                }*/


                for (int l=0; l<10 ; l++){
                    left1= left1 +array[l];

                }
                //오른쪽 앞줄 3개
                for(int le=10; le<13; le++){
                    left2 = left2 + array[le];
                }

                //왼쪽 앞줄 3개
                for(int ri=7; ri<10; ri++){
                    right2 = right2 + array[ri];
                }

               // Log.i("왼쪽1", "테스트: "+left1);
                for (int r=10; r<20;r++){
                    right = right + array[r];
                }
                for (int f=7; f<13; f++){
                    front = front +array[f];
                }
                for(int b=0; b<3 ; b++){
                    back = back+ array[b];
                }
                for(int b=17; b<20; b++){
                    back = back + array[b];
                }
                Log.i("left", " 왼쪽값 "+ left1);
                Log.i("right", "오른쪽값 "+ right);
                Log.i("front", "앞에값 "+ front);
                Log.i("back", "뒤에값 "+back);

                if (left1>right&& right<1500 && vibrate.isChecked()  ){
                    //푸시 알림
                    showNotiL();
                    //btSpp.send("qwer",true);
                    //Toast.makeText(getActivity().getApplicationContext(),"바른 자세로 앉아 주세요! ",Toast.LENGTH_SHORT).show();
                }
                else if (left1<right  && left1<1700 &&vibrate.isChecked() ){
                    showNotiR();
                    //btSpp.send("qwer",true);
                    //Toast.makeText(getActivity().getApplicationContext(),"바른 자세로 앉아주세요! ",Toast.LENGTH_SHORT).show();
                }
                else if(front>1900 && back>800 &&back<1500 && vibrate.isChecked()) {
                    showNotiF();
                    //btSpp.send("qwer",true);
                }
                else if(back<200 && front>1550 &&vibrate.isChecked()){
                    showNotiB();
                    //btSpp.send("100",true);
                }




            }
        });

        // 연결 이벤트 리스너
        btSpp.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) { // 연결 됐을 때
                Toast.makeText(getActivity().getApplicationContext(), name + "에 연결됨 \n" + address, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceDisconnected() {
                Toast.makeText(getActivity().getApplicationContext(), "연결 해제됨", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceConnectionFailed() { //연결 실패
                Toast.makeText(getActivity().getApplicationContext(), "연결 실패!", Toast.LENGTH_SHORT).show();
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener(){ // 연결 버튼 누름-> 블루투스 연결시도
            @Override
            public void onClick(View v) {
                if(btSpp.getServiceState() == BluetoothState.STATE_CONNECTED)
                    btSpp.disconnect();
                else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNumHandler.removeMessages(0);
                tenSaveHandler.removeMessages(0);
                AppDatabase db= Room.databaseBuilder(getActivity(),AppDatabase.class,"sensorData-db").allowMainThreadQueries().build();
                db.dataDao().clearAll();

                btSpp.disconnect();

            }
        });
        
        /*request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "전송되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });*/



        return rootView;
    }

    // nowDate 변수에 값을 저장한다.
    String formatDate;

    Handler makeNumHandler = new Handler() {
        public void handleMessage(Message msg) {
            value++;

            // 현재시간을 msec 으로 구한다.
            long now = System.currentTimeMillis();
            // 현재시간을 date 변수에 저장한다.
            Date date = new Date(now);

            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



            formatDate= sdfNow.format(date);

            TextView dateNow;
            dateNow = (TextView) getView().findViewById(R.id.tvCurretTime);
            dateNow.setText(formatDate);

            // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
            makeNumHandler.sendEmptyMessageDelayed(0,1000);
        }
    };

    Handler tenSaveHandler = new Handler() {
        public void handleMessage(Message msg){
            saveDb();


            tenSaveHandler.sendEmptyMessageDelayed(0,5000);
        }
    };




    //푸시 알림 메소드
    public void showNotiL() {
        builder = null;
        manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE); //버전 오레오 이상일 경우
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID);
            //하위 버전일 경우
        }
        else{
            builder = new NotificationCompat.Builder(getActivity());
        }
        //알림창 제목
        builder.setContentTitle("Waistand");
        // 알림창 메시지
        builder.setContentText("왼쪽으로 치우쳤어요!");
        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.sensor);
        //알림창 터치시 삭제
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);
    }

    public void showNotiR(){
        builder = null; manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE); //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID);
            //하위 버전일 경우
        }else{
            builder = new NotificationCompat.Builder(getActivity());
        }
        //알림창 제목
        builder.setContentTitle("Waistand");
        // 알림창 메시지
        builder.setContentText("오른쪽으로 치우쳤어요!");
        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.sensor);
        //알림창 터치시 삭제
        builder.setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND);

        Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);

    }
    public void showNotiF(){
        builder = null; manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE); //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID);
            //하위 버전일 경우
        }else{
            builder = new NotificationCompat.Builder(getActivity());
        }
        //알림창 제목
        builder.setContentTitle("Waistand");
        // 알림창 메시지
        builder.setContentText("앞으로 숙이셨어요!");
        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.sensor);
        //알림창 터치시 삭제
        builder.setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND);

        Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);

    }
    public void showNotiB(){
        builder = null; manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE); //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID);
            //하위 버전일 경우
        }else{
            builder = new NotificationCompat.Builder(getActivity());
        }
        //알림창 제목
        builder.setContentTitle("Waistand");
        // 알림창 메시지
        builder.setContentText("방석에 걸터 앉으셨어요!");
        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.sensor);
        //알림창 터치시 삭제
        builder.setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND);

        Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);

    }

    public void showNotiCrossL(){
        builder = null; manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE); //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID);
            //하위 버전일 경우
        }else{
            builder = new NotificationCompat.Builder(getActivity());
        }
        //알림창 제목
        builder.setContentTitle("Waistand");
        // 알림창 메시지
        builder.setContentText("왼쪽 다리를 꼬으셨어요!");
        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.sensor);
        //알림창 터치시 삭제
        builder.setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND);

        Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);

    }

    public void showNotiCrossR(){
        builder = null; manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE); //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID);
            //하위 버전일 경우
        }else{
            builder = new NotificationCompat.Builder(getActivity());
        }
        //알림창 제목
        builder.setContentTitle("Waistand");
        // 알림창 메시지
        builder.setContentText("왼쪽 다리를 꼬으셨어요!");
        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.sensor);
        //알림창 터치시 삭제
        builder.setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND);

        Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);

    }



    public void saveDb(){
        /// 디비 테스트 결과 값 출력하기 위해 선언
        //TextView mResultTv = getView().findViewById(R.id.tvResult);

        //////////디비
        AppDatabase db= Room.databaseBuilder(getActivity(),AppDatabase.class,"sensorData-db").allowMainThreadQueries().build();


        db.dataDao().insert(new Data(Arrays.toString(arrayNums), formatDate));
        //mResultTv.setText(db.dataDao().getAll().toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        btSpp.stopService();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(! btSpp.isBluetoothEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        }else {
            if(! btSpp.isServiceAvailable()){
                btSpp.setupService();
                btSpp.startService(BluetoothState.DEVICE_OTHER);
                // DEVICE_OTHER : 아두이노 기기 - 안드로이드 연결
                setup();
            }
        }
    }

    public void setup(){ // 블루투스 서비스가 시작되고 난 뒤 실행되는 메서드
        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { // btnSend(출력) 버튼을 누르면 Text가 아두이노에 전송됨

                btSpp.send("1", true);
                makeNumHandler.sendEmptyMessage(0);  // 핸들러에 메시지 전달
                tenSaveHandler.sendEmptyMessage(0);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE){ // 연결할 디바이스리스트 반환 요청 시
            // BluetoothState.REQUEST_CONNECT_DEVICE : DeviceListActivity가 연결할 디바이스 리스트를 반환
            if(resultCode == Activity.RESULT_OK)
                btSpp.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT){ //
            if(requestCode == Activity.RESULT_OK){
                btSpp.setupService();
                btSpp.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(), "블루투스가 활성화 되지 않았음", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}

