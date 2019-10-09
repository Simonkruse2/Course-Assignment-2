package rest;

import dto.PersonDTO;
import dto.PersonOutDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import facades.PersonFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import static org.glassfish.grizzly.http.util.Header.ContentType;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
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

//@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static PersonFacade facade;
    private EntityManager em;
    
    private Person p1, p2;
    private Hobby h1, h2, h3;
    private Address a1, a2;
    private CityInfo c1, c2;
    private Phone phone1, phone2, phone3;

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
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
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
            p1 = new Person("email", "Gurli", "Mogensen", a1);
            p2 = new Person("mail", "Gunnar", "Hjorth", a2);
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
     * Test of getPersonInfo method, of class PersonResource.
     */
    //@Test
    public void testGetPersonInfo() {
        System.out.println("getPersonInfo");
        given()
                .contentType("application/json")
                .get("/person/99999").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("email", equalTo("info@simonskodebiks.dk"))
                .body("firstName", equalTo("Gũnther"))
                .body("lastName", equalTo("Steiner"));
    }

    /**
     * Test of getPersonInfoByPhoneNumber method, of class PersonResource.
     */
    @Test
    public void testGetPersonInfoByPhoneNumber() {
        System.out.println("getPersonInfoByPhoneNumber");
        given()
                .contentType("application/json")
                .get("/person/phone/1111").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("email", equalTo("info@simonskodebiks.dk"))
                .body("firstName", equalTo("Gũnther"))
                .body("lastName", equalTo("Steiner"))
                .body("hobbies.name", hasItems("Cykling","Film"));
    }

    /**
     * Test of getAllPersonsInfoByHobby method, of class PersonResource.
     */
//    @Test
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
    @Test
    public void testGetAllPersonsInfoByCity() {
        System.out.println("getAllPersonsInfoByCity");
        given()
                .contentType("application/json")
                .get("/person/zipcode/1234").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("email", hasItems("info@simonskodebiks.dk"))
                .body("firstName", hasItems("Gũnther"))
                .body("lastName", hasItems("Steiner"));
    }

    /**
     * Test of getCountPersonByHobby method, of class PersonResource.
     */
//    @Test
    public void testGetCountPersonByHobby() {
        System.out.println("getCountPersonByHobby");
        PersonResource instance = new PersonResource();
//        instance.getCountPersonByHobby();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllZipCodes method, of class PersonResource.
     */
    @Test
    public void testGetAllZipCodes() {
        given()
                .contentType("application/json")
                .get("/person/zipcode/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("city", hasItems("ABC", "DEF"))
                .body("zipCode", hasItems(1234, 5678));
    }
    
    /**
     * Test of createPerson method, of class PersonResource.
     */
    @Test
    public void testCreatePerson(){
        
//        List<Map<String, Object>> hobbies = new ArrayList<>();
//        Map<String, Object> hobby1 = new HashMap<>();
//        hobby1.put("name", "football");
//        hobby1.put("description", "Every tuesday");
//        hobbies.add(hobby1);
//        Map<String, Object> hobby2 = new HashMap<>();
//        hobby2.put("name", "programming");
//        hobby2.put("description", "all the time");
//        hobbies.add(hobby2);
//        
//        List<Map<String, Object>> phones = new ArrayList<>();
//        Map<String, Object> phone1 = new HashMap<>();
//        phone1.put("phone", "12345");
//        phone1.put("description", "mobile");
//        phones.add(phone1);
//        Map<String, Object> phone2 = new HashMap<>();
//        phone2.put("phone", "4444");
//        phone2.put("description", "work");
//        phones.add(phone2);
//        
//        Map<String, Object> cityInfo = new HashMap<>();
//        cityInfo.put("zipcode", 1234);
//        cityInfo.put("city", "KBH");
//        
//        Map<String, Object> address = new HashMap<>();
//        address.put("street", "Jacobsvej");
//        address.put("additionalInfo", "Første sal");
//        address.put("street", "Jacobsvej");
//        address.put("cityInfo", cityInfo);
//                
//        personData.put("hobbies", hobbies);
//        personData.put("phones", phones);
//        personData.put("address", address);


        Map<String, Object> personData = new HashMap<>();
        personData.put("personID", 0);
        personData.put("email", "info@simonskodebiks.dk");
        personData.put("fistName", "Gũnther");
        personData.put("lastName", "Steiner");
        personData.put("street", "Jacobsvej");
        personData.put("zipcode", 1234);
        
        String payload = "{\n" +
        "  \"personID\": 0,\n" +
        "  \"email\": \"info@simonskodebiks.dk\",\n" +
        "  \"firstName\": \"Gũnther\"\n" +
        "  \"lastName\": \"Steiner\"\n" +
        "  \"street\": \"Jacobsvej\"\n" +
        "  \"zipcode\": 1234\n" +
        "}";
        
        //Arrange
        PersonDTO expResult = new PersonDTO("info@simonskodebiks.dk", "Gũnther", "Steiner", "Jacobsvej", 1234);
        

        //Act
        PersonDTO result
                = with()
                        .body(expResult) //include object in body
                        .contentType("application/json")
                        .when().request("POST", "/person/create").then() //post REQUEST
                        .assertThat()//.log().body()
                        .statusCode(HttpStatus.OK_200.getStatusCode())
                        .extract()
                        .as(PersonDTO.class); //extract result JSON as object

        //Assert
        MatcherAssert.assertThat((result.getFirstName()), equalTo(expResult.getFirstName()));
        MatcherAssert.assertThat((result.getStreet()), equalTo(expResult.getStreet()));
        
//        given()
//                .contentType("application/json")
//                .body(payload)
//                .post("/person/create").then().log().body()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("email", hasItems("info@simonskodebiks.dk"))
//                .body("firstName", hasItems("Gũnther"))
//                .body("lastName", hasItems("Steiner"));

//    given().urlEncodingEnabled(true)
//            .param("persondID", 0)
//            .param("email", "info@simonskodebiks.dk")
//            .param("firstName", "Gũnther")
//            .param("lastName", "Steiner")
//            .param("street", "Jacobsvej")
//            .param("zipcode", 1234)
//            .contentType("application/json")
//            .post("person/create")
//            .then().statusCode(200);
    
    }

}
