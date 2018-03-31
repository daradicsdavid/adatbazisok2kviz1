package application;

import input.Input;

public class Application {

    static Input adatok;

    public static void main(String[] args) {
        System.out.println("Órai feladat megoldása");
        feladtMegoldas(Input.orai.deepClone());
        System.out.println("==================================================================================");
        System.out.println("==================================================================================");
        System.out.println("==================================================================================");
        System.out.println("Feladatsor megoldása");
        feladtMegoldas(Input.feladatsor.deepClone());
    }

    private static void feladtMegoldas(Input input) {

        adatok = input;

        Lift lift = new Lift(adatok.deepClone());
        lift.runLift();
        Fifo fifo = new Fifo(adatok.deepClone());
        //fifo.runFifo();

    }

    private static Double keresesiIdoBecslese(Integer szektor1, Integer szektor2) {
        return ((double) Math.abs(szektor1 - szektor2) / (double) 4000) + 1;
    }
}
