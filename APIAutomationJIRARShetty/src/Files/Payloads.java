package Files;

public class Payloads {

	public static String addcomment() {
		return "{\r\n"
				+ "    \"body\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper.\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}";

	}

	public static String LoginJIRA() {

		return "{ \"username\": \"Ganesh0080\", \"password\": \"Salunkhe@97\" }";
	}
}
