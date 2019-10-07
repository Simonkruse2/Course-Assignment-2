package rest;

import dto.HobbyOutDTO;
import dto.PersonOutDTO;
import dto.ZipCodeOutDTO;
import entities.Hobby;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@OpenAPIDefinition(
        info = @Info(
                title = "Hobby API",
                version = "0.1",
                description = "API to get info about hobbies.",
                contact = @Contact(name = "Gũnther Steiner", email = "info@simonskodebiks.dk")
        ),
        tags = {
            @Tag(name = "hobby", description = "API related to Hobby Info")

        },
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/course-assignment-2"
            ),
            @Server(
                    description = "Server API",
                    url = "https://simonskodebiks.com/course-assignment-2"
            )

        }
)

@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/ca2",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacadeExample(EMF);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Person info by ID",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonOutDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested Person"),
                @ApiResponse(responseCode = "404", description = "Person not found")})

    public PersonOutDTO getPersonInfo(@PathParam("id") int personID) {
        if (personID > 0 && personID == 99999) {
            // for test
            return new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner");
        } else {
            // here should be something real :-)
            return new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner");
        }
    }

//    Get information about a person (address, hobbies etc) given a phone number
    @GET
    @Path("phone/{phoneNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Person info by PhoneNumber",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonOutDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested Person"),
                @ApiResponse(responseCode = "404", description = "Person not found")})

    public PersonOutDTO getPersonInfoByPhoneNumber(@PathParam("phoneNumber") String phoneNumber) {
        if (phoneNumber != null && phoneNumber.equals("1234")) {
            // for test
            return new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner");
        } else {
            // here should be something real :-)
            return new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner");
        }
    }

//    Get all persons with a given hobby
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all Persons info",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonOutDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested Person"),
                @ApiResponse(responseCode = "404", description = "Persons not found")})

    public List<PersonOutDTO> getAllPersonsInfoByHobby() {
        List<PersonOutDTO> p = new ArrayList();
        p.add(new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner"));
        p.add(new PersonOutDTO("kontakt@simonskodebiks.dk", "Osvaldo", "Ardiles"));
        return p;
    }

//    Get all persons living in a given city (i.e. 2800 Lyngby)
    @GET
    @Path("zipcode/{zipcode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Person info by zipcode",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonOutDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested Persons"),
                @ApiResponse(responseCode = "404", description = "Person not found")})
    public List<PersonOutDTO> getAllPersonsInfoByCity(@PathParam("zipCode") String zipCode) {
        if (zipCode != null && zipCode.equals("1234")) {
            // for test
            List<PersonOutDTO> persons = new ArrayList<>();
            PersonOutDTO p = new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner");
            HobbyOutDTO h1 = new HobbyOutDTO("fodbold", "hver tirsdag");
            HobbyOutDTO h2 = new HobbyOutDTO("fodbold", "hver tirsdag");
            ArrayList<HobbyOutDTO> hobbies = new ArrayList<>();
            hobbies.add(h1);
            hobbies.add(h2);
            p.setHobbies(hobbies);
            persons.add(p);
            return persons;
        } else {
            // here should be something real :-)
            List<PersonOutDTO> persons = new ArrayList<>();
            PersonOutDTO p = new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner");
            HobbyOutDTO h1 = new HobbyOutDTO("fodbold", "hver tirsdag");
            HobbyOutDTO h2 = new HobbyOutDTO("fodbold", "hver tirsdag");
            ArrayList<HobbyOutDTO> hobbies = new ArrayList<>();
            hobbies.add(h1);
            hobbies.add(h2);
            p.setHobbies(hobbies);
            persons.add(p);
            return persons;
        }
    }

//    Get the count of people with a given hobby
    @GET
    @Path("count/{hobby}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the count of people with a given hobby",
            tags = {"hobby"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyOutDTO.class))),
                @ApiResponse(responseCode = "200", description = "The count of a given hobby"),
                @ApiResponse(responseCode = "404", description = "Hobby not found")})
    public int getCountPersonByHobby(@PathParam("hobby") String hobby) {
        List<HobbyOutDTO> hobbies = new ArrayList();
        hobbies.add(new HobbyOutDTO("Tennis", "Green ball"));
        return hobbies.size();
    }

//    Get a list of all zip codes in Denmark
    @GET
    @Path("zipcode/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all zipcodes",
            tags = {"zipcode"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ZipCodeOutDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested Persons"),
                @ApiResponse(responseCode = "404", description = "Person not found")})
    public List<ZipCodeOutDTO> getAllZipCodes() {
        //dummy data
        List<ZipCodeOutDTO> zipcodes = new ArrayList<>();
        zipcodes.add(new ZipCodeOutDTO(1234));
        zipcodes.add(new ZipCodeOutDTO(5678));
        return zipcodes;
    }

    //    Get all persons with a given hobby(i.e. golf)
    @GET
    @Path("hobby/{hobby}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Person info by hobby",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonOutDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested Persons"),
                @ApiResponse(responseCode = "404", description = "Person not found")})
    public List<PersonOutDTO> getPersonsInfoByHobby(@PathParam("hobby") String hobby) {
        if (hobby != null && hobby.equals("golf")) {
            // for test
            List<PersonOutDTO> persons = new ArrayList<>();
            persons.add(new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner"));
            return persons;
        } else {
            // here should be something real :-)
            List<PersonOutDTO> persons = new ArrayList<>();
            persons.add(new PersonOutDTO("info@simonskodebiks.dk", "Gũnther", "Steiner"));
            return persons;
        }
    }
}
