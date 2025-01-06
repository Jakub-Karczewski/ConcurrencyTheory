package org.example;

import java.util.Objects;
import java.util.concurrent.Semaphore;

public class op implements Runnable{
    public ABC t;
    public int[] data = new int[3];
    static public float [][] Matrix;
    static public float [][] Coeff;
    static public float [][] Multres;
    public op(ABC type, int a, int b, int c){
        data[0] = a;
        data[1] = b;
        data[2] = c;
        this.t = type;
    }

    @Override
    public void run() {
        //System.out.println(t.toString() + " " + data[0] + " " + data[1] + " " + data[2]);
        switch(t){
            case A:
                Coeff[data[0]][data[1]] = Matrix[data[1]][data[0]]/Matrix[data[0]][data[0]];
                break;
            case B:
                Multres[data[2]][data[1]] = Coeff[data[0]][data[2]] * Matrix[data[0]][data[1]];
                break;
            case C:
                Matrix[data[2]][data[1]] -= Multres[data[2]][data[1]];
                break;
        }
    }
}
