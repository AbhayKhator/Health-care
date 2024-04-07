package com.example.healthiswealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sendMail extends AppCompatActivity {

    Button logout;
    Button home;
    Button sendMail;

    TextView email,subject,content;

    FirebaseAuth Auth;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);


        logout = findViewById(R.id.Logout_btn);
        home = findViewById(R.id.Btn_home);
        sendMail = findViewById(R.id.Btn_senmail);

        email = findViewById(R.id.mail);
        subject = findViewById(R.id.Subject);
        content = findViewById(R.id.Content);


        Auth=FirebaseAuth.getInstance();
        user = Auth.getCurrentUser();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent2);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), com.example.healthiswealth.MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Subject, Content, toEmail;

                Subject = subject.getText().toString();
                Content = content.getText().toString();
                toEmail = email.getText().toString();

                if (Subject.equals("") && Content.equals("") && toEmail.equals("")){
                    Toast.makeText(sendMail.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    Sendmail(Subject,Content,toEmail);
                    subject.setText("");
                    subject.setText("");
                    content.setText("");
                    finish();

                }


            }
        });


    }

    public void Sendmail(String subject, String content, String to_mail){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {to_mail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose Email client: "));
    }



}