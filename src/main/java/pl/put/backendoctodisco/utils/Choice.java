package pl.put.backendoctodisco.utils;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum Choice {
    GLOBAL, BOTH, LOCAL;

    public static boolean contains(String choice) {
        for (Choice cho : Choice.values()) {
            if (cho.name().toLowerCase().equals(choice)) {

                return true;
            }
        }
        return false;
    }
}