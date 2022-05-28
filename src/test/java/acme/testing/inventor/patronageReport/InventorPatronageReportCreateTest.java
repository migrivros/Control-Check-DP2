
package acme.testing.inventor.patronageReport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class InventorPatronageReportCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/patronageReport/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positivePatronageReportTest(final int recordIndex, final String memorandum, final String moreInfo, final String confirmation) {
		super.signIn("inventor2", "inventor2");
		super.clickOnMenu("Inventor", "List my patronages");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.clickOnButton("Create report");

		super.fillInputBoxIn("memorandum", memorandum);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("confirmation", confirmation);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Inventor", "List my patronage reports");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		super.checkInputBoxHasValue("memorandum", memorandum);
		
		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/patronageReport/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeChirpTest(final int recordIndex, final String memorandum, final String moreInfo, final String confirmation) {

		super.signIn("inventor2", "inventor2");
		super.clickOnMenu("Inventor", "List my patronages");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.clickOnButton("Create report");

		super.fillInputBoxIn("memorandum", memorandum);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("confirmation", confirmation);
		super.clickOnSubmit("Create");
		super.checkFormExists();

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	@Order(30)
	public void hackingTest() {
		super.checkNotLinkExists("Account");
		super.navigate("/inventor/patronage-report/create", "masterId=35");
		super.checkPanicExists();

		super.signIn("patron1", "patron1");
		super.navigate("/inventor/patronage-report/create", "masterId=35");
		super.checkPanicExists();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.navigate("/inventor/patronage-report/create", "masterId=35");
		super.checkPanicExists();
		super.signOut();
		
		super.signIn("inventor1", "inventor1");
		super.navigate("/inventor/patronage-report/create", "masterId=35");
		super.checkPanicExists();
		super.signOut();
	}

}
