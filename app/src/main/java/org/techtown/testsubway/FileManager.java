package org.techtown.testsubway;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

class FileHandler {
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private File file;

    public FileHandler() {
        this.fileReader = null;
        this.fileWriter = null;
        this.bufferedReader = null;
        this.bufferedWriter = null;
    }

    // need to test if file.exists() returns true when file has moved after new
    // File() called.
    public void open(String filePath) throws IOException {
        file = new File(filePath);
        if (!file.exists())
            file.createNewFile();
    }

    public void write(String s) throws IOException {
        if (fileWriter == null)
            fileWriter = new FileWriter(file);
        if (bufferedWriter == null)
            bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(s, 0, s.length());
    }

    public void writeTop(String s) throws IOException {
        if (fileReader == null)
            fileReader = new FileReader(file);
        if (bufferedReader == null)
            bufferedReader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        while (true) {
            String line = bufferedReader.readLine();
            System.out.println(line);
            if (line == null)
                break;
            list.add(line);
        }
        write(s);
        if (list != null)
            for (String line : list)
                write(line);
        return;
    }

    public List<Integer> read() throws IOException, FileNotFoundException {
        if (fileReader == null)
            fileReader = new FileReader(file);
        if (bufferedReader == null)
            bufferedReader = new BufferedReader(fileReader);
        List<Integer> list = new ArrayList<>();
        String line = bufferedReader.readLine();
        if (line == null)
            return null;
        else
            line = line.trim();
        for (String s : line.split(" "))
            list.add(Integer.parseInt(s));
        return list;
    }

    public void close() throws IOException {
        if (bufferedReader != null)
            bufferedReader.close();
        if (bufferedWriter != null)
            bufferedWriter.close();
        if (fileReader != null)
            fileReader.close();
        if (fileWriter != null)
            fileWriter.close();
    }

    public boolean delete() {
        return file.delete();
    }
}

public class FileManager {
    private String filePath;
    private FileHandler fHandler;
    private ArrayList<List<Integer>> routeList;

    public FileManager() {
        filePath = null;
        routeList = new ArrayList<List<Integer>>();
        fHandler = new FileHandler();
    }

    // 프로그램 시작때 불려야함
    public void open(String filePath) throws IOException {
        fHandler.open(filePath);
        this.filePath = filePath;
    }

    // 프로그램 종료때 불려야함
    public void close() throws IOException {
        fHandler.close();
    }

    // 같은 파일로 바뀌지 않는다
    public void changeFileTo(String fileName) throws IOException {
        if (filePath.equals(fileName))
            return;
        fHandler.close();
        fHandler.open(fileName);
        filePath = fileName;
    }

    // 추가
    public void push(List<Integer> list) {
        routeList.add(0, list);
    }

    // 제거
    public void remove(List<Integer> list) {
        routeList.remove(list);
    }

    // 비우기
    public void clear() {
        routeList.clear();
    }

    // 불러오기
    public void load() throws IOException, FileNotFoundException {
        while (true) {
            List<Integer> list = fHandler.read();
            if (list == null)
                break;
            routeList.add(routeList.size(), list);
        }
    }

    // 저장
    public void save() throws IOException {
        int total_size = routeList.size();
        while (total_size != 0) {
            List<Integer> list = routeList.remove(0);
            total_size -= 1;
            int size = list.size();
            String line = "";
            for (int i = 0; i < size; ++i)
                line += list.toArray()[i] + " ";
            fHandler.write(line.trim() + "\n");
        }
    }

    public boolean contains(List<Integer> list){
        return routeList.contains(list);
    }

    // 리스트 전체를 반환한다
    public Collection<List<Integer>> getCollection() {
        return routeList;
    }

    public void printList() {
        for (List<Integer> list : routeList) {
            System.out.println(list.toString());
        }
    }
}