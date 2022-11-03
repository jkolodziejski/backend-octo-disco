package pl.put.backendoctodisco.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

    @ApiModelProperty(notes = "Authorization token", example = "asdfasdfasd")
    @Column(name = "auth_token")
    private String authToken;
}
