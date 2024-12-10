package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Gauss g1 = new Gauss(10);
        g1.GenerateOperations();
        g1.find_dependencies();
        g1.RemoveRedundant();
        g1.getFoataClasses();
    }
}