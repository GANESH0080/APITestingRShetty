package Files;

import io.restassured.path.json.JsonPath;

public class ReusebleMethod {

	public static JsonPath rowtoJson(String response)
	{
		JsonPath object = new JsonPath(response);
		return object;
	}
}
