package resource;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class testDataBuild {

	public AddPlace addPlacePayload(String name, String language, String address) {
		AddPlace dd = new AddPlace();
		dd.setAccuracy(50);
		dd.setAddress(address);
		dd.setLanguage(language);
		dd.setPhone_number("(+91) 983 893 3937");
		dd.setWebsite("http://google.com");
		dd.setName(name);

		List<String> ls = new ArrayList<String>();
		ls.add("show park");
		ls.add("Shop");
		dd.setTypes(ls);

		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		dd.setLocation(l);

		return dd;

	}
	
	public String deletePlacePayload(String placeId)
	{
       return "{\r\n   \"place_id\":\""+placeId+"\"\r\n}";		
	}

}
