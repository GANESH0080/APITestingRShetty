package MockResponsePractice;
import static org.testng.Assert.assertEquals;

import Files.Payloads;
import io.restassured.path.json.JsonPath;

public class CompressJsonParse {

	public static void main(String[] args) {
		// Get total courses size or count
		JsonPath js = new JsonPath(Payloads.coursePrice());
		int totalCources = js.getInt("courses.size()");
		System.out.println("Total cources are" + totalCources);

		// Get the value of total purchaseAmount
		int purchaseAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println("Total purchase amount is : " + purchaseAmt);

		// Get the tittle of first course
		String fCourseTtittle = js.getString("courses[0].title");
		System.out.println("Your First course Tittle name is :" + fCourseTtittle);

		// Get the tittle of last course
		String lastCourseTtittle = js.getString("courses[2].title");
		System.out.println("Your Last course Tittle name is :" + lastCourseTtittle);

		// How to get prices of respective courses
		for (int i = 0; i < totalCources; i++) {
			System.out.println("Cource name is: " + js.getString("courses[" + i + "].title"));
			System.out.println(js.getString("courses[" + i + "].title") + " " + "Course price is : "
					+ js.getInt("courses[" + i + "].price"));

			// How to get RPA cource total copies
			if (js.getString("courses[" + i + "].title").contentEquals("RPA")) {
				System.out.println("RPA cource total copies are :" + js.get("courses[" + i + "].copies").toString());
			}
		}
		System.out.println("--------------------------------------------------------------------------");
		// Verify the of all the courses price matches with purchase amount
		int temp = 0;
		for (int j = 0; j < totalCources; j++) {
			int price = js.getInt("courses[" + j + "].price");
			int copies = js.getInt("courses[" + j + "].copies");
			int totalCoursePrice = price * copies;
			temp = totalCoursePrice + temp;
			System.out.println("Temporary value of course is: " + temp);
		}
		System.out.println("Total purchase value of course is : " + temp);
		assertEquals(purchaseAmt, temp);
		if (purchaseAmt == temp) {
			System.out.println("Purchase amount is same as total course amount");
		}
	}
}