package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;


public class LoginResponse {

    @ApiModelProperty(notes = "User authorization token", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXdfdXNlciIsInJvbGVzIjoidXNlciIsImlhdCI6MTY2Nzc1Njg4NiwiZXhwIjoxNjY3NzY3Njg2fQ.nK31xWzMEzhcxGpQj0QQQ6MbyLBxG5fTHIQ4S0nJ7w5_gN-vWaCDMt7RNt0YCI2k-hCGr6DvgIoaRn1kMD1V4Q")
    public String authToken;

    @ApiModelProperty(notes = "Permissions of the user", example = "user", allowableValues = "[user, admin]")
    public String permissions;

    public LoginResponse(String permissions, String authToken) {
        this.authToken = authToken;
        this.permissions = permissions;
    }
}
