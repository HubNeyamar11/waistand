/*
 * MainActivity : 접속 시 첫 화면 -> 로그인 화면
 * */
package com.example.waistand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoSignUpButton).setOnClickListener(onClickListener);

        /*Button loginBtn= findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginButton:
                    Log.i("로그인", "onClick: ");
                    login();
                    break;
                case R.id.gotoSignUpButton:
                    Log.i("회원가입", "onClick: ");
                    startSignUp();
                    break;

            }
        }
    };

    private void login() { //로그인 로직 처리
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.pwdEditText)).getText().toString();


        if(email.length() > 0 && password.length() > 0){ //이메일이나 패스워드를 입력 하지 않았을 때

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인 성공");
                                startMain();


                            } else {

                                if (task.getException() != null) { //입력값이 널값일 때
                                    startToast(task.getException().toString()); //비밀번호자리가 6글자 이하일 때 구글이 알아서

                                }
                            }

                        }
                    });

        }else {
            startToast("이메일 또는 비밀 번호를 입력 해주세요");

        }
    }

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void startMain(){ //로그인 완료시 서브액티비티(메인화면)으로 이동 함수
        Intent intent =new Intent(this, SubActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // 기존에 쌓여진 액티비티 클리어?
        startActivity(intent);
    }

    private void startSignUp(){ //회원가입 버튼 클릭시 회원가입 화면으로 이동 함수
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class );
        startActivity(intent);
    }
}


