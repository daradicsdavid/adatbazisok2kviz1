package main.Application;

import main.Application.input.Input;
import org.apache.commons.text.StrSubstitutor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Application {

    static Integer felulet;
    static Integer savPerFelulet;
    static Integer szektorPerSav;
    static Integer szektorMeret;//bájt
    static Integer hezagPerSavSzazalek;
    static Integer lemezForgasiSebesseg;//percenként
    static Double fejMozgasEgySavra; //ms
    static Integer szektorPerBlokk;
    static Integer hezagPerBlokk;

    static Double savMozgazPerMs;
    static Integer egyMasodpercMsben = 1000;
    static Integer egyPercMasodpercben = 60;
    static Integer teljesFordulatFokban = 360;

    public static void main(String[] args) {
        feladatMegoldas(Input.orai);
        feladatMegoldas(Input.feladatsor);
    }

    private static void feladatMegoldas(Input input) {
        System.out.println("Feladatmegoldás:");
        fillInput(input);

        lemezKapacitasSzamolas();

        maximalisKeresesiIdo();

        maximalisRotaciosKeses();

        atviteliIdo();

        atlagosKeresesiIdo();

        atlagosRotaciosKeses();

        System.out.println("");
    }

    private static void fillInput(Input input) {
        felulet = input.felulet;
        savPerFelulet = input.savPerFelulet;
        szektorPerSav = input.szektorPerSav;
        szektorMeret = input.szektorMeret;//bájt
        hezagPerSavSzazalek = input.hezagPerSavSzazalek;
        lemezForgasiSebesseg = input.lemezForgasiSebesseg;//percenként
        fejMozgasEgySavra = input.fejMozgasEgySavra; //ms
        szektorPerBlokk = input.szektorPerBlokk;
        hezagPerBlokk = szektorPerBlokk - 1;
        savMozgazPerMs = 1 / fejMozgasEgySavra;
    }


    private static Double fejMozgasNSavra(Integer n) {
        return 1 + fejMozgasEgySavra * n;
    }

    private static BigInteger lemezMeret() {
        return BigInteger.valueOf(felulet).multiply(BigInteger.valueOf(savPerFelulet)).multiply(BigInteger.valueOf(szektorPerSav)).multiply(BigInteger.valueOf(szektorMeret));
    }

    private static void lemezKapacitasSzamolas() {
        BigInteger bajtPerLemez = lemezMeret();

        System.out.println("Mekkora a lemez kapacitása?");
        String template = "A lemez mérete:${felulet} felület * ${savPerFelulet} sáv egy felületen * ${szektorPerSav} szektor sávonként * ${szektorMeret} bájt szektoronként " +
                "= ${lemezMeret} bájt azaz ${lemezMeretKB} kilobájt, ${lemezMeretMB} megabájt, ${lemezMeretGB} gigabájt, ${lemezMeretTB} terabájt.";

        Map<String, String> data = new HashMap<String, String>();
        data.put("felulet", String.valueOf(felulet));
        data.put("savPerFelulet", String.valueOf(savPerFelulet));
        data.put("szektorPerSav", String.valueOf(szektorPerSav));
        data.put("szektorMeret", String.valueOf(szektorMeret));
        data.put("lemezMeret", String.valueOf(bajtPerLemez));
        data.put("lemezMeretKB", String.valueOf(bajtPerLemez.divide(BigInteger.valueOf(1024))));
        data.put("lemezMeretMB", String.valueOf(bajtPerLemez.divide(BigInteger.valueOf(1048576))));
        data.put("lemezMeretGB", String.valueOf(bajtPerLemez.divide(BigInteger.valueOf(1073741824))));
        data.put("lemezMeretTB", String.valueOf(((bajtPerLemez.divide(BigInteger.valueOf(1073741824)).divide(BigInteger.valueOf(1024))))));

        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");
    }

    private static void maximalisKeresesiIdo() {
        Double maximalisKeresesiIdo = maximalisKeresesiIdoSzamolas();
        System.out.println("Mekkora a maximális keresési idő?");

        String template = "A maximális keresési idő: ${maximalisKeresesiIdo} ms.";
        Map<String, String> data = new HashMap<String, String>();
        data.put("maximalisKeresesiIdo", String.valueOf(maximalisKeresesiIdo));

        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");
    }

    private static Double maximalisKeresesiIdoSzamolas() {
        return fejMozgasNSavra(savPerFelulet);
    }

    private static void maximalisRotaciosKeses() {
        Double maximalisRotaciosKeses = maximalisRotaciosKesesSzamolas();
        System.out.println("Mekkora a maximális rotációs késés?");

        String template = "A maximális rotációs késés: ${maximalisRotaciosKeses} ms.";
        Map<String, String> data = new HashMap<String, String>();
        data.put("maximalisRotaciosKeses", String.valueOf(maximalisRotaciosKeses));

        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");
    }

    private static Double maximalisRotaciosKesesSzamolas() {
        return egyFordulatMsban();
    }

    private static Double egyFordulatMsban() {
        return (double) (egyPercMasodpercben * egyMasodpercMsben) / (double) lemezForgasiSebesseg;
    }


    private static void atviteliIdo() {
        Double atviteliIdo = atviteliIdoSzamolas();
        Map<String, String> data = new HashMap<String, String>();
        data.put("atviteliIdo", String.valueOf(atviteliIdo));
        data.put("szektorPerBlokk", String.valueOf(szektorPerBlokk));

        String template = "Ha egy blokk ${szektorPerBlokk} szektor méretű, mekkora egy blokk átviteli ideje?";
        System.out.println(StrSubstitutor.replace(template, data));

        template = "Egy blokk átviteli ideje: ${atviteliIdo} ms.";
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");
    }

    private static Double maximalisAtviteliIdoSzamolas() {
        return maximalisKeresesiIdoSzamolas() + maximalisRotaciosKesesSzamolas() + atviteliIdoSzamolas();
    }

    private static Double atviteliIdoSzamolas() {
        return (hezagPerBlokkFokbanSzamolas() + szektorPerBlokkFokbanSzamolas()) / teljesFordulatFokban * egyFordulatMsban();
    }

    private static Double szektorPerBlokkFokbanSzamolas() {
        return szektorPerSzektorFokbanSzamolas() * szektorPerBlokk;
    }

    private static Double szektorPerSzektorFokbanSzamolas() {
        return szektorPerFordulatFokbanSzamolas() / szektorPerSav;
    }

    private static Double szektorPerFordulatFokbanSzamolas() {
        return (((double) (100 - hezagPerSavSzazalek)) / 100) * teljesFordulatFokban;
    }

    private static Double hezagPerBlokkFokbanSzamolas() {
        return hezagPerSzektorFokbanSzamolas() * hezagPerBlokk;
    }

    private static Double hezagPerSzektorFokbanSzamolas() {
        return hezagPerFordulatFokbanSzamolas() / szektorPerSav;
    }

    private static Double hezagPerFordulatFokbanSzamolas() {
        return ((hezagPerSavSzazalek.doubleValue() / 100) * teljesFordulatFokban);
    }

    private static void atlagosKeresesiIdo() {
        Double atlagosKeresesiIdo = atlagosKeresesiIdoSzamolas();
        System.out.println("Mekkora az átlagos keresési idő?");

        String template = "Az átlagos keresési idő: ${atlagosKeresesiIdo} ms.";
        Map<String, String> data = new HashMap<String, String>();
        data.put("atlagosKeresesiIdo", String.valueOf(atlagosKeresesiIdo));

        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");

    }

    private static Double atlagosKeresesiIdoSzamolas() {
        return 1 + (((double) savPerFelulet / 3) / savMozgazPerMs);
    }

    private static void atlagosRotaciosKeses() {
        Double atlagosRotaciosKeses = atlagosRotaciosKesesSzamolas();
        System.out.println("Mekkora az átlagos rotációs késés?");

        String template = "Az átlagos rotációs késés: ${atlagosRotaciosKeses} ms.";
        Map<String, String> data = new HashMap<String, String>();
        data.put("atlagosRotaciosKeses", String.valueOf(atlagosRotaciosKeses));

        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");
    }

    private static Double atlagosRotaciosKesesSzamolas() {
        return maximalisRotaciosKesesSzamolas() / 2;
    }

}
