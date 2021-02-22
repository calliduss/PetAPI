package rest.test;

import com.petstore.controllers.PetController;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.BeforeClass;

import static io.restassured.config.EncoderConfig.encoderConfig;

public class BaseTest {

    static PetController petController;

    @BeforeClass
    public void setUp() {
        RestAssured.config = new RestAssuredConfig()
                .encoderConfig(encoderConfig()
                        .defaultContentCharset("UTF-8"));
        petController = new PetController();
    }
}