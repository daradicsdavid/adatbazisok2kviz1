package input;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Input implements Serializable {
    public Double atlagosKeresesiIdo;// ms
    public Double rotaciosKeses;// ms
    public Double atviteliIdo;// ms
    public Double blokkBeolvasas;// ms
    public Integer kezdoSav;// ms
    public Map<Integer, Integer> keresek;// ms

    public static Input feladatsor;
    public static Input orai;

    static {
        fillFeladatSor();
        fillOrai();
    }

    private static void fillOrai() {
        orai = new Input();
        orai.atlagosKeresesiIdo = 6.46;
        orai.rotaciosKeses = 4.17;
        orai.atviteliIdo = 0.13;
        orai.blokkBeolvasas = orai.rotaciosKeses + orai.atviteliIdo;
        orai.kezdoSav = 8000;
        orai.keresek = new HashMap<Integer, Integer>();
        orai.keresek.put(64000, 20);
        orai.keresek.put(40000, 30);
        orai.keresek.put(8000, 0);
        orai.keresek.put(56000, 0);
        orai.keresek.put(16000, 10);
        orai.keresek.put(24000, 0);
    }

    private static void fillFeladatSor() {
        feladatsor = new Input();
        feladatsor.atlagosKeresesiIdo = 6.46;
        feladatsor.rotaciosKeses = 4.17;
        feladatsor.atviteliIdo = 0.13;
        feladatsor.blokkBeolvasas = feladatsor.rotaciosKeses + feladatsor.atviteliIdo;
        feladatsor.kezdoSav = 32000;
        feladatsor.keresek = new HashMap<Integer, Integer>();
        feladatsor.keresek.put(8000, 0);
        feladatsor.keresek.put(48000, 1);
        feladatsor.keresek.put(4000, 10);
        feladatsor.keresek.put(40000, 20);
    }

    public Input deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Input) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
