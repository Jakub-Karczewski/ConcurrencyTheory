package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Observer {
    private ArrayList<ArrayList<Long>> times = new ArrayList<>();
    private int num;
    private ArrayList<Integer> units = new ArrayList<>();

    private FileWriter fout;
    public Observer(int num1, String path1) throws IOException {
        this.num = num1;
        fout = new FileWriter(path1);
        for(int i = 0; i < num; i++) {
            ArrayList<Long> temp = new ArrayList<>();
            times.add(temp);
            units.add(Integer.valueOf(0));
        }
    }

    public synchronized void add_times(int ID, long del_t){
        this.times.get(ID).add(Long.valueOf(del_t));

    }

    public synchronized void add_units(int ID){
        Integer value = units.get(ID);
        value = value + 1;
        units.set(ID, value);
    }




    public void print_times() throws IOException {
        for (ArrayList<Long> re : times) {
            for (int j = 0; j < re.size(); j++) {
                String to_write = "" + re.get(j);
                fout.write(to_write);
                if (j < re.size() - 1) {
                    fout.write(";");
                }
            }
            fout.write("\n");
        }
        fout.close();
    }

    public void print_units(){
        for(Integer X: units){
            System.out.println(X.intValue());
        }
    }
}
