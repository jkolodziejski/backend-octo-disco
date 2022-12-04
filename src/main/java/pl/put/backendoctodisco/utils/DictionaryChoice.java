package pl.put.backendoctodisco.utils;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum DictionaryChoice {
    GLOBAL, BOTH, LOCAL;

    public static boolean contains(String choice) {
        for (DictionaryChoice cho : DictionaryChoice.values()) {
            if (cho.name().toLowerCase().equals(choice)) {

                return true;
            }
        }
        return false;
    }
}