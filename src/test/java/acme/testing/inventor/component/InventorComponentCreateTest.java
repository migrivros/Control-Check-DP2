package acme.testing.inventor.component;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorComponentCreateTest extends TestHarness {
	
		// Lifecycle management ---------------------------------------------------

		// Test cases -------------------------------------------------------------

		@ParameterizedTest
		@CsvFileSource(resources = "/inventor/component/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void positiveTest(final int recordIndex, final String name, final String code, final String technology, final String description, final String retailPrice, final String moreInfo) {
			super.signIn("inventor1", "inventor1");

			super.clickOnMenu("Inventor", "List my components");
			super.checkListingExists();

			super.clickOnButton("Create new Component");
			
			super.fillInputBoxIn("name", name);
			super.fillInputBoxIn("code", code);
			super.fillInputBoxIn("technology", technology);
			super.fillInputBoxIn("description", description);
			super.fillInputBoxIn("retailPrice", retailPrice);
			super.fillInputBoxIn("moreInfo", moreInfo);
			super.clickOnSubmit("Create");

			super.clickOnMenu("Inventor", "List my components");
			super.checkListingExists();
			super.sortListing(0, "asc");
			super.checkColumnHasValue(recordIndex, 0, name);
			super.checkColumnHasValue(recordIndex, 1, code);
			super.checkColumnHasValue(recordIndex, 2, technology);
			super.checkColumnHasValue(recordIndex, 3, description);
			super.clickOnListingRecord(recordIndex);

			super.checkFormExists();
			super.checkInputBoxHasValue("name", name);
			super.checkInputBoxHasValue("code", code);
			super.checkInputBoxHasValue("technology", technology);
			super.checkInputBoxHasValue("description", description);
			super.checkInputBoxHasValue("retailPrice", retailPrice);
			super.checkInputBoxHasValue("moreInfo", moreInfo);

			super.signOut();
		}

		@ParameterizedTest
		@CsvFileSource(resources = "/inventor/component/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(20)
		public void negativeTest(final int recordIndex, final String name, final String code, final String technology, final String description, final String retailPrice, final String moreInfo) {

			super.signIn("inventor1", "inventor1");

			super.clickOnMenu("Inventor", "List my components");
			super.checkListingExists();

			super.clickOnButton("Create new Component");
			
			super.fillInputBoxIn("name", name);
			super.fillInputBoxIn("code", code);
			super.fillInputBoxIn("technology", technology);
			super.fillInputBoxIn("description", description);
			super.fillInputBoxIn("retailPrice", retailPrice);
			super.fillInputBoxIn("moreInfo", moreInfo);
			super.clickOnSubmit("Create");
			
			super.checkErrorsExist();

			super.signOut();
		}

		@Test
		@Order(30)
		public void hackingTest() {
			super.checkNotLinkExists("Account");
			super.navigate("/inventor/item/create");
			super.checkPanicExists();
			
			super.signIn("patron1", "patron1");
			super.navigate("/inventor/item/create");
			super.checkPanicExists();
			super.signOut();
			
			super.signIn("administrator", "administrator");
			super.navigate("/inventor/item/create");
			super.checkPanicExists();
			super.signOut();
		}

		// Ancillary methods ------------------------------------------------------

}
