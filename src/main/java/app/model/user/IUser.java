package app.model.user;

import app.model.common.Item;

/**
 * Created by Ionut on 19-Dec-16.
 */
public interface IUser extends Item {

    Long getId();

    String getPassword();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getEmail();

    void setEmail(String email);

}
