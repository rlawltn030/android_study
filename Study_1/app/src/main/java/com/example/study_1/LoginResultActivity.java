package com.example.study_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginResultActivity extends AppCompatActivity {

    TextView textView_get;
    Button button_next;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_result);

        textView_get = findViewById(R.id.textView_get);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        email = bundle.getString("email");
        password = bundle.getString("password");

        textView_get.setText("email = "+email + "\npassword = " + password);

        button_next = findViewById(R.id.button_next);

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginResultActivity.this, NewsActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
    }
}
