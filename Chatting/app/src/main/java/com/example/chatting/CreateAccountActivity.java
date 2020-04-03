package com.example.chatting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountActivity extends AppCompatActivity {

    RelativeLayout relativeLayout_createAccount;
    TextInputEditText textInputEditText_id, textInputEditText_password, textInputEditText_name,
            textInputEditText_nickname, textInputEditText_email, textInputEditText_confirmPassword; // id, password 입력창

    String id, password, nickname, name, email, passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        relativeLayout_createAccount = findViewById(R.id.relativeLayout_createAccount);
        textInputEditText_id = findViewById(R.id.textInputEditText_signUp_id);
        textInputEditText_password = findViewById(R.id.textInputEditText_signUp_password);
        textInputEditText_name = findViewById(R.id.textInputEditText_name);
        textInputEditText_nickname = findViewById(R.id.textInputEditText_nickname);
        textInputEditText_email = findViewById(R.id.textInputEditText_email);
        textInputEditText_confirmPassword = findViewById(R.id.textInputEditText_confirmPassword);

        id = textInputEditText_id.getText().toString();
        password = textInputEditText_password.getText().toString();
        nickname = textInputEditText_nickname.getText().toString();
        name = textInputEditText_name.getText().toString();
        email = textInputEditText_email.getText().toString();
        passwordConfirm = textInputEditText_confirmPassword.getText().toString();

        // Init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        //1. 값을 가져온다. - 검사
        //2. 클릭을 감지한다.
        //3. 1번의 값을 다음 액티비티로 넘긴다.


        relativeLayout_createAccount.setClickable(true);
        relativeLayout_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //로딩 (오레오(9.0, API26)부터 Deprecated 됨)
                final ProgressDialog mDialog = new ProgressDialog(CreateAccountActivity.this);
                mDialog.setMessage("Please Wating");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // 데이터베이스에 이미 있는지 확인
                        if(dataSnapshot.child(id).exists()){
                            mDialog.dismiss();
                            Toast.makeText(CreateAccountActivity.this, "already register", Toast.LENGTH_LONG).show();
                        }else if(!password_confirm()){
                            mDialog.dismiss();
                            Toast.makeText(CreateAccountActivity.this, "Password is wrong! ", Toast.LENGTH_LONG).show();
                        }
                        else{
                            mDialog.dismiss();

                            UserData user = new UserData(id, password, name, nickname, email);
                            table_user.child(id).setValue(user);
                            Toast.makeText(CreateAccountActivity.this, "sign up successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        relativeLayout_createAccount.setClickable(false);
        textInputEditText_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //글자 바뀌기 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    id = charSequence.toString();
                    relativeLayout_createAccount.setClickable(signUp_validation());
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
                    relativeLayout_createAccount.setClickable(signUp_validation());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //글자 바뀐 후
            }
        });

        textInputEditText_confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //글자 바뀌기 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    passwordConfirm = charSequence.toString();
                    relativeLayout_createAccount.setClickable(signUp_validation());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //글자 바뀐 후
            }
        });

        textInputEditText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //글자 바뀌기 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    name = charSequence.toString();
                    relativeLayout_createAccount.setClickable(signUp_validation());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //글자 바뀐 후
            }
        });
        textInputEditText_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //글자 바뀌기 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    nickname = charSequence.toString();
                    relativeLayout_createAccount.setClickable(signUp_validation());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //글자 바뀐 후
            }
        });
        textInputEditText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //글자 바뀌기 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    email = charSequence.toString();
                    relativeLayout_createAccount.setClickable(signUp_validation());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //글자 바뀐 후
            }
        });

    }

    public boolean signUp_validation(){
        return id.getBytes().length > 0 && password.getBytes().length > 0 && name.getBytes().length > 0 && nickname.getBytes().length > 0 && email.getBytes().length > 0;
    }
    public boolean password_confirm(){
        return password.equals(passwordConfirm);
    }
}
