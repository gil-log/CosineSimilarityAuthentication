package com.gil.gyrotouch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Valid extends AppCompatActivity {

    float mea[][] = new float[4][6];
    float baltest[][] = new float [4][6];
    String idvalid;
    double [] cosimlity1 = {0, 0, 0, 0};
    double [] validarr = {0, 0, 0, 0, 0, 0, 0, 0};
    boolean pass = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valid);
        Intent intent = getIntent(); /*데이터 수신*/

        final String testid = intent.getExtras().getString("id");

        mea[0] = intent.getExtras().getFloatArray("mea1");
        mea[1] = intent.getExtras().getFloatArray("mea2");
        mea[2] = intent.getExtras().getFloatArray("mea3");
        mea[3] = intent.getExtras().getFloatArray("mea4");

        final DBHelp dbHelperVal = new DBHelp(getApplicationContext(), "GyroTouch.db", null, 1);

        final EditText validid = (EditText)findViewById(R.id.validid);
        final TextView validresult = (TextView)findViewById(R.id.validresult);

        validid.setText(testid);

        idvalid = validid.getText().toString();

        Button validtogyro = (Button) findViewById(R.id.validtogyro);

        validtogyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Valid.this, GyroTouch.class);
                backIntent.putExtra("id", testid);
                startActivity(backIntent);
            }
        });

        Button validbtn = (Button)findViewById(R.id.validbtn);

        validbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idvalid = validid.getText().toString();

                for(int i = 0 ; i < 4; i++){
                    baltest[i] = dbHelperVal.getBalance(idvalid, i+1);
                }

                cosimlity1[0] = CosineSimilarity.cosinsimility(mea[0], baltest[0]);
                cosimlity1[1] = CosineSimilarity.cosinsimility(mea[1], baltest[1]);
                cosimlity1[2] = CosineSimilarity.cosinsimility(mea[2], baltest[2]);
                cosimlity1[3] = CosineSimilarity.cosinsimility(mea[3], baltest[3]);

                for(int i = 0; i<8; i++) {
                    validarr[i] = dbHelperVal.getValid(idvalid)[i];
                }

                Boolean val1, val2, val3, val4;

                val1 = false;
                val2 = false;
                val3 = false;
                val4 = false;

                String valresult = "!검증실패!";
                String va1 ="실패";
                String va2 = "실패";
                String va3 = "실패";
                String va4 = "실패";

                double rate = 9.5;

                double alpha1 = (validarr[1] - validarr[0])*rate;
                double alpha2 = (validarr[3] - validarr[2])*rate;
                double alpha3 = (validarr[5] - validarr[4])*rate;
                double alpha4 = (validarr[7] - validarr[6])*rate;

                if((validarr[0]-alpha1) <= cosimlity1[0])
                {
                    val1 = true;
                    va1 = "성공";
                }
                if((validarr[2]-alpha2) <= cosimlity1[1])
                {
                    val2 = true;
                    va2 = "성공";
                }
                if((validarr[4]-alpha3) <= cosimlity1[2])
                {
                    val3 = true;
                    va3 = "성공";
                }
                if((validarr[6]-alpha4) <= cosimlity1[3])
                {
                    val4 = true;
                    va4 = "성공";
                }
                if(val1 && val2 && val3 && val4){
                  valresult = "!!!검증성공!!!";
                  pass = true;

                }

                AlertDialog.Builder ad = new AlertDialog.Builder(Valid.this);

                ad.setTitle("검증결과");       // 제목 설정
                ad.setMessage(""+idvalid +" 와의 검증 결과 " + valresult + " 입니다. "+"\n"+"검증값 : "+ cosimlity1[0] +"\n"+ "검증값 : "+ cosimlity1[1] +"\n" + "검증값 : "+ cosimlity1[2] +"\n" +"검증값 : "+ cosimlity1[3] +"\n"+ "검증결과 : "+ va1 + ", "+ va2 + ", "+ va3 + ", "+ va4);   // 내용 설정

                // 확인 버튼 설정
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
            }

            }
        );
                ad.show();
                        // Event


                    }
                });

        Button validfindbtn = (Button)findViewById(R.id.validfind);

        validfindbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idvalid = validid.getText().toString();

                if(dbHelperVal.okid(idvalid)) {
                    validresult.setText(dbHelperVal.ValidResult(idvalid));
                }
                else{
                    validresult.setText("");
                    Toast.makeText(Valid.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button measureaddbtn = (Button)findViewById(R.id.measureaddbtn);
        measureaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pass) {
                    Intent intent = new Intent(Valid.this, MeasureRepository.class);
                    intent.putExtra("id", testid);
                    intent.putExtra("mea1", mea[0]);
                    intent.putExtra("mea2", mea[1]);
                    intent.putExtra("mea3", mea[2]);
                    intent.putExtra("mea4", mea[3]);

                    startActivity(intent);
                }else{
                    Toast.makeText(Valid.this, "검증에 실패하여 측정값 추가가 불가능 합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button homebtn = (Button)findViewById(R.id.homebtn);
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Valid.this, Main.class);
                startActivity(intent);
            }
        });

    }
/*
    public void valid(float[][] mea, String id){

        double [] validarr = {0, 0, 0, 0, 0, 0, 0, 0};
        DBHelp dbHelper = new DBHelp(getApplicationContext(), "GyroTouch.db", null, 1);
        String idvalid = id;
        float baltest[][] = new float [4][6];
        double [] cosimlity1 = {0, 0, 0, 0};


        for(int i = 0 ; i < 4; i++){
            baltest[i] = dbHelper.getBalance(idvalid, i+1);
        }

        cosimlity1[0] = CosineSimilarity.cosinsimility(mea[0], baltest[0]);
        cosimlity1[1] = CosineSimilarity.cosinsimility(mea[1], baltest[1]);
        cosimlity1[2] = CosineSimilarity.cosinsimility(mea[2], baltest[2]);
        cosimlity1[3] = CosineSimilarity.cosinsimility(mea[3], baltest[3]);

        for(int i = 0; i<8; i++) {
            validarr[i] = dbHelper.getValid(idvalid)[i];
        }

        Boolean val1, val2, val3, val4;


        val1 = false;
        val2 = false;
        val3 = false;
        val4 = false;

        String valresult = "!검증실패!";
        String va1 ="실패";
        String va2 = "실패";
        String va3 = "실패";
        String va4 = "실패";

        double rate = 9.5;

        double alpha1 = (validarr[1] - validarr[0])*rate;
        double alpha2 = (validarr[3] - validarr[2])*rate;
        double alpha3 = (validarr[5] - validarr[4])*rate;
        double alpha4 = (validarr[7] - validarr[6])*rate;

        if((validarr[0]-alpha1) <= cosimlity1[0])
        {
            val1 = true;
            va1 = "성공";
        }
        if((validarr[2]-alpha2) <= cosimlity1[1])
        {
            val2 = true;
            va2 = "성공";
        }
        if((validarr[4]-alpha3) <= cosimlity1[2])
        {
            val3 = true;
            va3 = "성공";
        }
        if((validarr[6]-alpha4) <= cosimlity1[3])
        {
            val4 = true;
            va4 = "성공";
        }
        if(val1 && val2 && val3 && val4){
            valresult = "!!!검증성공!!!";
        }


        AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("검증결과");       // 제목 설정
        ad.setMessage(""+idvalid +" 와의 검증 결과 " + valresult + " 입니다. "+"\n"+"검증값 : "+ cosimlity1[0] +"\n"+ "검증값 : "+ cosimlity1[1] +"\n" + "검증값 : "+ cosimlity1[2] +"\n" +"검증값 : "+ cosimlity1[3] +"\n"+ "검증결과 : "+ va1 + ", "+ va2 + ", "+ va3 + ", "+ va4);   // 내용 설정

        // 확인 버튼 설정
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }

                }
        );
        ad.show();
        // Event

    }
    */
}


