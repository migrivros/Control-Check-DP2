package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumCreateTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex,final int recordIndexItem, final String pattern, final String title, final String description, final String budget, final String startDate, final String endDate, final String link) {
		super.signIn("inventor6", "inventor6");

		super.clickOnMenu("Inventor", "List my components");
		super.checkListingExists();
		
		super.clickOnListingRecord(recordIndexItem);

		super.clickOnButton("Associate Chimpum");
		
		super.fillInputBoxIn("pattern", pattern);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Inventor", "List my chimpums");
		super.checkListingExists();
		super.sortListing(1, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 2, startDate);
		super.checkColumnHasValue(recordIndex, 3, budget);

		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String pattern, final String title, final String description, final String budget, final String startDate, final String endDate, final String link) {

		super.signIn("inventor6", "inventor6");

		super.clickOnMenu("Inventor", "List my components");
		super.checkListingExists();
		
		super.clickOnListingRecord(recordIndex);

		super.clickOnButton("Associate Chimpum");
		
		super.fillInputBoxIn("pattern", pattern);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	@Order(30)
	public void hackingTest() {
		
		super.signIn("inventor6", "inventor6");
		
		// Cogemos el id de un componente del inventor6
		super.clickOnMenu("Inventor", "List my components");
		super.checkListingExists();
		super.clickOnListingRecord(1);
		final String createQuery ="language=en&debug=true&itemId=" + super.getCurrentQuery().substring(4);
		super.signOut();
		
		super.checkNotLinkExists("Account");
		super.navigate("/inventor/chimpum/create", createQuery);
		super.checkPanicExists();
		
		super.signIn("patron1", "patron1");
		super.navigate("/inventor/chimpum/create", createQuery);
		super.checkPanicExists();
		super.signOut();
		
		super.signIn("inventor3", "inventor3");
		super.navigate("/inventor/chimpum/create", createQuery);
		super.checkPanicExists();
		super.signOut();
		
		super.signIn("administrator", "administrator");
		super.navigate("/inventor/chimpum/create", createQuery);
		super.checkPanicExists();
		super.signOut();
	}
}
