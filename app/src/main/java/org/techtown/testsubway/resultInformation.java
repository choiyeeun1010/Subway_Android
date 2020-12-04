package org.techtown.testsubway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class resultInformation extends AppCompatActivity {
    ArrayList<Integer> disNode, timeNode, chargeNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_information);

        Intent intent = getIntent();

        TextView infor = findViewById(R.id.infor);
        int minDis = intent.getIntExtra("minDis", 0);
        infor.setText("최단 거리: " + minDis);

        disNode = getIntent().getIntegerArrayListExtra("disNode");
        timeNode = getIntent().getIntegerArrayListExtra("timeNode");
        chargeNode = getIntent().getIntegerArrayListExtra("chargeNode");

        String temp = "";


        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}