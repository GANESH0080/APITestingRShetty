package Files;

public class Payloads {

	public static String addBookAPI(String isbn, String aisle) {
		String payloadvariable = "{\r\n" + "\r\n" + "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n" + "\"aisle\":\""+aisle+"\",\r\n" + "\"author\":\"John foe\"\r\n" + "}\r\n" + "\r\n"
				+ "";
		return payloadvariable;
	}
}

