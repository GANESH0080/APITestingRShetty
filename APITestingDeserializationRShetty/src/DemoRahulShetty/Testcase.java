package DemoRahulShetty;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import java.util.List;

import PoJo.Api;
import PoJo.GetCourse;
import PoJo.WebAutomation;

public class Testcase {

	public static void main(String[] args) throws InterruptedException {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String code = "4%2F0AX4XfWhoLOBnELYD29Wq4ihid3Vgb957HatX_gqgz4PgL11EfYC_gzTLZsRi0fP8tJmp_Q";

		// Script for getting authorization details
		// Below commented code not working because google stopped automation for gmail
		// signIn page
		// For solution manual gerneate the autorization code using
		// Go to below get url, Enter username & password and take autorization code

		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "D:\\SeleniumWorkspace\\APITestingOAuth2.0RShetty\\chromedriver.exe");
		 * WebDriver driver = new ChromeDriver();
		 * 
		 * driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * ); driver.findElement(By.cssSelector("input[type='email']")).sendKeys(
		 * "ganusalunkhe@gmail.com");
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(3000);
		 * driver.findElement(By.cssSelector("input[type='password']")).sendKeys(
		 * "Latika@80");
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(3000); String url = driver.getCurrentUrl();
		 * System.out.println(url); String partialURL = url.split("code=")[1]; String
		 * code = partialURL.split("&scope=")[0];
		 * System.out.println("Value of code is :" +code);
		 */

		// Script for getting the Access Token
		String accessTokenresponse = given().urlEncodingEnabled(false).queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type", "authorization_code")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = new JsonPath(accessTokenresponse);
		String accessToken = js.getString("access_token");
		System.out.println("Your access token will be:" + accessToken);

		// Script for getting course details
		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when()
				.get("/getCourse.php").as(GetCourse.class);
		System.out.println("Your Linkdin profile link is : " + gc.getLinkedIn());
		System.out.println("Your URL is : " + gc.getUrl());
		System.out.println("Your Expertise is : " + gc.getExpertise());
		System.out.println("Your Instructor is : " + gc.getInstructor());
		System.out.println("Your Sservices is : " + gc.getServices());

		// How to get API related cources
		System.out.println(
				"Api cources coursetittle for 1st course is : " + gc.getCourses().getApi().get(1).getCourseTitle());

		// getting the coursetittle using index not a good practice because index will
		// be change in future
		List<Api> apicources = gc.getCourses().getApi();

		for (int i = 0; i < apicources.size(); i++) {
			if (apicources.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
				;
			{
				System.out.println(apicources.get(i).getPrice());
			}
		}

		
		//get the name of WebAutomation course name
		List<WebAutomation> wa = gc.getCourses().getWebAutomation();
		for (int j = 0; j < wa.size(); j++) 
		{
			System.out.println(wa.get(j).getCourseTitle());
		}

	}
}