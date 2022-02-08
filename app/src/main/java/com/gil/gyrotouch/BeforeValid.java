package com.gil.gyrotouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class BeforeValid extends AppCompatActivity {

    float mea[][] = new float[4][6];
    String id;
    int less;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beforevalid);

        final EditText bfvalid = (EditText) findViewById(R.id.bfvalid);
        Button bfvalidgo = (Button) findViewById(R.id.bfvalidgo);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");
        less = intent.getExtras().getInt("less");
        mea[0] = intent.getExtras().getFloatArray("mea1");
        mea[1] = intent.getExtras().getFloatArray("mea2");
        mea[2] = intent.getExtras().getFloatArray("mea3");
        mea[3] = intent.getExtras().getFloatArray("mea4");

        bfvalid.setText(id);

        bfvalidgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idrep = bfvalid.getText().toString();

                Intent iintent = new Intent(BeforeValid.this, Valid.class);

                iintent.putExtra("id", idrep);
                iintent.putExtra("less", less);
                iintent.putExtra("mea1", mea[0]);
                iintent.putExtra("mea2", mea[1]);
                iintent.putExtra("mea3", mea[2]);
                iintent.putExtra("mea4", mea[3]);
                startActivity(iintent);

            }
        });
    }
}

