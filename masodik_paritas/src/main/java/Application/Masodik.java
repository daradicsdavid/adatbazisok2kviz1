package Application;

import Input.Input;
import org.apache.commons.text.StrSubstitutor;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Masodik {

    private static List<String> inputBitSorozatok;

    public static void main(String[] args) {


        /*        feladatMegoldas(Input.feladatsor.inputBitSorozatok);*/
        feladatMegoldas(Arrays.asList(""));
    }

    private static void feladatMegoldas(List<String> bitSorozatok) {
        inputBitSorozatok = bitSorozatok;
        Map<String, Character> szamitas = paritasSorozatSzamitas();
        System.out.println("A bitsorozatok:");
        String template = "Az ${i}. sorozat: ${sorozat} paritásbit: ${paritásbit}.";
        for (int i = 0; i < inputBitSorozatok.size(); i++) {
            Map<String, String> data = new HashMap<String, String>();
            data.put("i", String.valueOf(i));
            data.put("sorozat", inputBitSorozatok.get(i));
            data.put("paritásbit", String.valueOf(szamitas.get(inputBitSorozatok.get(i))));
            System.out.println(StrSubstitutor.replace(template, data));
        }

        System.out.println("");
    }

    private static Map<String, Character> paritasSorozatSzamitas() {

        Map<String, Character> bitsorozatokParitasBitekkel = new HashMap<>();
        for (String inputBitSorozat : inputBitSorozatok) {
            bitsorozatokParitasBitekkel.put(inputBitSorozat, ellenorzoBitSzamolas(inputBitSorozat));
        }
        return bitsorozatokParitasBitekkel;
    }

    private static Character ellenorzoBitSzamolas(String bitSorozat) {
        return bitSorozat.chars().filter(c -> c == '1').count() % 2 == 0 ? '0' : '1';
    }

    static String getStringRepresentation(List<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        return builder.toString();
    }

}
