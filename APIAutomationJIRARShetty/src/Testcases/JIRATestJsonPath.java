package Testcases;

import Files.Payloads;
import Files.ReusebleMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.File;

public class JIRATestJsonPath {

	public static void main(String[] args) {
		// Base URL / ENDpoint URL
		RestAssured.baseURI = "http://localhost:8080";
		// Login in JIRA
		String loginResponse = given().log().all().header("Content-Type", "application/json").body(Payloads.LoginJIRA())
				.when().post("/rest/auth/1/session").then().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println(loginResponse);

		// 1 way to extract session ID from response using JsonPath class
		JsonPath loginSessionID = ReusebleMethod.rowtoJson(loginResponse);
		String SessionID = loginSessionID.getString("session.value");
		String sessionName = loginSessionID.getString("session.name");
		System.out.println("SessionId is: " +SessionID);
		System.out.println("SessionName is: " +sessionName);

		// Add comment in Issue
		String CommentaddedJIRA = given().log().all().pathParam("id", "10104").header("Content-Type", "application/json")
				.header("cookie", sessionName +"=" +SessionID).body("{\r\n"
						+ "    \"body\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper.\",\r\n"
						+ "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n"
						+ "    }\r\n"
						+ "}").when().post("/rest/api/2/issue/{id}/comment")
				.then().assertThat().statusCode(201).log().all().extract().response().asString();
		
		// trying to FInd the commentID after adding the comment
		JsonPath js = ReusebleMethod.rowtoJson(CommentaddedJIRA);
		String commentID = js.getString("id");
		System.out.println("Your comment ID is : "+commentID);
		
		
		// Add attachment in issue
		 given().log().all().pathParam("id", "10104").header("X-Atlassian-Token", "no-check").
		 header("Content-Type", "multipart/form-data")
		.header("cookie", sessionName +"=" +SessionID).multiPart("file", new File("APITesting.txt")).when().post("rest/api/2/issue/{id}/attachments").then().assertThat()
		.statusCode(200).log().all().extract().response().asString();
		
		 
		 // Get added issue comment details
				String getissueComment =  given().log().all().header("cookie", sessionName +"=" +SessionID)
						.pathParam("id", "10104").queryParam("fields", "comment").header("Content-Type", "application/json")
		          .when().get("/rest/api/2/issue/{id}").then().log().all().assertThat().statusCode(200).extract()
		          .response().asString();
				
				// Printing only comment of issue by passing the comment value in Query parameter
				System.out.println("Added issue comment is: " +getissueComment);
				
				// Trying to find the size of comments
				JsonPath js1 = ReusebleMethod.rowtoJson(getissueComment);
				int commentsCount = js1.getInt("fields.comment.comments.size()");
				System.out.println("total comment size is: "+commentsCount);
				
				// Iterating the comment	
				 for(int i=0; i<commentsCount; i++) {
				  String expectedmessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper.";
					String commentIDissue = js1.get("fields.comment.comments["+i+"].id").toString();
					if(commentIDissue.equalsIgnoreCase(commentID))
					{
						String message = js1.get("fields.comment.comments["+i+"].body").toString();
						System.out.println(message);
						assertEquals(message, expectedmessage);
					}
				  }
	}

}
