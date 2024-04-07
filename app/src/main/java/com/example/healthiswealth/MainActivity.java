package com.example.healthiswealth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


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

    public class EmailService {
        public static void sendLoginNotification(String userEmail) {
            final String username = ""; // Your email address
            final String password = ""; // Your email password

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com"); // Use Gmail SMTP server
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(userEmail));
                message.setSubject("Login Notification");
                message.setText("Dear User,\n\nSomeone has logged into your account.\n\nRegards,\nYour App Team");

                Transport.send(message);

                System.out.println("Login notification email sent to: " + userEmail);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}