package net.longfalcon.web.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: Sten Martinez
 * Date: 3/7/16
 * Time: 10:26 PM
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "register")
public class RegisterType extends ApiResponse {

    @XmlAttribute
    private String username;

    @XmlAttribute
    private String password;

    @XmlAttribute
    private String apikey;

    public RegisterType() {
    }

    public RegisterType(String username, String password, String apikey) {
        this.username = username;
        this.password = password;
        this.apikey = apikey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
