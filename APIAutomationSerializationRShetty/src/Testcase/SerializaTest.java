package Testcase;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import Pojo.AddPlace;
import Pojo.Location;
import io.restassured.RestAssured;

public class SerializaTest {

	
	public static void main(String [] args)
	{
		AddPlace dd = new AddPlace();
		dd.setAccuracy(50);
		dd.setAddress("Kalamboli");
		dd.setLanguage("Marathi");
		dd.setPhone_number("9768180080");
		dd.setWebsite("https://rahulshettyacademy.com/");
		dd.setName("Ganesh");
		
		List<String> ls = new ArrayList<String>();
		ls.add("show park");
		ls.add("zoo");	
		dd.setTypes(ls);
		
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		dd.setLocation(l);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(dd).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response()
				.asString();
		System.out.println(response);

	}
}
