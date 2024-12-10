package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Gauss {
    private int dim;
    private int n;
    private ArrayList<op> ops_list = new ArrayList<>();
    int [][] D;
    public Gauss(int dim){
        this.dim = dim;
    }
    public void GenerateOperations(){
        for (int i = 0; i < dim; i++){
            for (int j = i+1; j < dim; j++){
                ops_list.add(new op(ABC.A, i, j, -1));
                for(int k = i; k < dim; k++){
                    ops_list.add(new op(ABC.B, i, k, j));
                    ops_list.add(new op(ABC.C, i, k, j));
                }
            }
        }
    }

    public void search_update(ABC t, int a, int b, int start, int ii){
        for(int j = start; j >= 0; j--){
            op op2 = ops_list.get(j);
            if(op2.t == t && op2.data[1] == a && op2.data[2] == b){
                D[j][ii] = 1;
                break;
            }
        }
    }

    public void find_dependencies(){
        n = ops_list.size();
        D = new int[n][n];
        int last_A = -1;
        for(int i = 0; i < n; i++){

            op op1 = ops_list.get(i);
            int a = op1.data[0];
            int b = op1.data[1];
            int c = op1.data[2];

            if(op1.t == ABC.A){
                if(op1.data[0] != 0) {
                    search_update(ABC.C, a, a, i - 1, i);
                    search_update(ABC.C, a, b, i - 1, i);
                }
                last_A = i;
            }

            else if(op1.t == ABC.B){
                D[last_A][i] = 1;
                if(op1.data[0] != op1.data[1]) {
                    search_update(ABC.C, b, a, i - 1, i);
                }
            }

            else if (op1.t == ABC.C){
                D[i-1][i] = 1;
                if (op1.data[0] != op1.data[1]) {
                    search_update(ABC.C, b, c, i - 1, i);
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

    public void getFoataClasses(){

        int[] inside = new int[n];
        int[] vis = new int[n];

        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if(D[i][j] == 1){
                    inside[j]++;
                }
            }
        }

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
                op op1 = ops_list.get(y);
                System.out.print(op1.t.toString() + "," + op1.data[0] + "," + op1.data[1]);
                if(op1.data[2] != -1){
                    System.out.print("," + op1.data[2]);
                }
                System.out.print(" ");
            }
            System.out.print("]\n");
        }
    }
}