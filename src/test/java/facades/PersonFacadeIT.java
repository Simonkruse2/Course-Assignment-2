package facades;

import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
            em.persist(new Person("Some txt", "More text","ccc"));
            em.persist(new Person("aaa", "bbb","ccc"));

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
        instance.getCompanyInfo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPersons method, of class PersonFacade.
     */
//    @org.junit.Test
    public void testGetAllPersons() {
        System.out.println("getAllPersons");
        PersonFacade instance = null;
        instance.getAllPersons();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getZipCodes method, of class PersonFacade.
     */
//    @org.junit.Test
    public void testGetZipCodes() {
        System.out.println("getZipCodes");
        PersonFacade instance = null;
        instance.getZipCodes();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
