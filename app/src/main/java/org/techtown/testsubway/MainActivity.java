package org.techtown.testsubway;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    LinearLayout container;
    String startST, finalST; //시작역과 종착역 이름을 저장할 변수
    TextView start_tx, final_tx; //시작역과 종착역 이름을 보여줄 텍스트 창 변수
    ArrayList<Station> station = new ArrayList<Station>(); //역 객체 (어레이 리스트 사용)
    Station StartStation, FinalStation; //시작역 과 도착역
    Button search; //검색 버튼
    static final int REQUEST_CODE_MENU = 999;
    Dijkstra ds, ds2, ds3; //다익스트라 변수
    int startNum=-1, finalNum=-1; //시작 노드 번호와 끝 노드 번호
    int min = -1; //최단 시간 값
    int min2 = -1;
    int min3 = -1;
    String user; //사용자가 입력한 문자를 저장할 변수

    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context_main = this;

        container = findViewById(R.id.container);

        search = findViewById(R.id.search);

        start_tx = findViewById(R.id.start_tx);
        final_tx = findViewById(R.id.final_tx);
        LinearLayout con = findViewById(R.id.con);

        //메인 지하철 노선도 이미지를 위한 과정
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.station);

        ImageDisplayView view = new ImageDisplayView(this);
        view.setImageData(bitmap);
        con.addView(view);

        //중간역, 즐찾, 역 검색 을 위한 준비
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sub, container, true);

        LinearLayout concon = findViewById(R.id.concon);
        //역 객체 생성
        makeStation();

        start_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectStation.class);
                startActivityForResult(intent, 101);
            }
        });

        final_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectStation.class);
                startActivityForResult(intent, 202);
            }
        });
        //다익스트라 알고리즘을 수행하는 역할
        //검색 버튼 클릭 시 알고리즘을 수행하여 최단 값을 반환해주고 해당 값을 화면에 보여줌
        /*
        * 수정 필요 사항
        * 1. 검색 버튼 클릭 시 아래에 정보가 뜨는게 아니라 새로운 화면으로 전환해서 떠야함
        * 2. 전환된 화면에서 즐겨찾기에 추가할 수 있어야함(설정된 시작역과 도착역 정보를 주면 될듯)
        * */
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("검색 버튼 클릭됨");
                if(StartStation == null || FinalStation == null){
                    Toast.makeText(MainActivity.this, "출발역과 도착역 설정 바람", Toast.LENGTH_SHORT).show();
                }else{
                    ds = new Dijkstra();
                    ds2 = new Dijkstra();
                    ds3 = new Dijkstra();
                    min = ds.dijkstra1(startNum, finalNum);
                    ArrayList<Integer> disNode = ds.inverseFind();
                    min2 = ds2.dijkstra2(startNum, finalNum);
                    ArrayList<Integer> timeNode = ds2.inverseFind();
                    min3 = ds3.dijkstra3(startNum, finalNum);
                    ArrayList<Integer> chargeNode = ds3.inverseFind();
                    // 검색 결과  화면으로 전환
                    Intent intent = new Intent(getApplicationContext(), Result.class);
                    intent.putExtra("StartStation", StartStation);
                    intent.putExtra("FinalStation", FinalStation);
                    intent.putExtra("Distance", min);
                    intent.putExtra("disNode", disNode);
                    intent.putExtra("Time", min2);
                    intent.putExtra("timeNode", timeNode);
                    intent.putExtra("Charge", min3);
                    intent.putExtra("chargeNode", chargeNode);
                    startActivityForResult(intent, 303);
                }
            }
        });

        final EditText UserText = (EditText)findViewById(R.id.UserText);
        Button finding = (Button)concon.findViewById(R.id.search);
        finding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("find 입성");
//                user = UserText.getText().toString();
//                System.out.println(user);
                boolean check = false; //올바르게 입력 됬는지 안됬는지 확인용
                if(UserText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "역명을 입력해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(Station s : station){
                    if(UserText.getText().toString().equals(s.getName())) {
                        //암튼 여기서 해당역의 정보창 화면을 띄어주도록 한다
                        Intent intent = new Intent(getApplicationContext(), Information.class);
                        intent.putExtra("inforStation", s);
                        startActivityForResult(intent, 505);
                        System.out.println(s.getName());
                        check = true;
                        break;
                    }
                }
                if(!check){
                    Toast.makeText(MainActivity.this, "역명을 정확히 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        * 즐겨찾기 버튼 클릭 시
        * 새로운 화면으로 전환되고 해당 화면엔 출발역과 도착역을 지니고 있는 목록들이 있고
        * 해당 목록 클릭 시 검색버튼 클릭시 동일한 역할을 수행하여 최단 정보들을
        * 검색버튼 클릭 시 보이는 화면과 동일한 화면을 보여줌줌        * */
        Button like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("즐겨찾기 클릭됨");
                Intent intent = new Intent(getApplicationContext(), Like.class);
                intent.putExtra("station", station);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });

        //중간역 버튼 클릭시 시행되는 함수
        Button middle = findViewById(R.id.middle);
        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("중간역 입성");
                Intent intent = new Intent(getApplicationContext(), MiddleStation.class);
                intent.putExtra("station", station);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });

        //아직 오류가 있음(버튼 클릭이 인식이 안됨)
        //수정 예정
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK){
            StartStation = (Station)data.getSerializableExtra("player");
            start_tx.setText(StartStation.getName());
            setStartStation(StartStation);
        }else if(requestCode == 202 && resultCode == RESULT_OK){
            FinalStation = (Station)data.getSerializableExtra("player");
            final_tx.setText(FinalStation.getName());
            setFinalStation(FinalStation);
        }else if(requestCode == 303){
            //
        }
    }

    //역 생성 함수
    public void makeStation(){
        //역을 생성하는 부분은 역 이름이 숫자로 될 예정이라 반복문 사용하여 바꿀 예정
        Random random = new Random();
        int d, t, s, h, c, e;
        String door, toilet, suyu, handi, con, el;

        //1호선 첫번째 역부터 시작
        int stationNum = 101;
        for(int i=0; i<111; i++){
            //호선이 바뀔때마다 stationNum 을 갱신
            if(stationNum == 124)
                stationNum = 201;
            else if(stationNum == 218)
                stationNum = 301;
            else if(stationNum == 309)
                stationNum = 401;
            else if(stationNum == 418)
                stationNum = 501;
            else if(stationNum == 508)
                stationNum = 601;
            else if(stationNum == 623)
                stationNum = 701;
            else if(stationNum == 708)
                stationNum = 801;
            else if(stationNum == 807)
                stationNum = 901;
            else if(stationNum > 904)
                break;

            //0 : 없음 and 오른쪾 and 밖, 1 : 있음 and 왼쪽 and 안
            d = random.nextInt(2);
            t = random.nextInt(2);
            s = random.nextInt(2);
            h = random.nextInt(2);
            c = random.nextInt(2);
            e = random.nextInt(2);

            station.add(new Station(Integer.toString(stationNum), i));
            if(d == 0)
                door = "오른쪽";
            else
                door = "왼쪽";
            if(t == 0)
                toilet = "개찰구 밖";
            else
                toilet = "개찰구 안";
            if(s == 0)
                suyu = "없음";
            else
                suyu = "있음";
            if(h == 0)
                handi = "없음";
            else
                handi = "있음";
            if(c == 0)
                con = "없음";
            else
                con = "있음";
            if(e == 0)
                el = "없음";
            else
                el = "있음";

            station.get(i).setInfo(door, toilet, suyu, handi, con, el);
            stationNum++;
        }

        int i = 0;
        for(Station a : station){
            System.out.println(a.getName() + " / " + a.getNumber());
            i++;
        }
    }

    //출발역과 도착역을 지정해주는 함수
    public void setStartStation(Station s){
        startST = s.getName();
        startNum = s.getNumber();
        start_tx.setText(startST);
    }

    public void setFinalStation(Station s){
        finalST = s.getName();
        finalNum = s.getNumber();
        final_tx.setText(finalST);
    }

    //출발역과 도착역 객체를 반환 해주는 함수
    public Station getStartStation(){
        return StartStation;
    }

    public Station getFinalStation(){
        return FinalStation;
    }

    //Station ArrayList를 전달해주는 함수
    public ArrayList<Station> getStation(){
        return station;
    }
}