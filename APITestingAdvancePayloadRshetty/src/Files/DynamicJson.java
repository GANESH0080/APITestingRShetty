package Files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test
	public void addBook() {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String addbookdetails = given().header("Content-Type", "application/json")
				.body(Payloads.addBookAPI("bhsv", "3235")).when().post("Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath js = ReusebleMethod.rowtoJson(addbookdetails);
		String bookID = js.getString("ID");
		System.out.println(bookID);
	}
}
