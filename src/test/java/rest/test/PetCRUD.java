package rest.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Response;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PetCRUD {

    private final String baseURI = "https://petstore.swagger.io";
    private final String CONTEXT_PATH = "/v2/pet";

    @Before
    public void setUp() {
        RestAssured.config = new RestAssuredConfig().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.baseURI = baseURI;
    }

    @Attachment
    @Step
    @Test
    public void testA_isSwaggerUp() {
        given().when().get(baseURI).then().statusCode(200);
    }

    @Attachment
    @Step
    @Test
    public void testB_addNewPet() throws Exception {

        List<Map<String, Object>> tags = new ArrayList<>();

        Map<String, Object>  petTags = new HashMap<>();
        petTags.put("id", 1);
        petTags.put("name", "tag_1");

        tags.add(petTags);

        HashSet photoUrls = new HashSet();
        photoUrls.add("picture");

        Map<String, Object>  category = new HashMap<>();
        category.put("id", 1);
        category.put("name", "Dog");

        Map<String, Object>  inputPayload = new HashMap<>();
        inputPayload.put("id", 666);
        inputPayload.put("name", "Cerberus");
        inputPayload.put("status", "available");
        inputPayload.put("category", category);
        inputPayload.put("tags", tags);
        inputPayload.put("photoUrls", photoUrls);

        Response response = given().log().all()
                .contentType("application/json")
                .accept("application/json")
                .body(inputPayload)
                .when().post(CONTEXT_PATH);

        System.out.print(response.asString());
        response.then()
                .contentType("application/json")
                .body("id", equalTo(666))
                .body("name", equalTo("Cerberus"))
                .statusCode(200)

                .extract().response();
    }

    @Attachment
    @Step
    @Test
    public void testC_getPetById() throws Exception {
        given().log().all()
                .when().get(CONTEXT_PATH + "/666")
                .then().statusCode(200)
                .assertThat().body("name", equalTo("Cerberus"));
    }

    @Attachment
    @Step
    @Test
    public void testD_updatePet() throws Exception {

        List<Map<String, Object>> tags = new ArrayList<>();

        Map<String, Object>  petTags = new HashMap<>();
        petTags.put("id", 1);
        petTags.put("name", "tag_1");

        tags.add(petTags);

        HashSet photoUrls = new HashSet();
        photoUrls.add("picture");

        Map<String, Object>  category = new HashMap<>();
        category.put("id", 1);
        category.put("name", "Dog");

        Map<String, Object>  inputPayload = new HashMap<>();
        inputPayload.put("id", 777); //updated value
        inputPayload.put("name", "Cerberus");
        inputPayload.put("status", "sold"); //updated value
        inputPayload.put("category", category);
        inputPayload.put("tags", tags);
        inputPayload.put("photoUrls", photoUrls);

        Response response = given().log().all()
                .contentType("application/json")
                .accept("application/json")
                .body(inputPayload)
                .when().put(CONTEXT_PATH);

        System.out.print(response.asString());
        response.then()
                .body("id", equalTo(777))
                .body("status", equalTo("sold"))
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
    }

    @Attachment
    @Step
    @Test
    public void testE_deletePet() throws Exception {
        when()
                .delete(CONTEXT_PATH + "/777")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
    }
}
