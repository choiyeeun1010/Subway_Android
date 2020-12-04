package org.techtown.testsubway;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MiddleStation extends AppCompatActivity implements Serializable{

    MainActivity ma;
    Station StartStation, FinalStation; //사용자1, 사용자2
    Button st1b, st2b, search; //사용자1 선택, 사용자2 선택, 검색
    TextView result, st1, st2, time, time2; //결과텍스트, 사용자1, 사용자2, 사용자1의 시간, 사용자 2의 시간, 지나치는 역들
    String player1, player2; // 사용자 1, 사용자 2
    MiddleDijkstra dj; //중간역다익스트라
    ArrayList<Station> s;
    static final int REQUEST_CODE_MENU = 999;
    static final int REQUEST_CODE_MIDDLE_ONE = 888;
    static final int REQUEST_CODE_MIDDLE_TWO = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_station);

        ImageButton back = findViewById(R.id.back);
        st1b = findViewById(R.id.st1b);
        st2b = findViewById(R.id.st2b);
        st1 = findViewById(R.id.middle);
        st2 = findViewById(R.id.like);
        result = findViewById(R.id.result);
        time = findViewById(R.id.time);
        time2 = findViewById(R.id.time2);
        search = findViewById(R.id.search);

        Intent intent = getIntent();
        s = (ArrayList<Station>) intent.getSerializableExtra("station");
        // ma = new MainActivjumity();
        // ArrayList<Station> st = ma.getStation();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //사용자1의 역 설정 버튼 클릭 시,
        st1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectStation.class);
                startActivityForResult(intent, 101);
            }
        });

        st2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectStation.class);
                startActivityForResult(intent, 202);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StartStation == null || FinalStation == null){
                    Toast.makeText(getApplicationContext(), "사용자들의 역을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    dj = new MiddleDijkstra();
                    dj.init();
                    dj.start(StartStation.getNumber(), FinalStation.getNumber());
                    System.out.println("중간역 함수 호출");
                    int m = dj.getMiddle();
                    if(m == -2)
                        result.setText("오류");
                    else if(m == -1)
                        result.setText("현재 역에서 만나세요!");
                    else {
                        result.setText("중간역은 : " + s.get(m).getName());
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK){
            StartStation = (Station)data.getSerializableExtra("player");
            st1.setText(StartStation.getName());
        }else if(requestCode == 202 && resultCode == RESULT_OK){
            FinalStation = (Station)data.getSerializableExtra("player");
            st2.setText(FinalStation.getName());
        }
    }
}