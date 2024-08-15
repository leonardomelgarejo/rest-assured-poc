package br.com.lmelgarejo;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class OlaMundoTest {

    @Test
    public void testOlaMundo(){
        Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
        Assertions.assertEquals("Ola Mundo!", response.getBody().asString());
        Assertions.assertEquals(200, response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void devoConhecerOutrasFormasRestAssured(){
        Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);

       get("http://restapi.wcaquino.me/ola").then().statusCode(200);

       given()
       .when()
           .get("http://restapi.wcaquino.me/ola")
       .then()
           .assertThat()
               .statusCode(200);
    }
}
