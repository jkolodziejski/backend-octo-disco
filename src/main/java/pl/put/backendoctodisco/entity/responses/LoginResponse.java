package pl.put.backendoctodisco.entity.responses;

import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.utils.AuthToken;

public class LoginResponse {
    public String authToken;

    public LoginResponse(String authToken){
        this.authToken = authToken;
    }
}
