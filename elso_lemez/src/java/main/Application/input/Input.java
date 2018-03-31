package main.Application.input;

import java.util.Map;

public class Input {
    public Integer felulet = 10;
    public Integer savPerFelulet = 100000;
    public Integer szektorPerSav = 1000;
    public Integer szektorMeret = 1024;//bájt
    public Integer hezagPerSavSzazalek = 20;
    public Integer lemezForgasiSebesseg = 10000;//percenként
    public Double fejMozgasEgySavra = 0.002; //ms
    public Integer szektorPerBlokk = 64;


    public static Input feladatsor;
    public static Input orai;

    static {
        feladatsor = new Input();
        feladatsor.felulet = 10;
        feladatsor.savPerFelulet = 100000;
        feladatsor.szektorPerSav = 1000;
        feladatsor.szektorMeret = 1024;//bájt
        feladatsor.hezagPerSavSzazalek = 20;
        feladatsor.lemezForgasiSebesseg = 10000;//percenként
        feladatsor.fejMozgasEgySavra = 0.002; //ms
        feladatsor.szektorPerBlokk = 64;
    }

    static {
        orai = new Input();
        orai.felulet = 16;
        orai.savPerFelulet = 65536;
        orai.szektorPerSav = 256;
        orai.szektorMeret = 4096;//bájt
        orai.hezagPerSavSzazalek = 10;
        orai.lemezForgasiSebesseg = 7200;//percenként
        orai.fejMozgasEgySavra = 0.00025; //ms
        orai.szektorPerBlokk = 4;
    }
}
