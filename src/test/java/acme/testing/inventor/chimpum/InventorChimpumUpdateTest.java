package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumUpdateTest  extends TestHarness {

	// Test cases -------------------------------------------------------------

		@ParameterizedTest
		@CsvFileSource(resources = "/inventor/chimpum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void positive(final int recordIndex, final String title, final String description, final String budget, final String startDate, final String endDate, final String link) {
			
			super.signIn("inventor3", "inventor3");

			super.clickOnMenu("Inventor", "List my chimpums");
			
			super.checkListingExists();
			super.sortListing(1, "asc");
			super.clickOnListingRecord(recordIndex);
			super.checkFormExists();
			
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("description", description);
			super.fillInputBoxIn("budget", budget);
			super.fillInputBoxIn("startDate", startDate);
			super.fillInputBoxIn("endDate", endDate);
			super.fillInputBoxIn("link", link);
			
			
			super.clickOnSubmit("Update");


			super.clickOnMenu("Inventor", "List my chimpums");
		
			super.checkListingExists();
			super.sortListing(1, "asc");
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
		@CsvFileSource(resources = "/inventor/chimpum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(20)
		public void negativeTest(final int recordIndex, final String title, final String description, final String budget, final String startDate, final String endDate, final String link) {
			
			super.signIn("inventor3", "inventor3");

			super.clickOnMenu("Inventor", "List my chimpums");
			
			super.checkListingExists();
			super.sortListing(1, "asc");
			super.clickOnListingRecord(recordIndex);
			super.checkFormExists();
			
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("description", description);
			super.fillInputBoxIn("budget", budget);
			super.fillInputBoxIn("startDate", startDate);
			super.fillInputBoxIn("endDate", endDate);
			super.fillInputBoxIn("link", link);
			
			super.clickOnSubmit("Update");

			super.checkErrorsExist();

			super.signOut();
		}
		
		@Test
		@Order(30)
		public void hackingTest() {
			
			super.signIn("inventor3", "inventor3");
			
			// Cogemos el id de un chimpum inventor3
			super.clickOnMenu("Inventor", "List my chimpums");
			super.checkListingExists();
			super.clickOnListingRecord(1);
			final String createQuery ="language=en&debug=true&id=" + super.getCurrentQuery().substring(4);
			super.signOut();
			
			super.checkNotLinkExists("Account");
			super.navigate("/inventor/chimpum/update", createQuery);
			super.checkPanicExists();
			
			super.signIn("patron1", "patron1");
			super.navigate("/inventor/chimpum/update", createQuery);
			super.checkPanicExists();
			super.signOut();
			
			super.signIn("inventor6", "inventor6");
			super.navigate("/inventor/chimpum/update", createQuery);
			super.checkPanicExists();
			super.signOut();
			
			super.signIn("administrator", "administrator");
			super.navigate("/inventor/chimpum/update", createQuery);
			super.checkPanicExists();
			super.signOut();
			
		}

}
