package com.example.chatting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout relativeLayout_login, relativeLayout_facebook; // 로그인 버튼, 페이스북 로그인 버튼
    TextInputEditText textInputEditText_id, textInputEditText_password; // id, password 입력창
    TextView createAccount; // 회원가입 버튼

    String id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        relativeLayout_login = findViewById(R.id.relativeLayout_login);
        relativeLayout_facebook = findViewById(R.id.relativeLayout_facebook);
        textInputEditText_id = findViewById(R.id.textInputEditText_id);
        textInputEditText_password = findViewById(R.id.textInputEditText_password);
        createAccount = findViewById(R.id.createAccount);

        id = textInputEditText_id.getText().toString();
        password = textInputEditText_password.getText().toString();

        //1. 값을 가져온다. - 검사
        //2. 클릭을 감지한다.
        //3. 1번의 값을 다음 액티비티로 넘긴다.

        createAccount.setClickable(true);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        relativeLayout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Please Wating");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // id 존재여부 확인
                        if (dataSnapshot.child(id).exists()) {
                            mDialog.dismiss();
                            UserData user = dataSnapshot.child(id).getValue(UserData.class);

                            // 해당 id의 비밀번호 확인
                            if (user.getPassword().equals(password)) {
                                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                                intent.putExtra("nickname", user.getNickname());
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Sign in failed!!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        relativeLayout_login.setClickable(false);
        textInputEditText_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //글자 바뀌기 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    id = charSequence.toString();
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
                    password = charSequence.toString();
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

            }
        });
    }
    public boolean login_validation(){
        return id.getBytes().length > 0 && password.getBytes().length > 0;
    }

}
