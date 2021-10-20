package resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;

	public RequestSpecification requestSepecifications() throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));

			// Create Object for RequestSpecBuilder class and set all the values
			req = new RequestSpecBuilder().setBaseUri(getGlobalvalue("baseUrl")).addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();

			return req;
		}
		return req;
	}

	public static String getGlobalvalue(String key) throws IOException {
		Properties prod = new Properties();
		FileInputStream fis = new FileInputStream(
				"D:\\SeleniumWorkspace\\APIBDDFrameworkRShetty\\src\\test\\java\\resource\\globalproperties.properties");
		prod.load(fis);
		return prod.getProperty(key);

	}
	
	public String getJsonPath(Response response, String key)
	{
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
			}
}
