package org.techtown.testsubway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectStation extends AppCompatActivity {
    String selected_item;
    MainActivity ma;
    ArrayList<Station> s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);

        ma = new MainActivity();
        s = ((MainActivity)MainActivity.context_main).getStation();

        ListView station_list = findViewById(R.id.station_list);

        //데이터를 저장할 리스트
        List<String> list = new ArrayList<>();

        //리스트와 리스트뷰 연결 어댑터
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        //리스트뷰의 어댑터를 지정해준다.
        station_list.setAdapter(adapter);


        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        station_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                //클릭한 아이템의 문자열을 가져옴
                selected_item = (String)adapterView.getItemAtPosition(position);
                Station st_item = s.get(position);
                Intent intent = new Intent();
                intent.putExtra("player", st_item);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        for(int i=0; i<s.size(); i++){
            list.add(s.get(i).getName());
        }


        //리스트뷰에 보여질 아이템을 추가
        /*
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");

         */


    }

    public String getSelected_item(){
        return selected_item;
    }
}