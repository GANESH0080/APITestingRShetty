package Testcases;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.File;

import Files.Payloads;
import Files.ReusebleMethod;

public class JiraTestSessionFilter {

	public static void main(String[] args) {

		// BaseURL or ENDpoint URL
		RestAssured.baseURI = "http://localhost:8080";

		// 2nd way to extract session ID from response using SessionFilter rest assured
		// class
		SessionFilter session = new SessionFilter();
		
		// Login in JIRA application
		String SessionInfo = given().log().all().header("Content-Type", "application/json")
				.body(Payloads.LoginJIRA()).filter(session).when()
				.post("/rest/auth/1/session").then().assertThat().statusCode(200).extract().response().asString();

		// Add comment in Issue
		String addCommentJIRA = given().log().all().pathParam("id", "10103").header("Content-Type", "application/json")
				.body(Payloads.addcomment())
				.filter(session).when().post("/rest/api/2/issue/{id}/comment").then().assertThat().statusCode(201).log()
				.all().extract().response().asString();
		
		// trying to FInd the commentID after adding the comment
		JsonPath js = ReusebleMethod.rowtoJson(addCommentJIRA);
		String commentID = js.getString("id");
		System.out.println("Your comment is : "+commentID);

		
		// Add attachment in issue
		 given().log().all().pathParam("id", "10103").header("X-Atlassian-Token", "no-check").
		 header("Content-Type", "multipart/form-data")
		.filter(session).multiPart("file", new File("APITesting.txt")).when().post("rest/api/2/issue/{id}/attachments").then().assertThat()
		.statusCode(200).log().all().extract().response().asString();

		 // Get added issue comment details
		String GetIssueDetails = given().log().all().filter(session).pathParam("id", "10103").queryParam("fields", "comment")
				.header("Content-Type", "application/json")
          .when().get("/rest/api/2/issue/{id}").then().log().all().assertThat().statusCode(200).extract()
          .response().asString();
		 
		// Printing only comment of issue by passing the comment value in Query parameter
		System.out.println("Getting issue details:" +GetIssueDetails); 
		

		// Trying to find the size of comments
		JsonPath js1 = ReusebleMethod.rowtoJson(GetIssueDetails);
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
