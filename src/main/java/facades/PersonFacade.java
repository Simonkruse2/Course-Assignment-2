package facades;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    
    // Get information about a person (address, hobbies etc) given a phone number
    public void getPerson(){
        
    }
    
    
    // Get all persons with a given hobby
    public void getAllPersonsWithHobby(Hobby hobby){
        
    }
    
    // Get all persons living in a given city (i.e. 2800 Lyngby)
    public ArrayList<Person> getAllPersonsWithZipCode(int zipcode){
        ArrayList<Person> persons = new ArrayList<>();
        return persons;
    }
    
    // Get the count of people with a given hobby
    public int getCountPeopleWithHobby(){
        EntityManager em = emf.createEntityManager();
        try{
            int renameMeCount = (int)em.createQuery("SELECT COUNT(r) FROM Person r where").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
    }
    
    // Get a list of all zip codes in Denmark
    public void getAllZipCodes(){
        
    }

}
