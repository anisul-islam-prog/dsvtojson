package dsvtojson;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConvertToJSONTest {
	String jsonString;

	@Test
	public void test() {
		String line = "Wolfgang,Amadeus,Mozart,Male,1756-01-27,1000";
		String result = "{\"firstName\": \"Wolfgang\",\"middleName\": \"Amadeus\",\"lastName\": \"Mozart\",\"gender\": \"Male\",\"dateOfBirth\": \"0031-10-22\",\"salary\": \"1000\"}";
		String[] headers = { "firstName", "middleName", "lastName", "gender", "dateOfBirth", "salary" };
		ToJson tj = new ToJson();
		String jsonString= tj.lineToJson(headers, ",", line);
		assertEquals(result, jsonString);
	}

}
