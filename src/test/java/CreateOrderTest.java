import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String[] changeColor;

    public CreateOrderTest(String[] changeColor) {
        this.changeColor = changeColor;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getQuest() {


        return new Object[][]{
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY", "BLACK"}},
                {new String[]{""}},
        };

    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder() {
        SetupOrderList setupOrderList = new SetupOrderList("Dimchik", "Dimkin", "Konoha, 142 apt.", "4",
                "+7 777 777 33 35", 2, "2022-12-12", "Faster", changeColor);
        given()
                .header("Content-type", "application/json")
                .body(setupOrderList)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}