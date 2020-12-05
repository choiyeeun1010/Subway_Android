package org.techtown.testsubway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class like extends AppCompatActivity {
    List<Integer> selected_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        // 문자열을 저장할 리스트
        List<String> list = new ArrayList<>();

        // 역들
        final List<Station> StationList = ((MainActivity)MainActivity.context_main).getStation();

        // 즐겨찾기한 역번호들
        final List<List<Integer>> favList = (List<List<Integer>>) MainActivity.fileManager.getCollection();

        // 문자열 입력
        for (List<Integer> favRoute : favList)
            list.add("출발역: "+ StationList.get(favRoute.get(0)).getName()+" 도착역: " + StationList.get(favRoute.get(1)).getName());

        ListView like_list = findViewById(R.id.like_list);

        //리스트와 리스트뷰 연결 어댑터
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        //리스트뷰의 어댑터를 지정해준다.
        like_list.setAdapter(adapter);

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        like_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                System.out.println("클릭한 위치: "+position);
                //클릭한 아이템의 문자열을 가져옴
                selected_item = (List<Integer>)favList.get(position);
                Dijkstra ds, ds2, ds3; //다익스트라 변수
                int min = -1; //최단 시간 값
                int min2 = -1;
                int min3 = -1;
                int startNum = selected_item.get(0);
                int finalNum = selected_item.get(1);
                Station StartStation = StationList.get(selected_item.get(0));
                Station FinalStation = StationList.get(selected_item.get(1));
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
                finish();
            }
        });

        ImageButton back = findViewById(R.id.back);
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