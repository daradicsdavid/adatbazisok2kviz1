package Application;


import Application.input.Input;
import org.apache.commons.text.StrSubstitutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Application {

    private static List<String> inputBitSorozatok;

    public static void main(String[] args) {
        feladatMegoldas(Input.feladatsor.inputBitSorozatok);
    }

    private static void feladatMegoldas(List<String> bitSorozatok) {
        inputBitSorozatok = bitSorozatok;
        List<BitsorozatParitasBitekkel> szamitas = paritasSorozatSzamitas();
        System.out.println("A bitsorozatok:");
        String template = "Az ${i}. sorozat: ${sorozat}, páratlan paritásbit: ${paratlanParitasBit}, páros paritásbit: ${parosParitasBit}.";
        for (int i = 0; i < szamitas.size(); i++) {
            Map<String, String> data = new HashMap<String, String>();
            data.put("i", String.valueOf(i));
            data.put("sorozat", szamitas.get(i).bitSorozat);
            data.put("paratlanParitasBit", String.valueOf(szamitas.get(i).paratlanParitasBit));
            data.put("parosParitasBit", String.valueOf(szamitas.get(i).parosParitasBit));
            System.out.println(StrSubstitutor.replace(template, data));
        }

        System.out.println("");
    }

    private static List<BitsorozatParitasBitekkel> paritasSorozatSzamitas() {

        List<BitsorozatParitasBitekkel> szamitas = new ArrayList<BitsorozatParitasBitekkel>();
        for (String inputBitSorozat : inputBitSorozatok) {
            szamitas.add(new BitsorozatParitasBitekkel(inputBitSorozat, ellenorzoBitSzamolas(inputBitSorozat, true), ellenorzoBitSzamolas(inputBitSorozat, false)));
        }
        return szamitas;
    }

    private static Character ellenorzoBitSzamolas(String bitSorozat, boolean paratlan) {
        return IntStream.range(0, bitSorozat.length()).filter(i -> paratlan ? i % 2 == 0 : i % 2 == 1).mapToObj(bitSorozat::charAt).filter(c -> c == '1').count() % 2 == 0 ? '0' : '1';
    }


}
