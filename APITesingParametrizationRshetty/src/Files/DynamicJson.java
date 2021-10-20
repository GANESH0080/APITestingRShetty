package Files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String addbookdetails = given().header("Content-Type", "application/json")
				.body(Payloads.addBookAPI(isbn, aisle)).when().post("Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath js = ReusebleMethod.rowtoJson(addbookdetails);
		String bookID = js.getString("ID");
		System.out.println(bookID);
	}
	

	@DataProvider(name="BooksData")
	public Object[][] getData() {

		return new Object[][] {{"aaa","1234"},{"bbb","4567"},{"ccc","7890"}};
	}
	
}
