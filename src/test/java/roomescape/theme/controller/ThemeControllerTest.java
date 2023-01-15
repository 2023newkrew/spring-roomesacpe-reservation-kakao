package roomescape.theme.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import roomescape.theme.dto.request.ThemeRequestDTO;
import roomescape.theme.dto.response.ThemeResponseDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class ThemeControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void createTheme() {
        final String expectedName = "name";
        final String expectedDesc = "desc";
        final int expectedPrice = 1000;

        final ThemeRequestDTO themeRequestDTO = ThemeRequestDTO.builder()
                .name(expectedName)
                .desc(expectedDesc)
                .price(expectedPrice)
                .build();

        final ThemeResponseDTO expectedResult = ThemeResponseDTO.builder().id(1L).name(expectedName).desc(expectedDesc)
                .price(expectedPrice).build();

        final ThemeResponseDTO result = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1")
                .extract()
                .as(ThemeResponseDTO.class);

        assertThat(result).isEqualTo(expectedResult);
    }
}
