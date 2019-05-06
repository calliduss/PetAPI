package rest.test;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.config.EncoderConfig.encoderConfig;

public class TestBase {

    static ResponseSpecification responseSpec;
    static final String baseURI = "https://petstore.swagger.io";

    @BeforeClass
    public void setUp() {
        RestAssured.config = new RestAssuredConfig()
                .encoderConfig(encoderConfig()
                        .defaultContentCharset("UTF-8"));
        RestAssured.baseURI = baseURI;
    }

    @BeforeClass
    public void setupResponseSpecification() {
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}