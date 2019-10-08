package facades;

import dto.HobbyOutDTO;
import dto.PersonHobbyOutDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

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

    public Person getPerson(int personID) {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, personID);
            return p;
        } finally {
            em.close();
        }
    }

    // Get information about a person (address, hobbies etc) given a phone number
    public List<PersonHobbyOutDTO> getPersonByPhoneNumber(String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = (TypedQuery<Person>) em.createQuery("SELECT p FROM Person p JOIN p.phones ph WHERE ph.phoneNumber = :phoneNumber");
            query.setParameter("phoneNumber", phoneNumber);
            
            Person person = query.getResultList().get(0);

            TypedQuery<Hobby> queryHobby
                    = (TypedQuery<Hobby>) em.createQuery("SELECT h FROM Hobby h JOIN h.persons p WHERE p.personID = :personID");
            queryHobby.setParameter("personID", person.getPersonID());
            
            TypedQuery<Address> queryAddress
                    = (TypedQuery<Address>) em.createQuery("SELECT a FROM Address a JOIN a.persons p WHERE p.personID = :personID");
            queryHobby.setParameter("personID", person.getPersonID());
            
            String address = "ToDogade 2"; //queryAddress.getResultList().get(0);
            List<Hobby> hob = queryHobby.getResultList();
//            ArrayList<HobbyOutDTO> hobOUT = new ArrayList();
//            for (Hobby hobby : queryHobby.getResultList()) {
//                hobOUT.add(new HobbyOutDTO(hobby));
//            }
            PersonHobbyOutDTO pOUT = new PersonHobbyOutDTO(person.getEmail(), person.getFirstName(), person.getLastName(), address, hob);
            
            List<PersonHobbyOutDTO> results = new ArrayList();
            results.add(pOUT);
            return results;
        } finally {
            em.close();
        }
    }

    // Get all persons with a given hobby
    public void getAllPersonsWithHobby(Hobby hobby) {

    }

    // Get all persons living in a given city (i.e. 2800 Lyngby)
    public List<Person> getAllPersonsWithZipCode(int zipcode) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query
                    = em.createQuery("SELECT p from Person p JOIN p.address a JOIN a.cityInfo c WHERE c.zipCode = :zipCode", Person.class).setParameter("zipCode", zipcode);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Get the count of people with a given hobby
    public int getCountPeopleWithHobby() {
        EntityManager em = emf.createEntityManager();
        try {
            int renameMeCount = (int) em.createQuery("SELECT COUNT(r) FROM Person r where").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }
    }

    // Get a list of all zip codes in Denmark
    public void getAllZipCodes() {

    }

}
