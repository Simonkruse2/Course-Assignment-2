package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author frede
 */
@Entity
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer phoneID;
    private int number;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "personID")
    private Person person;

    public Phone() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        if (!person.getPhones().contains(this)) {
            person.addPhone(this);
        }
    }

    public Phone(int number, String description, Person person) {
        this.number = number;
        this.description = description;
        this.person = person;
        person.addPhone(this);
    }

    public Integer getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(Integer phoneID) {
        this.phoneID = phoneID;
    }

    @Override
    public String toString() {
        return "Phone{" + "phoneID=" + phoneID + ", number=" + number + ", description=" + description + ", person=" + person + '}';
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.phoneID);
        hash = 37 * hash + this.number;
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.person);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Phone other = (Phone) obj;
        if (this.number != other.number) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.phoneID, other.phoneID)) {
            return false;
        }
        if (!Objects.equals(this.person, other.person)) {
            return false;
        }
        return true;
    }
    
    

}
