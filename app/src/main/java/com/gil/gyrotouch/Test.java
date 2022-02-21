package com.gil.gyrotouch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class Test extends AppCompatActivity {

    Button teststart, testinfo;
    EditText testid, testnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        testid = (EditText)findViewById(R.id.testid);
        testnum = (EditText)findViewById(R.id.testnum);



        teststart = (Button) findViewById(R.id.teststart);

        teststart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Test.this, GyroTouch.class);

                int tnum = Integer.parseInt(testnum.getText().toString());

                intent.putExtra("id",testid.getText().toString()); /*송신*/
                intent.putExtra("num", tnum);


                startActivity(intent);
            }
        });



        testinfo = (Button)findViewById(R.id.testinfo);

        testinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(Test.this);

                ad.setTitle("TEST");       // 제목 설정
                ad.setMessage("For Testing");   // 내용 설정

                // 확인 버튼 설정
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

                /*
                // 중립 버튼 설정
                ad.setNeutralButton("What?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

                // 취소 버튼 설정
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });
*/
                // 창 띄우기
                ad.show();
            }
        });



    }
}
