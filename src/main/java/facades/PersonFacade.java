package facades;

import dto.PersonHobbyOutDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import jdk.nashorn.internal.runtime.logging.Loggable;
import utils.EMF_Creator;

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
    public PersonHobbyOutDTO getPersonByPhoneNumber(String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        Person person = null;
        try {
            TypedQuery<Person> query = (TypedQuery<Person>) em.createQuery("SELECT p FROM Person p JOIN p.phones ph WHERE ph.phoneNumber = :phoneNumber").setParameter("phoneNumber", phoneNumber);
            if (query.getResultList().size() > 0) {
                person = query.getResultList().get(0);
            } else {
                System.out.println("Couldn't find person");
            }

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

//            List<PersonHobbyOutDTO> results = new ArrayList();
//            results.add(pOUT);
            return pOUT;
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

    // empty production database
    public void getAllZipCodes() {
    }

    public String emptyDB() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return "{\"status\":\"emptied\"}";
    }

    public String fillUp() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        EntityManager em = emf.createEntityManager();
        Person p1, p2;
        Hobby h1, h2, h3;
        Address a1, a2;
        CityInfo c1, c2;
        Phone phone1, phone2, phone3;
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            c1 = new CityInfo(2100, "KBH Ø");
            c2 = new CityInfo(2300, "KBH S");
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            h1 = new Hobby("Cykling", "Cykling på hold");
            h2 = new Hobby("Film", "Gyserfilm");
            h3 = new Hobby("Film", "Dramafilm");
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            a1 = new Address("Testgade", "dejligt sted", c1);
            a2 = new Address("Testvej", "fint sted", c2);
            em.persist(a1);
            em.persist(a2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        em = emf.createEntityManager();
        try {
            p1 = new Person("email@email.com", "Gurli", "Mogensen", a1);
            p2 = new Person("mail@mail.com", "Gunnar", "Hjorth", a2);
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        em = emf.createEntityManager();
        try {
            phone1 = new Phone("1234", "hjemmetelefon", p1);
            phone2 = new Phone("5678", "mobil", p1);
            phone3 = new Phone("4321", "arbejdstelefon", p2);
            em.getTransaction().begin();
            em.persist(phone1);
            em.persist(phone2);
            em.persist(phone3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return "{\"status\":\"filled\"}";
    }
    
    
}
