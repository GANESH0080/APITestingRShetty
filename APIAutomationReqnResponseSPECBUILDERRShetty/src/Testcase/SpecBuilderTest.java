package Testcase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import Pojo.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import Pojo.AddPlace;

public class SpecBuilderTest {

	public static void main(String[] args) {
		AddPlace dd = new AddPlace();
		dd.setAccuracy(50);
		dd.setAddress("29, side layout, cohen 09");
		dd.setLanguage("French-IN");
		dd.setPhone_number("(+91) 983 893 3937");
		dd.setWebsite("http://google.com");
		dd.setName("Frontline house");

		List<String> ls = new ArrayList<String>();
		ls.add("show park");
		ls.add("Shop");
		dd.setTypes(ls);

		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		dd.setLocation(l);

		// Create Object for RequestSpecBuilder class and set all the values
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		// Create Object for ResponseSpecBuilder class and set all the values
		ResponseSpecification respec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		// Pass RequestSpecBuilder class object in spec method and crected one object
		RequestSpecification res = given().spec(req).body(dd);

		// Pass ResponseSpecBuilder class object in spec method
		Response response = res.when().post("maps/api/place/add/json").then().spec(respec).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response();

		String responseString = response.asString();
		System.out.println(responseString);
	}
}
