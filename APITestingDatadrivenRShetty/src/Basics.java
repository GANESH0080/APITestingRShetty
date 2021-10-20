import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;

import Files.ReusebleMethod;

public class Basics {

	public static void main(String[] args) throws IOException {

		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files
						.readAllBytes(Paths.get("D:\\SeleniumWorkspace\\APITestingDatadrivenRShetty\\addPlace.json"))))
				.when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response()
				.asString();

		System.out.println(response);
		JsonPath js = ReusebleMethod.rowtoJson(response);
		String placeID = js.getString("place_id");
		System.out.println("Our placeID value is: " + placeID);

		// next update the place with new address once place is added using ADD_Place
		// API

		String address = "20 winter walk,USA";
		String UpdatePlace = given().log().all().param("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeID + "\",\r\n" + "\"address\":\"" + address + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated")).extract().response().asString();

		JsonPath js1 = ReusebleMethod.rowtoJson(UpdatePlace);
		String UpdatePlaceAPIResponse = js1.getString("msg");
		System.out.println("Our updated place value is: " + UpdatePlaceAPIResponse);

		// then use GET_Place API and validate newly address address
		String getAPI = given().log().all().param("key", "qaclick123").queryParam("place_id", placeID).when()
				.get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200)
				.body("address", equalTo(address)).extract().response().asString();

		JsonPath js2 = ReusebleMethod.rowtoJson(getAPI);
		String getAddressvalue = js2.getString("address");
		System.out.println("Our updated address value is: " + getAddressvalue);

		assertEquals(address, getAddressvalue);
	}

}
