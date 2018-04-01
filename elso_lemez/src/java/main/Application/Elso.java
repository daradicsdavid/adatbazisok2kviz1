package main.Application;

import main.Application.input.Input;
import org.apache.commons.text.StrSubstitutor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Elso {

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
        /*feladatMegoldas(Input.orai);*/
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

        String template = "A maximális keresési idő: 1 + ${fejMozgasEgySavra} (fejmozgás egy sávra) * ${savPerFelulet} (sáv per felület) = ${maximalisKeresesiIdo} ms.";
        Map<String, String> data = new HashMap<String, String>();
        data.put("fejMozgasEgySavra", String.valueOf(fejMozgasEgySavra));
        data.put("savPerFelulet", String.valueOf(savPerFelulet));
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

        String template = "A maximális rotációs késés: 60000 / ${lemezForgasiSebesseg} (RPM) = ${maximalisRotaciosKeses} ms.";
        Map<String, String> data = new HashMap<String, String>();
        data.put("lemezForgasiSebesseg", String.valueOf(lemezForgasiSebesseg));
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
        data.put("teljesFordulatFokban", String.valueOf(teljesFordulatFokban));
        data.put("lemezForgasiSebesseg", String.valueOf(lemezForgasiSebesseg));
        data.put("hezagPerBlokk", String.valueOf(hezagPerBlokk));
        data.put("szektorPerSav", String.valueOf(szektorPerSav));
        data.put("hezagPerSavSzazalek", String.valueOf(hezagPerSavSzazalek));


        String template = "Ha egy blokk ${szektorPerBlokk} szektor méretű, mekkora egy blokk átviteli ideje?";
        System.out.println(StrSubstitutor.replace(template, data));

        template = "Egy blokk átviteli ideje:" +
                "((((((${hezagPerSavSzazalek} (hézag aránya sávban) / 100) * ${teljesFordulatFokban} fok) / ${szektorPerSav} (szektor sávonként)) * ${hezagPerBlokk} (hézag blokkonként)) \n " +
                "+ (((((100 - ${hezagPerSavSzazalek} (hézag aránya sávban))) / 100) * ${teljesFordulatFokban} fok) / ${szektorPerSav} (szektor sávonként) * ${szektorPerBlokk} (szektor blokkonként))) \n" +
                "/ ${teljesFordulatFokban} fok * (60000 / ${lemezForgasiSebesseg} (RPM))" +
                "= ${atviteliIdo} ms.";
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");

        template = "(${hezagPerBlokkFokbanSzamolas} (hezagok foka blokkban) + ${szektorPerBlokkFokbanSzamolas} (szektorok foka blokkban)) / 360 * ${egyFordulatMsban} (teljes fordulat ideje ms-ben) = ${atviteliIdo} (átviteli idő)";
        data.put("hezagPerBlokkFokbanSzamolas", String.valueOf(hezagPerBlokkFokbanSzamolas()));
        data.put("szektorPerBlokkFokbanSzamolas", String.valueOf(szektorPerBlokkFokbanSzamolas()));
        data.put("szektorPerBlokkFokbanSzamolas", String.valueOf(szektorPerBlokkFokbanSzamolas()));
        data.put("egyFordulatMsban", String.valueOf(egyFordulatMsban()));
        data.put("atviteliIdo", String.valueOf(atviteliIdo));
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");

        template = "${hezagPerSzektorFokbanSzamolas} (hézagok foka szektoronként) * ${hezagPerBlokk} (hézagok száma blokkonként) = ${hezagPerBlokkFokbanSzamolas} (hezagok foka blokkban)";
        data.put("hezagPerSzektorFokbanSzamolas", String.valueOf(hezagPerSzektorFokbanSzamolas()));
        data.put("hezagPerBlokk", String.valueOf(hezagPerBlokk));
        data.put("hezagPerBlokkFokbanSzamolas", String.valueOf(hezagPerBlokkFokbanSzamolas()));
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");


        template = "${hezagPerFordulatFokbanSzamolas} (hézagok foka sávonként) / ${szektorPerSav} (szektorok száma sávonként) = ${hezagPerSzektorFokbanSzamolas} (hezagok foka szektorban)";
        data.put("hezagPerFordulatFokbanSzamolas", String.valueOf(hezagPerFordulatFokbanSzamolas()));
        data.put("szektorPerSav", String.valueOf(szektorPerSav));
        data.put("hezagPerSzektorFokbanSzamolas", String.valueOf(hezagPerSzektorFokbanSzamolas()));
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");

        template = "${hezagPerSavSzazalek} (hézagok százaléka sávonként) / 100 * 360  = ${hezagPerFordulatFokbanSzamolas} (hezagok foka sávonként)";
        data.put("hezagPerSavSzazalek", String.valueOf(hezagPerSavSzazalek));
        data.put("hezagPerFordulatFokbanSzamolas", String.valueOf(hezagPerFordulatFokbanSzamolas()));
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");

        template = "${szektorPerSzektorFokbanSzamolas} (szektorok foka szektoronként) * ${szektorPerBlokk} (szektorok száma blokkonként) = ${szektorPerBlokkFokbanSzamolas} (szektorok foka blokkban)";
        data.put("szektorPerSzektorFokbanSzamolas", String.valueOf(szektorPerSzektorFokbanSzamolas()));
        data.put("szektorPerBlokk", String.valueOf(szektorPerBlokk));
        data.put("szektorPerBlokkFokbanSzamolas", String.valueOf(szektorPerBlokkFokbanSzamolas()));
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");

        template = "${szektorPerFordulatFokbanSzamolas} (szektorok foka sávonként) / ${szektorPerBlokk} (szektorok száma sávonként) = ${szektorPerSzektorFokbanSzamolas} (szektorok foka szektorban)";
        data.put("szektorPerFordulatFokbanSzamolas", String.valueOf(szektorPerFordulatFokbanSzamolas()));
        data.put("szektorPerSav", String.valueOf(szektorPerSav));
        data.put("szektorPerSzektorFokbanSzamolas", String.valueOf(szektorPerSzektorFokbanSzamolas()));
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");


        template = "(100 - ${hezagPerSavSzazalek} (hézagok százaléka sávonként)) / 100 * 360= ${szektorPerFordulatFokbanSzamolas} (szektorok foka sávonként)";
        data.put("hezagPerSavSzazalek", String.valueOf(hezagPerSavSzazalek));
        data.put("szektorPerFordulatFokbanSzamolas", String.valueOf(szektorPerFordulatFokbanSzamolas()));
        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");


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

        String template = "Az átlagos keresési idő: 1 + (${savPerFelulet} (sávok száma) / 3) / ${savMozgazPerMs} (fejmozgás fokban ms-ként)  = ${atlagosKeresesiIdo} ms.";
        Map<String, String> data = new HashMap<String, String>();
        data.put("savPerFelulet", String.valueOf(savPerFelulet));
        data.put("savMozgazPerMs", String.valueOf(savMozgazPerMs));
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

        String template = "Az átlagos rotációs késés:  60000 / ${lemezForgasiSebesseg} (RPM) / 2 = ${atlagosRotaciosKeses} ms.";
        Map<String, String> data = new HashMap<String, String>();
        data.put("lemezForgasiSebesseg", String.valueOf(lemezForgasiSebesseg));
        data.put("atlagosRotaciosKeses", String.valueOf(atlagosRotaciosKeses));

        System.out.println(StrSubstitutor.replace(template, data));
        System.out.println("");
    }

    private static Double atlagosRotaciosKesesSzamolas() {
        return maximalisRotaciosKesesSzamolas() / 2;
    }

}
