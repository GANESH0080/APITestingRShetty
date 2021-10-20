# Add place
Feature: Validating Place API's 

@AddPlace @Regression
Scenario Outline: Verify ADD place functionality working or not 
	Given Add place payload with "<name>" "<language>" "<address>" 
	When User calls "AddPlaceAPI" with "POST" http request 
	Then the API call is success with status code 200 
	And "status" in response body is "OK" 
	And "scope" in response body is "APP" 
	And verify place_id created maps to "<name>" using "GetPlaceAPI" 
	
	
	
	Examples: 
		|name           |language |address |
		|Balu           |Marathi  |Vashi   |
	#	|Kalu           |Hindi    |Belapur |
		
		@DeletePlace @Regression 
		Scenario: Verify If Delete Place functionality is working. 
			Given DeletePlace payload 
			When User calls "DeletePlaceAPI" with "POST" http request 
			Then the API call is success with status code 200 
			Then the API call is success with status code 200 
			
	