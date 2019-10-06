package facades;

import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author martin
 */
public class PersonFacadeIT {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private Person p1, p2;
    private Hobby h1, h2, h3;
    private Address a1, a2;
    private CityInfo c1, c2;
    private Phone phone1, phone2, phone3;
    
    public PersonFacadeIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,EMF_Creator.Strategy.DROP_AND_CREATE);
       facade = PersonFacade.getFacadeExample(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            h1 = new Hobby("Cykling", "Cykling på hold");
            h2 = new Hobby("Film", "Gyserfilm");
            h3 = new Hobby("Film", "Dramafilm");
            c1 = new CityInfo(2100, "KBH Ø");
            c2 = new CityInfo(2300, "KBH S");
            a1 = new Address("Testgade", "dejligt sted", c1);
            a2 = new Address("Testvej", "fint sted", c2);
            p1 = new Person("email", "Gurli", "Mogensen", a1);
            p2 = new Person("mail", "Gunnar", "Hjorth", a2);
            phone1 = new Phone(1234, "hjemmetelefon", p1);
            phone2 = new Phone(5678, "mobil", p1);
            phone3 = new Phone(4321, "arbejdstelefon", p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getCompanyInfo method, of class PersonFacade.
     */
//    @org.junit.Test
    public void testGetCompanyInfo() {
        System.out.println("getCompanyInfo");
        PersonFacade instance = null;
        //instance.getCompanyInfo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPersonsWithHobby method, of class PersonFacade.
     */
//    @org.junit.Test
    public void testGetAllPersonsWithHobby() {
        System.out.println("getAllPersons");
        PersonFacade instance = null;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPersonsWithZipCode method, of class PersonFacade.
     */
    @org.junit.Test
    public void testGetAllPersonsWithZipCode() {
        System.out.println("getZipCodes");
        ArrayList<Person> persons = facade.getAllPersonsWithZipCode(2300);
        assertEquals(persons.size(), 1);
    }

    /**
     * Test of getCountPeopleWithHobby method, of class PersonFacade.
     */
//    @org.junit.Test
    public void testGetCountPeopleWithHobby() {
        System.out.println("getCountPeopleWithHobby");
        PersonFacade instance = null;
        int expResult = 0;
        int result = instance.getCountPeopleWithHobby();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllZipCodes method, of class PersonFacade.
     */
//    @org.junit.Test
    public void testGetAllZipCodes() {
        System.out.println("getAllZipCodes");
        PersonFacade instance = null;
        instance.getAllZipCodes();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
