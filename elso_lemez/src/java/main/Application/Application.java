package main.Application;

import org.apache.commons.text.StrSubstitutor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Application {

    static Integer felulet = 10;
    static Integer savPerFelulet = 100000;
    static Integer szektorPerSav = 1000;
    static Integer szektorMeret = 1024;//bájt
    static Integer hezagPerSavSzazalek = 20;
    static Integer lemezForgasiSebesseg = 10000;//percenként
    static Double fejMozgasEgySavra = 0.002;

    public static void main(String[] args) {
        lemezKapacitasSzamolas();
    }

    private static Double fejMozgasNSavra(Integer n) {
        return 1 + fejMozgasEgySavra * n;
    }

    private static Integer lemezMeret() {
        return felulet * savPerFelulet * szektorPerSav * szektorMeret;
    }

    private static void lemezKapacitasSzamolas() {
        Integer bajtPerLemez = lemezMeret();

        System.out.println("Mekkora a lemez kapacitása?");
        String template = "A lemez mérete:${felulet} felület * ${savPerFelulet} sáv egy felületen * ${szektorPerSav} szektor sávonként * ${szektorMeret} bájt szektoronként " +
                "= ${lemezMeret} bájt azaz ${lemezMeretKB} kilobájt, ${lemezMeretMB} megabájt, ${lemezMeretGB} gigabájt, ${lemezMeretTB} terabájt.";

        Map<String, String> data = new HashMap<String, String>();
        data.put("felulet", String.valueOf(felulet));
        data.put("savPerFelulet", String.valueOf(savPerFelulet));
        data.put("szektorPerSav", String.valueOf(szektorPerSav));
        data.put("szektorMeret", String.valueOf(szektorMeret));
        data.put("lemezMeret", String.valueOf(bajtPerLemez));
        data.put("lemezMeretKB", String.valueOf(bajtPerLemez/1024));
        data.put("lemezMeretMB", String.valueOf(bajtPerLemez/1048576));
        data.put("lemezMeretGB", String.valueOf(bajtPerLemez/1073741824));
        data.put("lemezMeretTB", String.valueOf(((bajtPerLemez/1073741824)/1024)));

        System.out.println(StrSubstitutor.replace(template, data));
    }
}
