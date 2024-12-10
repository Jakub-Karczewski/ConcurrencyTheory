package org.example;

public enum ABC {
    A(0),
    B(1),
    C(2);
    public final int value;

    ABC(int id) {
        this.value = id;
    }
    public int getVal() {
        return value;
    }

    @Override
    public String toString() {
        switch(value){
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
        };
        return null;
    }
}
