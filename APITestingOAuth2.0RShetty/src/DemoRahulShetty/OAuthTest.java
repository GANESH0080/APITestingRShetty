package DemoRahulShetty;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {

		 RestAssured.baseURI = "https://rahulshettyacademy.com";

		String code = "4%2F0AX4XfWgGEI6TDNbeAMtcsMjqWKTcNi9eDKxnkFRIXbBg1Wh-ad-K8xbIMUTTHYUOtamkLQ";
		
		
		// Script for getting authorization details
		// Below commented code not working because google stopped automation for gmail signIn page 
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
		String response = given().queryParam("access_token", accessToken).when().log().all()
				.get("/getCourse.php").asString();
		System.out.println(response);
	}

}
