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
    ArrayList<Station> s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_information);

        Intent intent = getIntent();

        s = ((MainActivity)MainActivity.context_main).getStation();

        TextView infor = findViewById(R.id.infor);
        int min = intent.getIntExtra("min", 0);

        disNode = getIntent().getIntegerArrayListExtra("disNode");
        timeNode = getIntent().getIntegerArrayListExtra("timeNode");
        chargeNode = getIntent().getIntegerArrayListExtra("chargeNode");
        System.out.println(timeNode);

        if(disNode != null){
            String temp = "";
            for(int dis : disNode){
                System.out.println(dis);
                temp += s.get(dis).getName() + "\n" + "↓" + "\n";
            }
            temp += "도착" + "\n";
            infor.setText("\n" + "최단 거리: " + min + "\n\n" + temp);
        }else if(timeNode != null){
            String temp = "";
            for(int time : timeNode){
                System.out.println(time);
                temp += s.get(time).getName() + "\n" + "↓"+ "\n";
            }
            temp += "도착" + "\n";
            infor.setText("\n"+"최단 시간: " + min + "\n\n" + temp);
        }else if(chargeNode != null){
            String temp = "";
            for(int charge : chargeNode){
                System.out.println(charge);
                temp += s.get(charge).getName() + "\n" + "↓"+ "\n";
            }
            temp += "도착" + "\n";
            infor.setText("\n"+"최소 비용: " + min + "\n\n" + temp);
        }

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