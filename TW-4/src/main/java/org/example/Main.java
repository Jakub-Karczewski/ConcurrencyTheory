package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        int n_phil = 15, war = 4;
        String MyPath = "C:\\Users\\HP\\IdeaProjects\\TW-4";
        long mint = 0, sec = 60;
        ArrayList<Simulation> sim_list = new ArrayList<>();
        for(int i = 1; i <= 6; i++){
            Simulation s_i = new Simulation(n_phil, MyPath, i, sec, mint);
            sim_list.add(s_i);
        }

        for(int i = 2; i <= 6; i++){
            sim_list.get(i-1).start_sim();
        }

        System.out.println("Koncze MAINA");
    }

}