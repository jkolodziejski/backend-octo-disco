package pl.put.backendoctodisco.utils;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum Choice {
    global, both, local;

    public static boolean contains(String choice){
        for (Choice cho : Choice.values()) {
            if (cho.name().toLowerCase().equals(choice)) {

                return true;
            }
        }
        return false;
    }
}