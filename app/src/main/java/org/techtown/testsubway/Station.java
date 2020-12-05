package org.techtown.testsubway;

import java.io.Serializable;

public class Station implements Serializable {
    String name; //역 이름
    int number; //역 번호
    String info; //역 정보

    public Station(String name, int num){
        this.name = name;
        number = num;
        info = "";
    }

    //내리는 문, 화장실 유무(개찰구 안인지 밖인지), 수유시설, 장애인 시설, 편의점, 엘레베이터
    public void setInfo(String door, String toilet, String suyu, String handi, String con, String el){
        info += "역 이름: " + name + "\n" +
                "내리실 문: " + door + "\n" +
                "화장실: " + toilet + "\n" +
                "수유시설: " + suyu + "\n" +
                "장애인 시설: " + handi + "\n" +
                "편의점: " + con + "\n" +
                "엘레베이터: " + el + "\n";
    }

    public String getInfo(){
        return info;
    }

    public String getName() { return name; }

    public int getNumber() { return number; }
}
