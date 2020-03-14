package com.example.study_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout relativeLayout_login, relativeLayout_facebook; // 로그인 버튼, 페이스북 로그인 버튼
    TextInputEditText textInputEditText_email, textInputEditText_password; // email, password 입력창
    String emailOK = "123@gmail.com";
    String passwordOK = "1234";

    String inputEmail = "";
    String inputPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        relativeLayout_login = findViewById(R.id.relativeLayout_login);
        relativeLayout_facebook = findViewById(R.id.relativeLayout_facebook);
        textInputEditText_email = findViewById(R.id.textInputEditText_email);
        textInputEditText_password = findViewById(R.id.textInputEditText_password);

        //1. 값을 가져온다. - 검사 {123@gmail.com / 1234} 해당값일 경우에만 로그인가능하게
        //2. 클릭을 감지한다.
        //3. 1번의 값을 다음 액티비티로 넘긴다.
        relativeLayout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = textInputEditText_email.getText().toString();
                String password = textInputEditText_password.getText().toString();

                Intent intent = new Intent(LoginActivity.this, LoginResultActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

        relativeLayout_login.setClickable(false);
        textInputEditText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //글자 바뀌기 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    inputEmail = charSequence.toString();
                    relativeLayout_login.setClickable(login_validation());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //글자 바뀐 후
            }
        });

        textInputEditText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //글자 바뀌기 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    inputPassword = charSequence.toString();
                    relativeLayout_login.setClickable(login_validation());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //글자 바뀐 후
            }
        });

        relativeLayout_facebook.setClickable(true);
        relativeLayout_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = textInputEditText_email.getText().toString();
                String password = textInputEditText_password.getText().toString();
            }
        });
    }

    public boolean login_validation(){
        return inputEmail.equals(emailOK) && inputPassword.equals(passwordOK);
    }
}
