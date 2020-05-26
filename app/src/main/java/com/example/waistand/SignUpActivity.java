/*
 * MainActivity : 접속 시 첫 화면 -> 로그인 화면
 * */
package com.example.waistand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import android.widget.Toolbar;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acctivity_sign_up);

        //initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.signUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoLoginButton).setOnClickListener(onClickListener);

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



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signUpButton:
                    signUp();
                    // Log.d("클릭", "onClick: ");
                    break;


            }
        }
    };

    private void signUp() { //회원가입 로직 처리
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();

        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){ //이메일이나 패스워드를 입력 하지 않았을 때
            if(password.equals(passwordCheck)){
                //final RelativeLayout loaderLayout = findViewById(R.id.loaderLyaout);
                //loaderLayout.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //loaderLayout.setVisibility(View.GONE);
                                if (task.isSuccessful()) { //회원가입 성공시 로직
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입 성공");
                                    startLogin();
                                    //showToast(SignUpActivity.this, "회원가입에 성공하였습니다.");
                                    //myStartActivity(MainActivity.class);

                                } else {  //회원가입 실패 시 로직

                                    if(task.getException() != null){ //입력값이 널값일 때
                                        startToast(task.getException().toString()); //비밀번호자리가 6글자 이하일 때 구글이 알아서서
                                        //showToast(SignUpActivity.this, task.getException().toString());
                                    }

                                }
                            }
                        });
            }else{
                startToast("비밀번호 불일치");
                //showToast(SignUpActivity.this, "비밀번호가 일치하지 않습니다.");
            }
        }else {
            startToast("이메일 또는 비밀 번호를 입력 해주세요");

            //showToast(SignUpActivity.this, "이메일 또는 비밀번호를 입력해 주세요.");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void startLogin(){ //로그인 버튼 클릭시 로그인 화면으로 이동 함수
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

}


