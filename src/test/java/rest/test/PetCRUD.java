package rest.test;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.listeners.LogListener;

import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.equalTo;

@Listeners({LogListener.class})
public class PetCRUD {

    private final String baseURI = "https://petstore.swagger.io";
    private final String CONTEXT_PATH = "/v2/pet";

    @BeforeClass
    public void setUp() {
        RestAssured.config = new RestAssuredConfig().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.baseURI = baseURI;
    }

    @Description("The test checks if remote swagger is available")
    @Test
    public void isSwaggerUp() {
        given().when().get(baseURI).then().statusCode(200);
    }

    @Description("The test creates new pet using POST method")
    @Test(dependsOnMethods = {"isSwaggerUp"})
    public void addNewPet() throws Exception {

        List<Map<String, Object>> tags = new ArrayList<>();

        Map<String, Object> petTags = new HashMap<>();
        petTags.put("id", 1);
        petTags.put("name", "tag_1");

        tags.add(petTags);

        HashSet photoUrls = new HashSet();
        photoUrls.add("picture");

        Map<String, Object> category = new HashMap<>();
        category.put("id", 1);
        category.put("name", "Dog");

        Map<String, Object> inputPayload = new HashMap<>();
        inputPayload.put("id", 666);
        inputPayload.put("name", "Cerberus");
        inputPayload.put("status", "available");
        inputPayload.put("category", category);
        inputPayload.put("tags", tags);
        inputPayload.put("photoUrls", photoUrls);

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(inputPayload)
                .when().post(CONTEXT_PATH);

        System.out.print(response.asString());
        response.then()
                .contentType(ContentType.JSON)
                .body("id", equalTo(666))
                .body("name", equalTo("Cerberus"))
                .statusCode(200)
                .extract().response();
    }

    @Description("The test gets an ID of the newly created pet by using GET method")
    @Test(dependsOnMethods = {"addNewPet"})
    public void getPetById() throws Exception {
        given().log().all()
                .when().get(CONTEXT_PATH + "/666")
                .then().statusCode(200)
                .assertThat().body("name", equalTo("Cerberus"));
    }

    @Description("The test updates some info of the newly created pet by using PUT method")
    @Test(dependsOnMethods = {"getPetById"})
    public void updatePet() throws Exception {

        List<Map<String, Object>> tags = new ArrayList<>();

        Map<String, Object> petTags = new HashMap<>();
        petTags.put("id", 1);
        petTags.put("name", "tag_1");

        tags.add(petTags);

        HashSet photoUrls = new HashSet();
        photoUrls.add("picture");

        Map<String, Object> category = new HashMap<>();
        category.put("id", 1);
        category.put("name", "Dog");

        Map<String, Object> inputPayload = new HashMap<>();
        inputPayload.put("id", 777); //updated value
        inputPayload.put("name", "Cerberus");
        inputPayload.put("status", "sold"); //updated value
        inputPayload.put("category", category);
        inputPayload.put("tags", tags);
        inputPayload.put("photoUrls", photoUrls);

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(inputPayload)
                .when().put(CONTEXT_PATH);

        System.out.print(response.asString());
        response.then()
                .body("id", equalTo(777))
                .body("status", equalTo("sold"))
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();
    }

    @Description("The test deletes the newly created pet by using DELETE method")
    @Test(dependsOnMethods = {"updatePet"})
    public void deletePet() throws Exception {
        when()
                .delete(CONTEXT_PATH + "/777")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();
    }
}