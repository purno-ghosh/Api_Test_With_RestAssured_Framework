import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TestCase {

    Properties prop = new Properties();
    FileInputStream file;

    {
        try {
            file = new FileInputStream("./src/test/resources/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String token;

    public void callingLoginAPI() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"email\": \"eve.holt@reqres.in\",\n" +
                                "    \"password\": \"cityslicka\"\n" +
                                "}").
                        when()
                        .post("/login").
                        then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonpath = res.jsonPath();
        token = jsonpath.get("token");
        Utils.setEnvVariable("token", token);
        System.out.println(res.asString());
    }

    public void AlluserList() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization", prop.getProperty("token")).
                        when()
                        .get("/users?page=2").
                        then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("data[0].id").toString(), "7");
        System.out.println(res.asString());
    }
    public void CheckOneUser() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization", prop.getProperty("token")).
                        when()
                        .get("/users/2").
                        then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonPath = res.jsonPath();
        Assert.assertEquals(jsonPath.get("data.first_name").toString(), "Janet");
        System.out.println(res.asString());

    }
    public void createNewUser() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json").header("Authorization", prop.getProperty("token"))
                        .body("{\n" +
                                "    \"name\": \"purno\",\n" +
                                "    \"job\": \"SQA\"\n" +
                                "}")
                        .when()
                        .post("/users").
                        then()
                        .assertThat().statusCode(201).extract().response();


        System.out.println(res.asString());
    }
    public void updateUSER() throws IOException {

        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization", prop.getProperty("token"))
                        .body("{\n" +
                                "    \"name\": \"Anik ghosh\",\n" +
                                "    \"job\": \"Sr.QA\"\n" +
                                "}")
                        .when()

                        .put("/users/2").
                        then()
                        .assertThat().statusCode(200).extract().response();
        JsonPath jsonPath = res.jsonPath();
        Assert.assertEquals(jsonPath.get("name").toString(), "Anik ghosh");
        System.out.println(res.asString());
    }

    public void deleteCustomer() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization", prop.getProperty("token")).
                        when()
                        .delete("/users/2").
                        then()
                        .assertThat().statusCode(204).extract().response();

        System.out.println(res.asString());
    }

}
