/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author frede
 */
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String street;
    private String addiontionalInfo;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cityInfo_id")
    private CityInfo cityInfo;

    public Address(String street, String addiontionalInfo, CityInfo cityInfo) {
        this.street = street;
        this.addiontionalInfo = addiontionalInfo;
        this.cityInfo = cityInfo;
        cityInfo.addAddress(this);
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
        if(!cityInfo.getAddresses().contains(this))
            cityInfo.addAddress(this);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddiontionalInfo() {
        return addiontionalInfo;
    }

    public void setAddiontionalInfo(String addiontionalInfo) {
        this.addiontionalInfo = addiontionalInfo;
    }    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
}
