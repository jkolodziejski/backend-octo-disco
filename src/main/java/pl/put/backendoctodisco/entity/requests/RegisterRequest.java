package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

public class RegisterRequest {
    @ApiModelProperty(notes = "Users login", example = "sample", required = true)
    @Column(name = "login", nullable = false)
    public String login;

    @ApiModelProperty(notes = "Users email", example = "sample.ofall@gmail.com", required = true)
    @Column(name = "email", nullable = false)
    public String email;

    @ApiModelProperty(notes = "Users password", example = "pass", required = true)
    @Column(name = "password", nullable = false)
    public String password;
}
