package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Gauss {
    private int dim;
    private int n;
    private int type;
    private ArrayList<op> operations_list = new ArrayList<>();

    private ArrayList<op> operations_ordered = new ArrayList<>();

    private ExecutorService executor = Executors.newFixedThreadPool(8);
    int [][] D;
    public Gauss(float[][] arr, int type){
        this.dim = arr.length;
        this.type = type;
        op.Matrix = arr;
        op.Multres = new float[dim+1][dim+1];
        op.Coeff = new float[dim][dim];
    }
    public void GenerateOperations(){

        for (int i = 0; i < dim; i++){
            for (int j = i+1; j < dim; j++){
                operations_list.add(new op(ABC.A, i, j, -1));
                for(int k = i; k < dim; k++){
                    operations_list.add(new op(ABC.B, i, k, j));
                    operations_list.add(new op(ABC.C, i, k, j));
                }
            }
        }

        for (int i = 0; i < dim; i++){
            for (int j = i+1; j < dim; j++){
                operations_ordered.add(new op(ABC.A, i, j, -1));
            }
            for (int j = i+1; j < dim; j++){
                for(int k = i; k < dim+type; k++){
                    operations_ordered.add(new op(ABC.B, i, k, j));
                }
                for(int k = i; k < dim+type; k++) {
                    operations_ordered.add(new op(ABC.C, i, k, j));
                }
            }
        }
    }

    public void ExecuteGauss() throws InterruptedException, ExecutionException {
        List<Future<?>> futures = new ArrayList<Future<?>>();
        int jd = operations_list.size();
        op prev_op = null;
        for(op op1: operations_ordered){
            if(prev_op != null && prev_op.t != op1.t){
                for(Future<?> future : futures)
                {
                    future.get();
                }
                futures.clear();
            }
            Future<?> f = executor.submit(op1);
            futures.add(f);
            prev_op = op1;
        }
    }

    int calc_dist(int a, int b){
        int dim_ = dim-1; // ostatni element to dim-1
        int dist = (1 + 2 * (dim_ - a + 1)) * (b - a - 1);
        dist += (1 + 2 * (dim_ - (a-1) + 1)) * (dim_ - (a-1));
        return dist;

    }

    public void find_dependencies(){

        //Wygenerowanie i wypisanie operacji(ta funkcja + funkcja "getFoataClasses_fromDependencies" ) działają dla macierzy kwadratowych
        // Jeśli chodzi o znalezienie rozwiązania układu równań, to podaje się macierz prostokątną o wymiarach n na n+1

        this.n = operations_list.size();
        D = new int[n][n];
        int last_A = -1;
        for(int i = 0; i < n; i++){
            op op1 = operations_list.get(i);
            int a = op1.data[0];
            int b = op1.data[1];
            int c = op1.data[2];
            if(op1.t == ABC.A){
                last_A = i;
                if(a != 0){
                    int dist1 = calc_dist(a, b) - 4;
                    int dist2 = dist1 - (b-a) * ((dim-1 - (a-1) + 1) * 2 + 1);
                    D[i - dist2][i] = 1; //c(a-1, a, b)
                    D[i - dist1][i] = 1; //C(a-1, a, a)
                }
            }
            else if(op1.t == ABC.B) {
                D[last_A][i] = 1;
                if(op1.data[0] != op1.data[1] && a != 0) {
                    int dist3 = (b-a) * 2 + 1; //wracamy do A(a, c)
                    dist3 += calc_dist(a, c);
                    dist3 -= (b - (a-1)) * 2;
                    D[i - dist3][i] = 1;
                }
            }
            else if (op1.t == ABC.C){
                D[i-1][i] = 1;
                if (op1.data[0] != op1.data[1] && a != 0) {
                    int dist4 = (b-a) * 2 + 2; //wracamy do A(a, c)
                    dist4 += (1 + 2 * (dim-1 - a + 1)) * (c - a - 1); // schodzimy do A(a-1, dim-1)
                    dist4 += (1 + 2 * (dim-1 - a + 1)) * (dim-1 - c + 1); //schodzimy do A(a-1, c)
                    dist4 -= (b - (a-1)) * 2;
                    D[i - dist4][i] = 1;
                }
            }
        }
    }

    public void BFS_ways(int start){
        int[] ways = new int[n];
        ArrayDeque<Integer> Q = new ArrayDeque<>();
        Q.addLast(start);
        while(!Q.isEmpty()){
            Integer w = Q.pollFirst();
            ways[w]++;
            for (int i = 0; i < n; i++){
                if(D[w][i] == 1){
                    Q.add(i);
                }
            }
        }
        for (int i = 0; i < n; i++){
            if(D[start][i] == 1 && ways[i] > 1) {
                D[start][i] = 0;
            }
        }
    }

    public void RemoveRedundant(){
        for(int i = 0; i < n; i++){
            BFS_ways(i);
        }
    }

    public void getFoataClasses_fromDependencies(){
        System.out.println("n: " + n);
        int[] inside = new int[n];
        int[] vis = new int[n];

        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if(D[i][j] == 1){
                    inside[j]++;
                }
            }
        }

        /*
        for (int i = 0; i < n; i++){
          System.out.print(inside[i] + " ");
         }
         System.out.print("\n");
         */

        ArrayList<ArrayList<Integer>> Foata = new ArrayList<>();
        int id = 1;
        while(true) {

            boolean found = false;
            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (vis[i] == 0 && inside[i] == 0) {
                    vis[i] = id;
                    temp.add(i);
                    found = true;
                }
            }
            if(!temp.isEmpty()){
                Foata.add(temp);
            }

            for (int i = 0; i < n; i++){
                if(vis[i] == id){
                    for(int j = 0; j < n; j++){
                        if(D[i][j] == 1){
                            inside[j]--;
                        }
                    }
                }
            }

            if(!found){
                break;
            }
            id++;
        }

        System.out.println("Foata classes:");
        for (ArrayList<Integer> L: Foata){
            System.out.print("[ ");
            for (Integer y: L){
                op op1 = operations_list.get(y);
                System.out.print(op1.t.toString() + "," + (op1.data[0]+1) + "," + (op1.data[1]+1));
                if(op1.data[2] != -1){
                    System.out.print("," + (op1.data[2]+1));
                }
                System.out.print(" ");
            }
            System.out.print("]\n");
        }
    }

}
