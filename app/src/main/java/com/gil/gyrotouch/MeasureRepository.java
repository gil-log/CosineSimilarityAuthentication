package com.gil.gyrotouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MeasureRepository extends AppCompatActivity {

    float mea[][] = new float[4][6];
    float me[][][] = new float[5][4][6];
    EditText repid, repnum;
    TextView result;
    String id;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurerepository);

        final DBHelp dbHelper2 = new DBHelp(getApplicationContext(), "GyroTouch.db", null, 1);

        // 테이블에 있는 모든 데이터 출력
        result = (TextView) findViewById(R.id.measureresult);

        repid = (EditText) findViewById(R.id.repid);
        repnum = (EditText) findViewById(R.id.repnum);

        Intent intent = getIntent(); /*데이터 수신*/

        id = intent.getExtras().getString("id");
        mea[0] = intent.getExtras().getFloatArray("mea1");
        mea[1] = intent.getExtras().getFloatArray("mea2");
        mea[2] = intent.getExtras().getFloatArray("mea3");
        mea[3] = intent.getExtras().getFloatArray("mea4");

        repid.setText(id);
        result.setText(dbHelper2.MeasureResult(id));

        count = dbHelper2.Measurecount(id);
        repnum.setText(Integer.toString(count));

        Button addbtn = (Button)findViewById(R.id.add);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idrep = repid.getText().toString();
                int numrep = 0;
                numrep = Integer.parseInt(repnum.getText().toString());
                dbHelper2.Measureinsert(idrep, numrep, 1, mea[0]);
                dbHelper2.Measureinsert(idrep, numrep, 2, mea[1]);
                dbHelper2.Measureinsert(idrep, numrep, 3, mea[2]);
                dbHelper2.Measureinsert(idrep, numrep, 4, mea[3]);

                result.setText(dbHelper2.MeasureResult(id));

                Boolean isit = dbHelper2.isitok(idrep);

                if(isit){
                    float baltest[][] = new float [4][6];

                    for(int i = 0; i < 4; i++){
                        baltest[i] = dbHelper2.makeBalance(idrep, i+1);
                        dbHelper2.Balanceinsert(idrep, i+1, baltest[i]);
                    }

                    double cosimlity [][] = new double [5][4];

                    for(int i = 0 ; i<5; i++){
                        me[i][0] = dbHelper2.getMeasure(idrep, i+1, 1);
                        me[i][1] = dbHelper2.getMeasure(idrep, i+1, 2);
                        me[i][2] = dbHelper2.getMeasure(idrep, i+1, 3);
                        me[i][3] = dbHelper2.getMeasure(idrep, i+1, 4);

                        cosimlity[i][0] = CosineSimilarity.cosinsimility(me[i][0], baltest[0]);
                        cosimlity[i][1] = CosineSimilarity.cosinsimility(me[i][1], baltest[1]);
                        cosimlity[i][2] = CosineSimilarity.cosinsimility(me[i][2], baltest[2]);
                        cosimlity[i][3] = CosineSimilarity.cosinsimility(me[i][3], baltest[3]);
                    }

                    double forvalid[][] = new double[4][5];

                    for(int i = 0 ; i < 4; i++){
                        for(int j = 0; j < 5; j++){
                            forvalid[i][j] = cosimlity[j][i];
                        }
                    }

                    double valid[][] = new double [4][2];

                    valid[0] = minmax(forvalid[0]);
                    valid[1] = minmax(forvalid[1]);
                    valid[2] = minmax(forvalid[2]);
                    valid[3] = minmax(forvalid[3]);

                    dbHelper2.Validinsert(idrep, valid);

                    Intent lesschange = new Intent(MeasureRepository.this, GyroTouch.class);
                    lesschange.putExtra("less", 0);
                }
            }
        });

        Button findbtn = (Button)findViewById(R.id.find);

        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String findid = repid.getText().toString();
                //int findnum = 0;
                //findnum = Integer.parseInt(repnum.getText().toString());
                result.setText(dbHelper2.MeasureResult(findid));
            }
        });
        Button againbtn = (Button)findViewById(R.id.agaian);
        againbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeasureRepository.this, GyroTouch.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button mainbtn = (Button)findViewById(R.id.mainbtn);
        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeasureRepository.this, Main.class);
                startActivity(intent);
            }
        });
    }

    public double[] minmax(double [] forvalid1){
        double minmax[] = new double[2];
        double min = forvalid1[0];
        double max = forvalid1[0];
        for(int i = 1; i < forvalid1.length; i++){
            if(min>forvalid1[i]){
                min = forvalid1[i];
            }
            if(max < forvalid1[i]){
                max = forvalid1[i];
            }
        }
        minmax[0] = min;
        minmax[1] = max;
        return minmax;
    }
}