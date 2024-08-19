package br.com.lmelgarejo;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Classe de testes inicial")
public class OlaMundoTest {

    @Test
    @DisplayName("Olá mundo teste")
    public void testOlaMundo(){
        Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
        Assertions.assertEquals("Ola Mundo!", response.getBody().asString());
        Assertions.assertEquals(200, response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    @DisplayName("Deve conhecer outras formas de usar o Rest-Assured")
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

    @Test
    @DisplayName("Devo conhecer o Matchers.Hamcrest")
    public void devoConhecerMatchersHamcrest(){
        assertThat("Maria", is("Maria"));
        assertThat(128, is(128));
        assertThat(128, isA(Integer.class));
        assertThat(128d, isA(Double.class));
        assertThat(128d, greaterThan(120d));
        assertThat(128d, lessThan(130d));

        List<Integer> impares = Arrays.asList(1,3,5,7,9);
        assertThat(impares, hasSize(5));
        assertThat(impares, contains(1,3,5,7,9));
        assertThat(impares, containsInAnyOrder(1,3,5,9,7));
        assertThat(impares, hasItem(1));
        assertThat(impares, hasItems(1,5));

        assertThat("Maria", is(not("João")));
        assertThat("Maria", not("João"));
        assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
        assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
    }

    @Test
    @DisplayName("Devo validar o body da resposta")
    public void devoValidarBody(){
        given()
        .when()
            .get("http://restapi.wcaquino.me/ola")
        .then()
            .statusCode(200)
            .body(is("Ola Mundo!"))
            .body(containsString("Mundo"))
            .body(is(not(nullValue())))
        ;
    }
}
