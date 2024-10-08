package br.com.lmelgarejo;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void deveAcessarSWAPI(){
        given()
            .log().all()
        .when()
            .get("https://swapi.dev/api/people/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Luke Skywalker"))
        ;
    }


    @Test
    public void deveObterClima(){
        given()
            .log().all()
            .queryParam("q", "Porto Alegre,BR")
            .queryParam("appid", "d7622f0ce23f551d008f9407e98fb6cd")
            .queryParam("units", "metric")
        .when()
            .get("https://api.openweathermap.org/data/2.5/weather")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Porto Alegre"))
            .body("coord.lon", is(-51.23f))
            .body("main.temp", greaterThan(5f))
        ;
    }

    @Test
    public void naoDeveAcessarSemSenha(){
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/basicauth")
        .then()
            .log().all()
            .statusCode(401)
        ;
    }

    @Test
    public void deveFazerAutenticacaoBasica(){
        given()
            .log().all()
        .when()
            .get("https://admin:senha@restapi.wcaquino.me/basicauth")
        .then()
            .log().all()
            .statusCode(200)
            .body("status", is("logado"))
        ;
    }

    @Test
    public void deveFazerAutenticacaoBasica2(){
        given()
            .log().all()
            .auth().preemptive().basic("admin", "senha")
        .when()
            .get("https://restapi.wcaquino.me/basicauth2")
        .then()
            .log().all()
            .statusCode(200)
            .body("status", is("logado"))
        ;
    }

    @Test
    public void deveFazerAutenticacaoComTokenJWT(){
        Map<String, String> login = new HashMap<>();
        login.put("email","melgarejom.leonardo@gmail.com");
        login.put("senha", "1234");

        String token = given()
            .log().all()
            .body(login)
            .contentType(ContentType.JSON)
        .when()
            .post("https://barrigarest.wcaquino.me/signin")
        .then()
            .log().all()
            .statusCode(200)
                .extract().path("token")
        ;
        given()
            .log().all()
            .header("Authorization", "JWT " + token)
        .when()
            .get("https://barrigarest.wcaquino.me/contas")
        .then()
            .log().all()
            .statusCode(200)
            .body("nome", hasItem("Conta de teste"))
        ;
    }

    @Test
    public void deveAcessarAplicacaoWeb(){
        String cookie = given()
            .log().all()
            .formParam("email", "melgarejom.leonardo@gmail.com")
            .formParam("senha", "1234")
            .contentType(ContentType.URLENC.withCharset("UTF-8"))
        .when()
            .post("https://seubarriga.wcaquino.me/logar")
        .then()
            .log().all()
            .statusCode(200)
            .extract().header("set-cookie")
        ;
        cookie = cookie.split("=")[1].split(";")[0];
        System.out.println(cookie);

        // https://seubarriga.wcaquino.me/contas
        String body = given()
            .log().all()
            .cookie("connect.sid", cookie)
        .when()
            .get("https://seubarriga.wcaquino.me/contas")
        .then()
            .log().all()
            .statusCode(200)
            .body("html.body.table.tbody.tr[0].td[0]", is("Conta de teste"))
            .extract().body().asString()
        ;

        System.out.println("----------------------------");
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, body);
        System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
        assertThat(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"), is("Conta de teste"));
    }

}
