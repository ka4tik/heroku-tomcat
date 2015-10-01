package messenger.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class User {

    private String username;
    private String firstName;
    private String lastName;
    private Date created;

    public User() {
        //no-op
    }

    public User( String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.created = new Date();
    }
    public User( String username, String firstName, String lastName,Date created) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.created =  created;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
