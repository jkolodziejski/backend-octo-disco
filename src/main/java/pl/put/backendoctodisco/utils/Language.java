package pl.put.backendoctodisco.utils;

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
}
