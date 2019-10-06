package rest;

import dto.PersonOutDTO;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

@Disabled
public class PersonResourceIT {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person r1,r2;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
        //System.in.read();
         //Don't forget this, if you called its counterpart in @BeforeAll
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
//        r1 = new Person("Some txt","More text");
//        r2 = new Person("aaa","bbb");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(r1);
            em.persist(r2); 
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
   
    @Test
    public void testDummyMsg() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("msg", equalTo("Hello World"));   
    }

    /**
     * Test of demo method, of class PersonResource.
     */
    @org.junit.Test
    public void testDemo() {
        System.out.println("demo");
        PersonResource instance = new PersonResource();
        String expResult = "";
        String result = instance.demo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonInfo method, of class PersonResource.
     */
//    @org.junit.Test
    public void testGetPersonInfo() {
        System.out.println("getPersonInfo");
        int personID = 0;
        PersonResource instance = new PersonResource();
        PersonOutDTO expResult = null;
        PersonOutDTO result = instance.getPersonInfo(personID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPersonsInfoByHobby method, of class PersonResource.
     */
//    @org.junit.Test
    public void testGetAllPersonsInfoByHobby() {
        System.out.println("getAllPersonsInfoByHobby");
        PersonResource instance = new PersonResource();
        List<PersonOutDTO> expResult = null;
        List<PersonOutDTO> result = instance.getAllPersonsInfoByHobby();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPersonsInfoByCity method, of class PersonResource.
     */
//    @org.junit.Test
    public void testGetAllPersonsInfoByCity() {
        System.out.println("getAllPersonsInfoByCity");
        PersonResource instance = new PersonResource();
        List<PersonOutDTO> expResult = null;
        List<PersonOutDTO> result = instance.getAllPersonsInfoByCity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCountPersonByHobby method, of class PersonResource.
     */
//    @org.junit.Test
    public void testGetCountPersonByHobby() {
        System.out.println("getCountPersonByHobby");
        PersonResource instance = new PersonResource();
        instance.getCountPersonByHobby();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllZipCodes method, of class PersonResource.
     */
//    @org.junit.Test
    public void testGetAllZipCodes() {
        System.out.println("getAllZipCodes");
        PersonResource instance = new PersonResource();
        instance.getAllZipCodes();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
}
