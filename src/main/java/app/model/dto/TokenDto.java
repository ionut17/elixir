package app.model.dto;

import app.model.common.Item;
import app.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TokenDto implements Item {

    private String token;

    private UserDto user;

    //Constructors

    public TokenDto() {
    }

    //Setters and getters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
