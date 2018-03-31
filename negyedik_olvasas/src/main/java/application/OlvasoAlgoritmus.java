package application;

import input.Input;
import org.apache.commons.text.StrSubstitutor;
import util.ValueThenKeyComparator;

import java.util.*;

public abstract class OlvasoAlgoritmus {
    protected final Input adatok;
    protected Double elteltIdo;
    protected Integer jelenlegiSav;


    public OlvasoAlgoritmus(Input adatok) {
        this.adatok = adatok;
        adatok.keresek = keresekRendezese(adatok.keresek);
        elteltIdo = Double.valueOf(0);
        jelenlegiSav = adatok.kezdoSav;
    }


    protected void kiiras(Map.Entry<Integer, Integer> next) {
        Double keresesiIdo = keresesiIdoBecslese(jelenlegiSav, next.getKey());
        elteltIdo = elteltIdo + keresesiIdo + adatok.blokkBeolvasas;
        jelenlegiSav = next.getKey();

        String template = "Blokk sorszáma:${sorszam} , teljesítés ideje: ${ido}";
        Map<String, String> data = new HashMap<String, String>();
        data.put("sorszam", String.valueOf(next.getKey()));
        data.put("ido", String.valueOf(elteltIdo));
        System.out.println(StrSubstitutor.replace(template, data));

        deleteItem(next);
    }

    protected void deleteItem(Map.Entry<Integer, Integer> next) {
        adatok.keresek.remove(next.getKey());
    }


    protected Map<Integer, Integer> keresekRendezese(Map<Integer, Integer> keresek) {
        List<Map.Entry<Integer, Integer>> list = mapToList(keresek);
        Collections.sort(list, new ValueThenKeyComparator<Integer, Integer>());
        Map<Integer, Integer> rendezettKeresek = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : list) {
            rendezettKeresek.put(entry.getKey(), entry.getValue());
        }
        return rendezettKeresek;
    }

    protected List<Map.Entry<Integer, Integer>> mapToList(Map<Integer, Integer> map) {
        return new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
    }

    protected Map<Integer, Integer> listToMap(List<Map.Entry<Integer, Integer>> list) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : list) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    protected static Double keresesiIdoBecslese(Integer szektor1, Integer szektor2) {
        if (szektor1.equals(szektor2)) {
            return Double.valueOf(0);
        }
        return ((double) Math.abs(szektor1 - szektor2) / (double) 4000) + 1;
    }
}
