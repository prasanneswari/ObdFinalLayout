package jts.mahesh.jtsdesktop.obdfinallayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    static String username, pwd, macid;
    EditText user, password, mac;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.userInput);
        password = (EditText) findViewById(R.id.passInput);
        mac = (EditText) findViewById(R.id.macInput);
        log = (Button) findViewById(R.id.loginbtn);
        log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (user.getText().toString().trim().length() == 0) {
                    user.setError("Username is not entered");
                    user.requestFocus();
                }
                if (password.getText().toString().trim().length() == 0) {
                    password.setError("Password is not entered");
                    password.requestFocus();
                }
                if (mac.getText().toString().trim().length() == 0) {
                    mac.setError("macid is not entered");
                    mac.requestFocus();
                } else {
                    username = user.getText().toString();
                    pwd = password.getText().toString();
                    macid = mac.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   /* intent.putExtra("username", (Parcelable) user);
                    intent.putExtra("pwd", (Parcelable) password);
                    intent.putExtra("macid", (Parcelable) mac);*/
                    startActivity(intent);
                }
            }
        });
    }

}





