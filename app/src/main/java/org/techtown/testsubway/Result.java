package org.techtown.testsubway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.view.View.OnTouchListener;

import static android.view.View.*;

public class Result extends AppCompatActivity {
    String a, c;
    int b, d;


    TextView name_s; // 출발역을 출력해주는 변수
    TextView name_f; // 도착역을 출력해주는 변수
    TextView distance; // 거리를 출력해주는 변수
    TextView time; // 시간을 출력해주는 변수
    TextView charge; // 값을 출력해주는 변수
    Button minDis, minTime, minCharge;
    Button like;
    int min, min2, min3;
    ArrayList<Integer> disNode, timeNode, chargeNode;
    ArrayList<Integer[]> likeNode = new ArrayList<Integer[]>();
    public static final String KEY_SIMPLE_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 변수명과 아이디를 연결
        name_s = findViewById(R.id.name_s);
        name_f = findViewById(R.id.name_f);
        minDis = findViewById(R.id.minDis);
        minTime = findViewById(R.id.minTime);
        minCharge = findViewById(R.id.minCharge);

        // 뒤로가는 변수, 메소드
        Intent intent = getIntent();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        TextView finals = findViewById(R.id.finals);

        Station startInfo = (Station) intent.getSerializableExtra("StartStation");
        Station finalInfo = (Station) intent.getSerializableExtra("FinalStation");
        min = getIntent().getIntExtra("Distance",1);
        min2 = getIntent().getIntExtra("Time",2);
        min3 = getIntent().getIntExtra("Charge",3);
        disNode = getIntent().getIntegerArrayListExtra("disNode");
        timeNode = getIntent().getIntegerArrayListExtra("timeNode");
        chargeNode = getIntent().getIntegerArrayListExtra("chargeNode");

        String string1 = Integer.toString(min);
        String string2 = Integer.toString(min2);
        String string3 = Integer.toString(min3);

        name_s.setText(startInfo.getName());
        name_f.setText(finalInfo.getName());
        finals.setText(finalInfo.getInfo());

        // 즐겨찾기 리스트추가 수정중
        a = name_s.getText().toString();
        c = name_f.getText().toString();

        b = Integer.parseInt(a);
        d = Integer.parseInt(c);

        like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                like.setBackgroundResource(R.drawable.star1);
                likeNode.add(new Integer[]{b, d});

                System.out.println(likeNode.get(0)[0]);
                System.out.println(likeNode.get(0)[1]);

            }
        });

        minDis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), resultInformation.class);
                intent.putExtra("min", min);
                intent.putExtra("disNode", disNode);
                startActivityForResult(intent, 123);
            }
        });

        minTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), resultInformation.class);
                intent.putExtra("min", min2);
                intent.putExtra("timeNode", timeNode);
                startActivityForResult(intent, 124);

            }
        });

        minCharge.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), resultInformation.class);
                intent.putExtra("min", min3);
                intent.putExtra("chargeNode", chargeNode);
                startActivityForResult(intent, 125);

            }
        });
    }
}