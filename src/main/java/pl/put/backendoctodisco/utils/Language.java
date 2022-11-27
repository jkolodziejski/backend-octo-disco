package pl.put.backendoctodisco.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Language {
    EN;

    public static boolean contains(String language) {
        for (Language lang : Language.values()) {
            if (lang.name().toLowerCase().equals(language)) {
                return true;
            }
        }
        return false;
    }

    public static String allToString(){
        return Arrays.stream(Language.values()).map(lang -> {
            return lang.name().toLowerCase();
        }).collect(Collectors.joining(", "));
    }
}
