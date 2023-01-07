package pl.put.backendoctodisco.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.put.backendoctodisco.entity.requests.RegisterRequest;
import pl.put.backendoctodisco.exceptions.PropertiesNotAvailableException;
import pl.put.backendoctodisco.exceptions.SomethingWentWrongException;
import pl.put.backendoctodisco.utils.Hash256;
import pl.put.backendoctodisco.utils.PropertiesReader;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "User ID", example = "1")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "User login", example = "sample", required = true)
    @Column(name = "login", nullable = false)
    private String login;

    @ApiModelProperty(notes = "User email", example = "sample.ofall@gmail.com", required = true)
    @Column(name = "email", nullable = false)
    private String email;

    @ApiModelProperty(notes = "User password", example = "pass", required = true)
    @Column(name = "password", nullable = false)
    private String password;

    @ApiModelProperty(notes = "Permissions of the user", example = "user", allowableValues = "[user, admin]")
    @Column(name = "permissions")
    private String permissions;

    @ApiModelProperty(notes = "Authorization token", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXdfdXNlciIsInJvbGVzIjoidXNlciIsImlhdCI6MTY2Nzc1Njg4NiwiZXhwIjoxNjY3NzY3Njg2fQ.nK31xWzMEzhcxGpQj0QQQ6MbyLBxG5fTHIQ4S0nJ7w5_gN-vWaCDMt7RNt0YCI2k-hCGr6DvgIoaRn1kMD1V4Q")
    @Column(name = "auth_token")
    private String authToken;

    @ApiModelProperty(notes = "Exp points for english tests", example = "1250", required = true)
    @Column(name = "exp")
    private Integer exp;

    public User(RegisterRequest registerRequest) throws PropertiesNotAvailableException, SomethingWentWrongException {
        this.login = registerRequest.login;
        this.email = registerRequest.email;
        final boolean passwordHash = PropertiesReader.read("password.hash").equals("true");
        if(passwordHash){
            this.password = Hash256.password(registerRequest.password);
        }else {
            this.password = registerRequest.password;
        }
        this.permissions="user";
        this.exp=0;
    }
}
