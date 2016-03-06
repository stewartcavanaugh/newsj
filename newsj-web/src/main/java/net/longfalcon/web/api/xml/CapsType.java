package net.longfalcon.web.api.xml;

import net.longfalcon.web.api.xml.caps.*;

import javax.xml.bind.annotation.*;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "caps")
@XmlType(name = "CapsType",propOrder = {"serverType","limitsType","registrationType","searchingType","categoriesType"})
public class CapsType extends ApiResponse {

    @XmlElement(name = "server")
    protected ServerType serverType;

    @XmlElement(name = "limits")
    protected LimitsType limitsType;

    @XmlElement(name = "registration")
    protected RegistrationType registrationType;

    @XmlElement(name = "searching")
    protected SearchingType searchingType;

    @XmlElement(name = "categories")
    protected CategoriesType categoriesType;

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public LimitsType getLimitsType() {
        return limitsType;
    }

    public void setLimitsType(LimitsType limitsType) {
        this.limitsType = limitsType;
    }

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
    }

    public SearchingType getSearchingType() {
        return searchingType;
    }

    public void setSearchingType(SearchingType searchingType) {
        this.searchingType = searchingType;
    }

    public CategoriesType getCategoriesType() {
        return categoriesType;
    }

    public void setCategoriesType(CategoriesType categoriesType) {
        this.categoriesType = categoriesType;
    }
}
