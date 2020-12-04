package org.techtown.testsubway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class MiddleDijkstra {


    int n = 111; // 정점의 갯수

    final static int INF = 30000; // 선이 없는 곳... 무지 큰수로 설정
    int data[][];	// 전체 지도 데이타

    boolean visit[]; // 방문지 확인
    int dis[]; // 시작점 부터의 거리
    int prev[]; // 도착점 전의 정점 저장

    int s,e;  // 시작점과 끝점 저장
    int stack[]; // 시작점부터 끝점까지의 순서 저장

    Vector<Integer> stackV;
    ArrayList<Integer> st;
    Dijkstra dj;
    ArrayList<int[]> std;
    public void init() // 다익스트라(Dijkstra) 알고리즘/단일 점에 따라 최단거리
    {
        dj = new Dijkstra();
        std = new ArrayList<int[]>();
        dj.distance1(std);
        dj.distance2(std);

        dis = new int[n];
        visit = new boolean[n];
        prev = new int[n];
        stack = new int[n];
        stackV=new Vector<Integer>();
        st = new ArrayList<>();
    }

    public int theLeastDistance()
    {
        return dis[e];
    }

    public void start(int start,int end)
    {
        System.out.println("==========================================================");
        System.out.println("Dijkstra start");
        System.out.println("startPoint: "+start);
        System.out.println("endPoint: "+end);
        System.out.println("===========================================================");
        s=start;
        e=end;

        init();

        int k=0;
        int min=0;



        for (int i = 0; i < n; i++) { /* 초기화 */
            dis[i] = INF;
            prev[i] = 0;
            visit[i] = false;
        }

        dis[s] = 0; /* 시작점의 거리는 0 */

        for (int i = 0; i < n; i++) {
            min = INF;
            for (int j = 0; j < n; j++) { /* 정점의 수만큼 반복 */
                if (visit[j] == false && dis[j] < min) { /* 확인하지 않고 거리가 짧은 정점을 찾음 */
                    k = j;
                    min = dis[j];
                }
            }
            visit[k] = true; /* 해당 정점 확인 체크 */

            if (min == INF)break; /* 연결된 곳이 없으면 종료 */

            /****
             * I -> J 보다 I -> K -> J의
             * 거리가 더 작으면
             * 갱신
             ****/
            for (int j = 0; j < n; j++) {
                if (dis[k] + std.get(k)[j] < dis[j]) {
                    dis[j] = dis[k] + std.get(k)[j]; /* 최단거리 저장*/
                    prev[j] = k; /* J로 가기 위해서는 K를 거쳐야 함 */
                }
            }
        }
        nowLeastDistance();   //콘솔에서 최단거리 출력
        inverseFind();			// 콘솔에서 최단 경로 출력
//        return dis[e];		// 콘솔에서 최단 경로 출력
    }

    /**** 최단 거리 출력 ****/
    public void nowLeastDistance()
    {
        System.out.printf("최단거리:  %10d       ", dis[e]);
    }

    /**** 최단 경로를 저장 ****/
    public ArrayList<Integer> inverseFind()
    {
        int tmp = 0;
        int top = -1;
        tmp = e;
        while (true) {
            stack[++top] = tmp;
            if (tmp == s)break; /* 시작점에 이르렀으면 종료 */
            tmp = prev[tmp];
        }
        /* 역추적 결과 출력 */
        stackV.removeAllElements();
        for (int i = top; i > -1; i--) {
            System.out.printf("%d", stack[i]);
            st.add(stack[i]);
            stackV.add(stack[i]);
            if (i != 0)System.out.printf(" -> ");
        }
        System.out.printf("\n");
        return st;
    }

    public Vector<Integer> getStack()
    {
        return stackV;
    }

    public int[] getSt(){
        return stack;
    }

    public int getMiddle(){
        int p1 = 0, p2 = 0; //사용자 1과 2
        int start = 0, end = st.size();
        int s = start + 1, e = st.size()-1; //st의 시작과 끝
        int prevS = start, prevE = e;
        int rand;
        Random random = new Random();
        rand = random.nextInt(2)+1; //1은 사용자 1 / 2는 사용자 2
        //출발역과 시작역이 같은지를 확인
        if(start == e) {
            System.out.println("서로 같은 역일 때");
            return -1; //-1을 반환한다는 것은 서로 같은 역이라는 의민
        }
        //2개짜리 경로인지 확인 = s와 e가 같은지 판별
        //운에 따라 사용자 1과 2중 하나의 역을 선택해서 보내줌
        if(s == e){
            System.out.println("거쳐가는 노드가 2개일 때");
            if(rand == 1)
                return st.get(s);
            else
                return st.get(e);
        }
        //3개일 때
        if(end == 3) {
            System.out.println("거쳐가는 노드가 3개일 때");
            return st.get(s);
        }
        //4개 이상일 때
        e--;
        int temp;
        System.out.println("s: " + s + " e: " + e);
        int i=1;
        while(i==1){
            System.out.println("입성");
            //배열의 왼쪽 즉 사용자 1쪽부터 시작
            temp = dj.dijkstra1(st.get(prevS), st.get(s));
            p1 += temp;
            //배열의 오른쪽(끝) 즉 사용자 2가 움직일 차례
            temp = dj.dijkstra1(st.get(prevE), st.get(e));
            p2 += temp;
            if(s >= e){
                System.out.println("p1: " + p1 + " p2: " + p2);
                if(p1 > p2)
                    return st.get(e);
                else if(p1 < p2)
                    return st.get(s);
                else{
                    System.out.println(rand);
                    if(rand == 1)
                        return st.get(e);
                    else
                        return st.get(s);
                }

            }
            prevS = s;
            prevE = e;
            s++;
            e--;

        }

        return -2; //오류코드

    }
}
