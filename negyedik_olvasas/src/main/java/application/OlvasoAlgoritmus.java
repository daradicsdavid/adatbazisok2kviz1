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
        jelenlegiSav = next.getKey();
        Double ujElteltIdo = elteltIdo + keresesiIdo + adatok.blokkBeolvasas;

        String template = "Eddig eltelt idő:${elteltIdo}, jelenlegi blokk: ${jelenlegiSav} kovektkező blokk:${sorszam} , " +
                "teljesítés ideje: ${elteltIdo} (eltelt idő) + ${keresesiIdo} (keresési idő) + ${bblokkBeolvasas} (blokk beolvasás) = ${ido}";
        Map<String, String> data = new HashMap<String, String>();
        data.put("elteltIdo", String.valueOf(elteltIdo));
        data.put("jelenlegiSav", String.valueOf(jelenlegiSav));
        data.put("sorszam", String.valueOf(next.getKey()));
        data.put("keresesiIdo", String.valueOf(keresesiIdo));
        data.put("bblokkBeolvasas", String.valueOf(adatok.blokkBeolvasas));
        data.put("ido", String.valueOf(ujElteltIdo));
        System.out.println(StrSubstitutor.replace(template, data));

        elteltIdo = ujElteltIdo;
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
