package br.com.lmelgarejo;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FileTest {

    @Test
    public void deveObrigarEnvioArquivo(){
        given()
            .log().all()
        .when()
            .post("http://restapi.wcaquino.me/upload")
        .then()
            .log().all()
            .statusCode(404)
            .body("error", is("Arquivo não enviado"))
        ;
    }

    @Test
    public void deveEnviarArquivoUpload(){
        given()
            .log().all()
            .multiPart("arquivo", new File("src/test/resources/users.pdf"))
        .when()
            .post("http://restapi.wcaquino.me/upload")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("users.pdf"))
        ;
    }

    @Test
    public void naoDeveFazerUploadArquivoGrande(){
        given()
            .log().all()
            .multiPart("arquivo", new File("src/test/resources/users2.pdf"))
        .when()
            .post("http://restapi.wcaquino.me/upload")
        .then()
            .log().all()
            .time(lessThan(5000L))
            .statusCode(200)
            .body("name", is("users2.pdf"))
        ;
    }

    @Test
    public void deveBaixarArquivo() throws IOException {
        byte[] image = given()
            .log().all()
        .when()
            .get("http://restapi.wcaquino.me/download")
        .then()
            .statusCode(200)
            .extract().asByteArray()
        ;
        File imagem = new File("src/test/resources/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();

        assertThat(imagem.length(), lessThan(100000L));
    }

    @Test
    public void deveValidarSchemaJson(){
        given()
            .log().all()
        .when()
            .get("http://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"))
        ;
    }
}
