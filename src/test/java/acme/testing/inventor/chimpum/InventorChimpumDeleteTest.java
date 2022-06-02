package acme.testing.inventor.chimpum;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumDeleteTest extends TestHarness{

	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex) {
		super.signIn("inventor6", "inventor6");

		super.clickOnMenu("Inventor", "List my components");
		super.checkListingExists();
		
		super.clickOnListingRecord(recordIndex);

		super.checkNotButtonExists("Associate Chimpum");
		
		super.clickOnMenu("Inventor", "List my chimpums");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.clickOnSubmit("Delete");
		
		super.clickOnMenu("Inventor", "List my components");
		super.checkListingExists();
		
		super.clickOnListingRecord(recordIndex);

		super.checkButtonExists("Associate Chimpum");

		super.signOut();
	}
	
}