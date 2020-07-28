package com.gil.gyrotouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {
    EditText idedit;
    DBHelp dbhelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        dbhelp = new DBHelp(getApplicationContext(), "GyroTouch.db", null, 1);

        idedit = (EditText)findViewById(R.id.idT);

        Button regB = (Button) findViewById(R.id.regB);
        Button infoB = (Button) findViewById(R.id.infoB);
        Button loginB = (Button) findViewById(R.id.loginB);

        regB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent register = new Intent(Main.this, Register.class);
                startActivity(register);
            }
        });

        infoB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent info = new Intent(Main.this, Info.class);
                startActivity(info);
            }
        });

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idedit.getText().toString();
                if(dbhelp.okid(id)) {
                    if(dbhelp.Measurecount(id)<6){
                        Toast.makeText(Main.this, "측정횟수가 부족합니다. 측정을 진행합니다.", Toast.LENGTH_LONG).show();
                        Intent measurelessIntent = new Intent(Main.this, GyroTouch.class);
                        measurelessIntent.putExtra("id", id); /*송신*/
                        measurelessIntent.putExtra("less", true);
                        startActivity(measurelessIntent);
                    }
                    else if(dbhelp.Measurecount(id)>=6){
                        Intent loginIntent = new Intent(Main.this, GyroTouch.class);
                        loginIntent.putExtra("id", id); /*송신*/
                        loginIntent.putExtra("less", false);
                        startActivity(loginIntent);
                    }
                }

                else if (!dbhelp.okid(id)){
                    Toast.makeText(Main.this, "아이디가 존재하지 않습니다.\n회원가입을 진행해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
