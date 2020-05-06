package com.gil.gyrotouch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class GyroTouch extends AppCompatActivity implements SensorEventListener {
    float tx = 0, ty = 0;
    int count = 0;
    private SensorManager sensorManager;
    private Sensor gravitySensor;
    long dTStart, dTStop;
    String id;
    int less;
    float[] dTF = {0, 0, 0, 0};
    float[] tX = {0, 0, 0, 0, 0};
    float[] tY = {0, 0, 0, 0, 0};
    float[] gX = {0, 0, 0, 0, 0};
    float[] gY = {0, 0, 0, 0, 0};
    float[] gZ = {0, 0, 0, 0, 0};

    float gx, gy, gz;

    float[] mtX ={0, 0, 0 ,0};
    float[] mtY ={0, 0, 0 ,0};
    float [] mgX = {0, 0, 0, 0};
    float [] mgY = {0, 0, 0, 0};
    float [] mgZ = {0, 0, 0, 0};

    public float mea[][] = new float[4][6];

    float databox[][] = new float[5][6];

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyrotouch);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        LinearLayout touch = (LinearLayout)findViewById(R.id.gyrotouch);

        iv = (ImageView) findViewById(R.id.gtimageView1);

        Intent intent = getIntent(); /*데이터 수신*/
        id = intent.getExtras().getString("id");
        less = intent.getExtras().getInt("less");
        touch.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN :

                        tx = event.getX();
                        ty = event.getY();

                        iv.setVisibility(View.VISIBLE);
                        iv.setX(tx-120);
                        iv.setY(ty-120);
                        // 이미지 뷰의 위치를 옮기기

                        count++;

                        switch(count){
                            case 1:
                                dTStart = System.currentTimeMillis();

                                tX[0] = tx;
                                tY[0] = ty;

                                gX[0] = gx;
                                gY[0] = gy;
                                gZ[0] = gz;

                                databox[0][0] = tx;
                                databox[0][1] = ty;
                                databox[0][2] = gx;
                                databox[0][3] = gy;
                                databox[0][4] = gz;
                                databox[0][5] = 0;

                                Toast.makeText(GyroTouch.this, "측정이 시작 되었습니다!", Toast.LENGTH_LONG).show();
                                break;

                            case 2:

                                dTStop = System.currentTimeMillis();

                                dTF[0] = (float)(dTStop - dTStart);

                                tX[1] = tx;
                                tY[1] = ty;

                                gX[1] = gx;
                                gY[1] = gy;
                                gZ[1] = gz;

                                databox[1][0] = tx;
                                databox[1][1] = ty;
                                databox[1][2] = gx;
                                databox[1][3] = gy;
                                databox[1][4] = gz;
                                databox[1][5] = dTF[0];

                                Toast.makeText(GyroTouch.this, "2회 측정", Toast.LENGTH_SHORT).show();

                                break;

                            case 3:

                                dTStart = System.currentTimeMillis();
                                dTF[1] = (float)(dTStart - dTStop);

                                tX[2] = tx;
                                tY[2] = ty;

                                gX[2] = gx;
                                gY[2] = gy;
                                gZ[2] = gz;

                                databox[2][0] = tx;
                                databox[2][1] = ty;
                                databox[2][2] = gx;
                                databox[2][3] = gy;
                                databox[2][4] = gz;
                                databox[2][5] = dTF[1];

                                Toast.makeText(GyroTouch.this, "3회 측정", Toast.LENGTH_SHORT).show();

                                break;

                            case 4:

                                dTStop = System.currentTimeMillis();

                                dTF[2] = (float)(dTStop - dTStart);

                                tX[3] = tx;
                                tY[3] = ty;

                                gX[3] = gx;
                                gY[3] = gy;
                                gZ[3] = gz;

                                databox[3][0] = tx;
                                databox[3][1] = ty;
                                databox[3][2] = gx;
                                databox[3][3] = gy;
                                databox[3][4] = gz;
                                databox[3][5] = dTF[2];

                                Toast.makeText(GyroTouch.this, "4회 측정", Toast.LENGTH_SHORT).show();
                                break;

                            case 5:

                                tX[4] = tx;
                                tY[4] = ty;

                                gX[4] = gx;
                                gY[4] = gy;
                                gZ[4] = gz;

                                dTStart = System.currentTimeMillis();
                                dTF[3] = (float)(dTStart - dTStop);

                                databox[4][0] = tx;
                                databox[4][1] = ty;
                                databox[4][2] = gx;
                                databox[4][3] = gy;
                                databox[4][4] = gz;
                                databox[4][5] = dTF[3];

                                mtX = touchMeasure(tX);
                                mtY = touchMeasure(tY);
                                mgX = touchMeasure(gX);
                                mgY = touchMeasure(gY);
                                mgZ = touchMeasure(gZ);
                               //5번의 측정값 x, y좌표, x, y, z 벡터를 뒤 측정 - 앞 측정 으로 4구간으로 변환

                                for(int j = 0 ; j < 4; j++){
                                        mea[j][0] = mtX[j];
                                        mea[j][1] = mtY[j];
                                        mea[j][2] = mgX[j];
                                        mea[j][3] = mgY[j];
                                        mea[j][4] = mgZ[j];
                                        mea[j][5] = dTF[j];
                                }

                                AlertDialog.Builder ad = new AlertDialog.Builder(GyroTouch.this);

                                ad.setTitle("측정 완료");       // 제목 설정
                                ad.setMessage("측정값을 추가 하시겠습니까?");   // 내용 설정
                                // 확인 버튼 설정
                                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();     //닫기

                                        Intent intent = new Intent(GyroTouch.this, MeasureRepository.class);
                                        intent.putExtra("id", id);
                                        intent.putExtra("less", less);
                                        intent.putExtra("mea1", mea[0]);
                                        intent.putExtra("mea2", mea[1]);
                                        intent.putExtra("mea3", mea[2]);
                                        intent.putExtra("mea4", mea[3]);

                                        startActivity(intent);

                                        // Event
                                    }
                                });
                                ad.setNeutralButton("검증", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();     //닫기

                                        if(less==0) {
                                            Intent intent = new Intent(GyroTouch.this, BeforeValid.class);

                                            intent.putExtra("id", id);
                                            intent.putExtra("less", less);
                                            intent.putExtra("mea1", mea[0]);
                                            intent.putExtra("mea2", mea[1]);
                                            intent.putExtra("mea3", mea[2]);
                                            intent.putExtra("mea4", mea[3]);

                                            startActivity(intent);
                                        }
                                        else if (less==1){
                                            Toast.makeText(GyroTouch.this, "측정값이 부족합니다.\n측정값을 추가해 주세요.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                ad.setNegativeButton("다시", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();     //닫기

                                        count = 0;
                                        iv.setVisibility(View.INVISIBLE);
                                        // Event
                                    }
                                });
                                ad.show();
                                break;
                            case 6:
                                count = 0;
                                Toast.makeText(GyroTouch.this, "측정을 다시 시작합니다.", Toast.LENGTH_LONG).show();
                                iv.setVisibility(View.INVISIBLE);
                                break;
                            default:
                                break;
                        }
                    case MotionEvent.ACTION_MOVE :
                    case MotionEvent.ACTION_UP   :
                }
                return true;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == gravitySensor) {
            gx = event.values[0];
            gy = event.values[1];
            gz = event.values[2];
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    public static float[] touchMeasure(float touch[]){
        float [] mt ={0, 0, 0, 0};
        mt[0] = touch[1] - touch[0];
        mt[1] = touch[2] - touch[1];
        mt[2] = touch[3] - touch[2];
        mt[3] = touch[4] - touch[3];
        return mt;
    }
}
