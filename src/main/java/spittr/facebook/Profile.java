package spittr.facebook;

import com.sun.jndi.toolkit.url.Uri;

/**
 * Created by dell on 2017-7-11.
 */
public class Profile {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String name;
    private Uri linkUri;

    public Profile(String id, String firstName, String middleName, String lastName, String name, Uri linkUri) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.name = name;
        this.linkUri = linkUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getLinkUri() {
        return linkUri;
    }

    public void setLinkUri(Uri linkUri) {
        this.linkUri = linkUri;
    }
}
