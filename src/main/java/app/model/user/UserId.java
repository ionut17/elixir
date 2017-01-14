package app.model.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Ionut on 14-Jan-17.
 */
@Embeddable
public class UserId implements Serializable {

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    //Constructors

    public UserId(){}

    public UserId(Long id, String type){
        this.setId(id);
        this.setType(type);
    }

    //Setters and getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}