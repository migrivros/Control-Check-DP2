package acme.testing.any.toolkits;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyToolkitsListAllTest extends TestHarness{
	
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/any/toolkit/toolkit.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positive(final int recordIndex, final String code, final String title, final String description, final String assemblyNotes, 
						final String totalPrice, final String moreInfo) {
		
		super.clickOnMenu("Anonymous", "List toolkits");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title );
		super.checkColumnHasValue(recordIndex, 2, description);
		
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("assemblyNotes", assemblyNotes);
		super.checkInputBoxHasValue("totalPrice", totalPrice);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		
		super.checkButtonExists("Components");
		super.checkButtonExists("Tools");
		
	}
}
