package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Simulation {
    private int n_phil;
    private int wariant_;
    private String path_;

    private long seconds_;
    private long minutes_;
    public Simulation(int n, String MyPath, int war, long sec, long mint){
        this.n_phil = n;
        this.wariant_ = war;
        this.path_ = MyPath;
        this.minutes_ = mint;
        this.seconds_ = sec;
    }

    public void start_sim() throws IOException, InterruptedException {

        Semaphore temp_mult = new Semaphore(n_phil - 1);
        Object obj1 = new Object();
        Observer obs1 = new Observer(n_phil, path_ + "\\out_" + wariant_ + ".txt");
        ArrayList<Filozof> phil_list = new ArrayList<>();
        Semaphore l_first = new Semaphore(1);
        Semaphore left_beg = null, r_first;

        for (int i = 1; i <= n_phil; i++) {
            if(i == 1){
                left_beg = l_first;
            }
            if(i < n_phil){
                r_first = new Semaphore(1);
            }
            else{
                r_first = left_beg;
            }

            Filozof f1 = new Filozof(l_first, r_first, temp_mult, obj1, obs1, wariant_);
            phil_list.add(f1);
            l_first = r_first;
        }

        long start_t = System.nanoTime();

        for(int i = 0; i < n_phil; i++){
            phil_list.get(i).start();
        }

        long pot = 1000 * 1000 * 1000;

        while(true){
            if(System.nanoTime() - start_t >=  pot * (60 * minutes_ + seconds_) ){
                for(int i = 0; i < n_phil; i++){
                    phil_list.get(i).turn_off();
                }
                for(int i = 0; i < n_phil; i++){
                    phil_list.get(i).free_sem();
                }
                System.out.println("Zrobilem");
                Thread.sleep(500);
                break;
            }
        }

        obs1.print_times();
        System.out.println("\nKONIEC WARIANTU " + wariant_ + "\n");
        obs1.print_units();
    }
}
