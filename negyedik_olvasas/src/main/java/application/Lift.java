package application;

import input.Input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Lift extends OlvasoAlgoritmus {

    private Irany irany;

    public Lift(Input adatok) {
        super(adatok);
    }

    public void runLift() {
        while (!adatok.keresek.isEmpty()) {
            Map<Integer, Integer> availableNext = getAvailableNext(adatok.keresek);
            if (irany == null) {
                Map.Entry<Integer, Integer> closestNext = getClosestNext(availableNext);
                iranyBeallitas(closestNext);
                kiiras(closestNext);
            } else {
                if (irany == Irany.BEFELE) {
                    Map<Integer, Integer> lesserAvailable = getLesserAvailable(availableNext);
                    if (!lesserAvailable.isEmpty()) {
                        List<Map.Entry<Integer, Integer>> entries = mapToList(lesserAvailable);
                        Map.Entry<Integer, Integer> next = entries.get(entries.size() - 1);
                        kiiras(next);
                    } else {
                        irany = Irany.KIFELE;
                    }

                } else {
                    Map<Integer, Integer> moreAvailable = getMoreAvailable(availableNext);
                    if (!moreAvailable.isEmpty()) {
                        List<Map.Entry<Integer, Integer>> entries = mapToList(moreAvailable);
                        Map.Entry<Integer, Integer> next = entries.get(0);
                        kiiras(next);
                    } else {
                        irany = Irany.BEFELE;
                    }

                }
            }
        }
    }

    private Map<Integer, Integer> getLesserAvailable(Map<Integer, Integer> keresek) {
        Map<Integer, Integer> lesserNext = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : mapToList(keresek)) {
            if (entry.getKey() <= jelenlegiSav) {
                lesserNext.put(entry.getKey(), entry.getValue());
            }
        }
        return lesserNext;
    }

    private Map<Integer, Integer> getMoreAvailable(Map<Integer, Integer> keresek) {
        Map<Integer, Integer> lesserNext = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : mapToList(keresek)) {
            if (entry.getKey() >= jelenlegiSav) {
                lesserNext.put(entry.getKey(), entry.getValue());
            }
        }
        return lesserNext;
    }

    private void iranyBeallitas(Map.Entry<Integer, Integer> closestNext) {
        if (closestNext.getKey() < jelenlegiSav) {
            irany = Irany.BEFELE;
        } else if (closestNext.getKey() > jelenlegiSav) {
            irany = Irany.KIFELE;
        }
    }

    private Map.Entry<Integer, Integer> getClosestNext(Map<Integer, Integer> availableNext) {
        Map.Entry<Integer, Integer> min = null;
        Double minTav = null;
        for (Map.Entry<Integer, Integer> entry : availableNext.entrySet()) {
            Double keresesiIdoBecslese = keresesiIdoBecslese(jelenlegiSav, entry.getKey());
            if (minTav == null || keresesiIdoBecslese == 0 || keresesiIdoBecslese < minTav) {
                min = entry;
                minTav = keresesiIdoBecslese;
            }
        }
        return min;
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

    private enum Irany {
        KIFELE, BEFELE;
    }
}
