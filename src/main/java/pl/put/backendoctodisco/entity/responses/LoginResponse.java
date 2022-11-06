package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.utils.AuthToken;

public class LoginResponse {

    @ApiModelProperty(notes = "User authorization token", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXdfdXNlciIsInJvbGVzIjoidXNlciIsImlhdCI6MTY2Nzc1Njg4NiwiZXhwIjoxNjY3NzY3Njg2fQ.nK31xWzMEzhcxGpQj0QQQ6MbyLBxG5fTHIQ4S0nJ7w5_gN-vWaCDMt7RNt0YCI2k-hCGr6DvgIoaRn1kMD1V4Q")
    public String authToken;

    public LoginResponse(String authToken){
        this.authToken = authToken;
    }
}
