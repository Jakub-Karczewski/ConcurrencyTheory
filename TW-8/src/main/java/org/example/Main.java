package org.example;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        float [][] M = {{-3.0f, 4.0f, 8.0f, -23.0f}, {-10.0f, 2.0f, 16.0f, -18.0f}, {0.0f, 2.0f, 3.0f, -7.0f}};
        Gauss g1 = new Gauss(M, 1);
        g1.GenerateOperations();
        //g1.find_dependencies();
        //g1.RemoveRedundant();
        //g1.getFoataClasses_fromDependencies();
        g1.ExecuteGauss();
        for(int i = 0; i < M.length; i++){
            System.out.println(Arrays.toString(M[i]));
        }
    }
}