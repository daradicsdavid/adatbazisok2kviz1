package Application;

import Input.Input;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private static List<String> inputBitSorozatok;

    public static void main(String[] args) {
        feladatMegoldas(Input.feladatsor.inputBitSorozatok);
    }

    private static void feladatMegoldas(List<String> bitSorozatok) {
        inputBitSorozatok = bitSorozatok;
        System.out.println("A bitsorozatok:");
        for (int i = 0; i < inputBitSorozatok.size(); i++) {
            System.out.println(i + ". sorozat:" + inputBitSorozatok.get(i));
        }

        System.out.println("A bitsorozatok paritásbitsorozata:");
        String paritasSorozat = paritasSorozatSzamitas();
        System.out.println(paritasSorozat);

        System.out.println("Ellenőrzés:");
        inputBitSorozatok = new ArrayList<>(inputBitSorozatok);
        inputBitSorozatok.add(paritasSorozat);
        String ellenorzoParitasSorozat = paritasSorozatSzamitas();
        System.out.println(ellenorzoParitasSorozat);
    }

    private static String paritasSorozatSzamitas() {
        List<Character> ellenorzoSorozatKarakterek = new ArrayList<>();
        for (int i = 0; i < inputBitSorozatok.get(0).length(); i++) {
            ellenorzoSorozatKarakterek.add(ellenorzoBitSzamolas(i));
        }

        return getStringRepresentation(ellenorzoSorozatKarakterek);
    }

    private static Character ellenorzoBitSzamolas(int i) {
        return inputBitSorozatok.stream().filter(s -> s.charAt(i) == '1').count() % 2 == 0 ? '0' : '1';
    }

    static String getStringRepresentation(List<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        return builder.toString();
    }

}
