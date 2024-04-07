package com.example.healthiswealth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth Auth;
    Button Logout;
    Button sendMail;
    TextView Mail;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth=FirebaseAuth.getInstance();
        Logout = findViewById(R.id.Btn_Logout);
        Mail= findViewById(R.id.show_mail);
        user = Auth.getCurrentUser();
        sendMail= findViewById(R.id.Btn_mail);



        if(user == null){
            Intent intent2 = new Intent(getApplicationContext(), Login.class);
            startActivity(intent2);
            finish();

        }else{

            Mail.setText(user.getEmail());
        }

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent2);
                finish();
            }

        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), com.example.healthiswealth.sendMail.class);
                startActivity(intent2);
                finish();
            }
        });

    }

}