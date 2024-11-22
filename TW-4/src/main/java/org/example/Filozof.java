package org.example;

import java.util.concurrent.Semaphore;

public class Filozof extends Thread {
    private Semaphore LeftFork;
    private Semaphore RightFork;
    static private int actID = 0;

    private int myID;
    private Semaphore multi_sem;
    private Semaphore tryb;

    private Object synch_block;
    private Observer JD;
    private static final int wait_time = 100;

    private boolean run_bool = true;
    private int wariant;


    public Filozof(Semaphore l1, Semaphore r1, Semaphore mult, Object block, Observer obs, int war) {
        this.myID = actID;
        actID++;
        this.LeftFork = l1;
        this.RightFork = r1;
        this.multi_sem = mult;
        this.synch_block = block;
        this.JD = obs;
        this.wariant = war;
    }

    public void turn_off(){
        this.run_bool = false;
        actID = 0;
    }

    public void free_sem(){
        boolean x = LeftFork.tryAcquire();
        LeftFork.release(1);
    }

    public void run() {

        switch(this.wariant) {
            case 1:
                Wariant1();
                break;
            case 2:
                Wariant2();
                break;
            case 3:
                Wariant3();
                break;
            case 4:
                Wariant4();
                break;
            case 5:
                Wariant5();
                break;
            case 6:
                Wariant6();
                break;
        }
    }

    public void Wariant1(){

        boolean special = false, normal = false, reset_ = true;
        long t1 = 0, t2 = 0;

        while(run_bool){
            try {

                LeftFork.acquire(1);
                RightFork.acquire(1);

                System.out.println(myID + " zaczyna jesc");
                Thread.sleep(wait_time);
                System.out.println(myID + " konczy jesc");

            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }

            LeftFork.release();
            RightFork.release();

        }
    }
    public void Wariant2() {

        boolean special = false, normal = false, reset_ = true;
        long t1 = 0, t2 = 0;

        while (this.run_bool) {
            if (reset_) {
                t1 = System.nanoTime();
                reset_ = false;
            }
            boolean bool_l = false, bool_r = false;
            bool_l = LeftFork.tryAcquire(1);
            if (bool_l) {
                bool_r = RightFork.tryAcquire(1);
                if (!bool_r) {
                    LeftFork.release(1);
                }
            }
            if (bool_l && bool_r) {
                t2 = System.nanoTime();
                reset_ = true;
                if (run_bool) {
                    JD.add_units(myID);
                    System.out.println(myID + " bierze oba");
                }
                JD.add_times(myID, t2 - t1);

                try {
                    Thread.sleep(wait_time);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                LeftFork.release(1);
                RightFork.release(1);
                System.out.println(myID + " odklada oba");
            }
        }
    }
    public void Wariant3() {

        boolean special = false, normal = false, reset_ = true;
        long t1 = 0, t2 = 0;

        while (run_bool)
        {
            try {

                t1 = System.nanoTime();

                if (myID % 2 == 0) {
                    RightFork.acquire(1);
                    LeftFork.acquire(1);
                } else {
                    LeftFork.acquire(1);
                    RightFork.acquire(1);
                }

                t2 = System.nanoTime();

                if(run_bool){
                    JD.add_units(myID);
                    System.out.println(myID + " zaczyna jesc");
                }
                JD.add_times(myID, t2 - t1);

                Thread.sleep(wait_time);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            LeftFork.release(1);
            RightFork.release(1);
            System.out.println(myID + " konczy jesc");
        }
    }
    public void Wariant4(){

        boolean special = false, normal = false, reset_ = true;
        long t1 = 0, t2 = 0;

        while(run_bool)
        {
            try {
                double choice = Math.random();
                //System.out.println(choice);
                t1 = System.nanoTime();
                if (choice <= 0.5) {
                    LeftFork.acquire(1);
                    RightFork.acquire(1);
                } else {
                    RightFork.acquire(1);
                    LeftFork.acquire(1);
                }
                t2 = System.nanoTime();

                JD.add_times(myID, t2 - t1);
                if(run_bool){
                    JD.add_units(myID);
                    System.out.println(myID + " zaczyna jesc");
                }

                Thread.sleep(wait_time);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LeftFork.release(1);
            RightFork.release(1);
            System.out.println(myID + " konczy jesc");
        }
    }
    public void Wariant5(){

        boolean special = false, normal = false, reset_ = true;
        long t1 = 0, t2 = 0;

        while(run_bool)
        {
            try {
                multi_sem.acquire(1);
                //System.out.println(multi_sem.availablePermits());

                t1 = System.nanoTime();
                LeftFork.acquire(1);
                RightFork.acquire(1);
                t2 = System.nanoTime();

                JD.add_times(myID, t2 - t1);
                if(run_bool){
                    JD.add_units(myID);
                    System.out.println(myID + " zaczyna jesc");
                }

                Thread.sleep(wait_time);

                LeftFork.release(1);
                RightFork.release(1);
                System.out.println(myID + " konczy jesc");

                multi_sem.release(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void Wariant6(){
        boolean special = false, normal = false, reset_ = true;
        long t1 = 0, t2 = 0;
        while(run_bool)
        {
            try {

                t1 = System.nanoTime();
                synchronized (synch_block) {
                    normal = multi_sem.tryAcquire(1);
                    if (!normal) {
                        special = true;
                    }
                }

                if (special) {
                    RightFork.acquire(1);
                    LeftFork.acquire(1);
                } else {
                    LeftFork.acquire(1);
                    RightFork.acquire(1);

                }
                t2 = System.nanoTime();

                JD.add_times(myID, t2 - t1);
                if(this.run_bool) {
                    JD.add_units(myID);
                    System.out.println(myID + " zaczyna jesc");
                }
                Thread.sleep(100);


            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }

            synchronized(synch_block){
                if(special){
                    special = false;
                }
                else{
                    multi_sem.release(1);
                }
                LeftFork.release(1);
                RightFork.release(1);
                System.out.println(myID + " konczy jesc");
            }
        }
    }
}
