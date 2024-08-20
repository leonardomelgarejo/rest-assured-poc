package br.com.lmelgarejo;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Testar Json da API")
public class UserJsonTest {

    @Test
    @Description("Deve verificar primeiro nivel do json")
    public void deveVerificarPrimeiroNivel(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/users/1")
        .then()
            .statusCode(200)
            .body("id", is(1))
            .body("name", containsString("Silvf"))
            .body("age", greaterThan(18))
        ;
    }

    @Test
    @Description("Deve verificar primeiro nivel do json de outras formas")
    public void deveVerificarPrimeiroNivelOutrasFormas(){
        Response response = request(Method.GET, "https://restapi.wcaquino.me/users/1");

        assertEquals(Integer.valueOf(1), response.path("id"));
        assertEquals(Integer.valueOf(1), response.path("%s","id"));

        JsonPath jpath = new JsonPath(response.asString());
        assertEquals(1, jpath.getInt("id"));

        int id = JsonPath.from(response.asString()).getInt("id");
        assertEquals(1, id);
    }

    @Test
    @Description("Deve verificar segundo nivel do json")
    public void deveVerificarSegundoNivel(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/users/2")
        .then()
            .statusCode(200)
            .body("name", containsString("Joaquina"))
            .body("endereco.rua", is("Rua dos bobos"))
        ;
    }

    @Test
    @Description("Deve verificar lista no json")
    public void deveVerificarLista(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/users/3")
        .then()
            .statusCode(200)
            .body("filhos", hasSize(2))
            .body("filhos[0].name", is("Zezinho"))
            .body("filhos[1].name", is("Luizinho"))
            .body("filhos.name", hasItem("Zezinho"))
            .body("filhos.name", hasItems("Zezinho","Luizinho"))
        ;
    }

    @Test
    @Description("Deve retornar erro de usuário inexistente")
    public void deveRetornarErroUsuarioInexistente(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/users/4")
        .then()
            .statusCode(404)
            .body("error", is("Usuário inexistente"))
        ;
    }

    @Test
    @Description("Deve verificar lista na raiz do json")
    public void deveVerificarListaRaiz(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/users")
        .then()
            .statusCode(200)
            .body("$", hasSize(3))
            .body("name", hasItems("João da Silva","Maria Joaquina","Ana Júlia"))
            .body("age[1]", is(25))
            .body("filhos.name", hasItem(Arrays.asList("Zezinho","Luizinho")))
            .body("salary", contains(1234.5678f, 2500, null))
        ;
    }

    @Test
    @Description("Deve unir json com Java")
    public void devoUnirJsonPathComJAVA(){
        ArrayList<String> nomes =
                given()
                .when()
                    .get("https://restapi.wcaquino.me/users")
                .then()
                    .statusCode(200)
                    .extract().path("name.findAll{it.startsWith('Maria')}")
                ;
        assertEquals(1, nomes.size());
        assertTrue(nomes.get(0).equalsIgnoreCase("mAria Joaquina"));
        assertEquals(nomes.get(0).toUpperCase(), "maria joaquina".toUpperCase());
    }
}