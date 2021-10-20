package stepsDefinations;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;
import resource.APIResources;
import resource.Utils;
import resource.testDataBuild;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.junit.Assert.*;

public class stepDefination extends Utils {

	RequestSpecification res;
	ResponseSpecification respec;
	Response response;
	testDataBuild data = new testDataBuild();
	static String placeID;

	@Given("Add place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		// Pass RequestSpecBuilder class object in spec method and crected one object
		res = given().spec(requestSepecifications()).body(data.addPlacePayload(name, language, address));
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (httpMethod.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getResource()).then().spec(respec).extract().response();
		else if (httpMethod.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource()).then().spec(respec).extract().response();
	}

	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedKeyValue) {
		assertEquals(getJsonPath(response, keyValue), ExpectedKeyValue);
	}

	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		placeID = getJsonPath(response, "place_id");
		res = given().spec(requestSepecifications()).queryParams("place_id", placeID);
		user_calls_with_http_request(resource, "GET");
		String ActualName = getJsonPath(response, "name");
		assertEquals(ActualName,expectedName);
	}
	
	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
	  
		res = given().spec(requestSepecifications()).body(data.deletePlacePayload(placeID));
	}
	

}