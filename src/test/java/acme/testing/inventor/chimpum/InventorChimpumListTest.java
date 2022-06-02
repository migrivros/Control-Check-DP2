
package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/chimpum.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positive(final int recordIndex, final String title, final String code, final String description, final String budget, final String startDate, final String endDate, final String link) {
		super.signIn("inventor3", "inventor3");
		super.clickOnMenu("Inventor", "List my chimpums");

		super.checkListingExists();
		super.sortListing(1, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, startDate);
		super.checkColumnHasValue(recordIndex, 3, budget);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);
	}
}
