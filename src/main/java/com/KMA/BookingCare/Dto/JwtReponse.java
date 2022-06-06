package com.KMA.BookingCare.Dto;

import java.util.List;

public class JwtReponse {

    private String token;

    private String type = "Bearer";

    private Long id;

    private String usernamee;

    private String password;

    private List<String> roles;


    public JwtReponse() {
    }

    public JwtReponse(String token, String type, Long id, String usernamee, String password, List<String> roles) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.usernamee = usernamee;
        this.password = password;
        this.roles = roles;
    }

    public JwtReponse(String token, Long id, String usernamee, List<String> roles) {
        this.token = token;
        this.id = id;
        this.usernamee = usernamee;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsernamee() {
        return usernamee;
    }

    public void setUsernamee(String usernamee) {
        this.usernamee = usernamee;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
