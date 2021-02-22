package com.petstore.controllers;

import com.petstore.models.Category;
import com.petstore.models.Pet;
import com.petstore.models.Status;
import com.petstore.models.Tag;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Collections;
import java.util.List;

import static com.petstore.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;

public class PetController {

    private final RequestSpecification requestSpecification;
    private final ResponseSpecification responseSpecification;

    public PetController() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(BASE_URL);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(anyOf(is(both(greaterThanOrEqualTo(200)).and(lessThan(300)))));
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecBuilder.log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    public Pet createPet(String name) {
        int min = 1;
        int max = 10000;

        long randomId = (long) (Math.random() * (max - min + 1) + min);

        Pet pet = new Pet.Builder()
                .withId(randomId)
                .withName(name)
                .withPhotoUrls(Collections.singletonList(PHOTO_URL))
                .withStatus(Status.available)
                .withTags(Collections.singletonList(new Tag(1, "German shepherd dog")))
                .inCategory(new Category(1, "Dogs")).build();
        return pet;
    }

    public Pet addNewPet(Pet pet) {
        Response response = given(requestSpecification)
                .body(pet)
                .post(PET_ENDPOINT);

        // ensure response is valid
        response.then().spec(responseSpecification);

        return response.as(Pet.class);
    }

    public List<Pet> findPetsByStatus() {
        Response response =  given(requestSpecification)
                .queryParam("status", Status.available.toString())
                .get(FIND_BY_STATUS_ENDPOINT);

        response.then().spec(responseSpecification);

        return response.then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);
    }

    public void deletePet(Pet pet) {
        Response response = given(requestSpecification).delete(PET_ENDPOINT + "/" + pet.getId());
        response.then().spec(responseSpecification);
    }

    public void verifyPetDeleted(Pet pet) {
        Response response = given(requestSpecification).get(PET_ENDPOINT + "/" + pet.getId());
        response.then().statusCode(404).body(containsString("Pet not found"));
    }

    public Pet findPet(Pet pet) {
        Response response = given(requestSpecification)
                .get(PET_ENDPOINT + "/" + pet.getId());

        response.then().spec(responseSpecification);

        return response.as(Pet.class);
    }

    public Pet updatePet(Pet pet) {
        Response response =  given(requestSpecification)
                .body(pet)
                .put(PET_ENDPOINT);

        response.then().spec(responseSpecification);

        return response.as(Pet.class);
    }
}
