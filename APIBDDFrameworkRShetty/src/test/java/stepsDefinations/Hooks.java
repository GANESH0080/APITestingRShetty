package stepsDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		stepDefination step = new stepDefination();
		
		if(stepDefination.placeID==null)
		{
		step.add_place_payload_with("Rahul", "Frentch", "Turbhe");
		step.user_calls_with_http_request("AddPlaceAPI", "POST");
		step.verify_place_id_created_maps_to_using("Rahul", "GetPlaceAPI");
	}
}
}