package application;

import input.Input;
import org.apache.commons.text.StrSubstitutor;
import util.ValueThenKeyComparator;

import java.util.*;

public class Fifo extends OlvasoAlgoritmus {


    public Fifo(Input adatok) {
        super(adatok);
    }

    public void runFifo() {
        while (!adatok.keresek.isEmpty()) {
            Map.Entry<Integer, Integer> next = getNext(adatok.keresek);

            kiiras(next);
        }
    }


    private Map.Entry<Integer, Integer> getNext(Map<Integer, Integer> keresek) {
        return mapToList(keresekRendezese(getAvailableNext(adatok.keresek))).get(0);
    }

    private Map<Integer, Integer> getAvailableNext(Map<Integer, Integer> keresek) {
        Map<Integer, Integer> availableNexts = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : mapToList(keresek)) {
            if (entry.getValue() <= elteltIdo) {
                availableNexts.put(entry.getKey(), entry.getValue());
            }
        }
        return availableNexts;
    }

}
