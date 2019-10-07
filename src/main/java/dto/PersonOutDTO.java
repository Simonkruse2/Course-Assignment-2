package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;

@Schema(name = "PersonInfo")
public class PersonOutDTO {

    private int personID;
    
    @Schema(required = true, example = "info@simonskodebiks.dk")
    private String email;
    
    @Schema(required = true, example = "Gũnther")
    private String firstName;
    
    @Schema(required = true, example = "Steiner")
    private String lastName;
    
    @Schema(required = true)
    private ArrayList<HobbyOutDTO> hobbies;

    public ArrayList<HobbyOutDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<HobbyOutDTO> hobbies) {
        this.hobbies = hobbies;
    }
    
    public PersonOutDTO(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonOutDTO() {
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

   

}
