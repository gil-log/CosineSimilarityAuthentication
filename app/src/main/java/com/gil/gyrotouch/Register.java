package com.gil.gyrotouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText idedit;
    Button regB, loginB;
    DBHelp dbhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        idedit = (EditText)findViewById(R.id.idT);

        Button regB = (Button) findViewById(R.id.regB);
        Button cancelB = (Button) findViewById(R.id.cancelB);

        dbhelp = new DBHelp(getApplicationContext(), "GyroTouch.db", null, 1);

        regB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idedit.getText().toString();

                if (!dbhelp.okid(id)){
                    Toast.makeText(Register.this, "사용 가능한 아이디 입니다.\n측정을 진행합니다.", Toast.LENGTH_LONG).show();
                    Intent measurelessIntent = new Intent(Register.this, GyroTouch.class);
                    measurelessIntent.putExtra("id", id);
                    measurelessIntent.putExtra("less",1);
                    startActivity(measurelessIntent);
                }
                else if(dbhelp.okid(id)){
                    Toast.makeText(Register.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancelB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(Register.this, Main.class);
                startActivity(cancelIntent);
            }
        });
    }
}
