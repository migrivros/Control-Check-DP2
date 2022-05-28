package acme.testing.patron.patronagereport;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronageReportListMineTest extends TestHarness{


	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronagereport/patronage-report.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positive(final int recordIndex, final String sequenceNumber, final String code, final String creationMoment, final String memorandum, final String moreInfo, final String legalStuff, final String budget) {
		
		super.signIn("patron1", "patron1");
		super.clickOnMenu("Patron", "List my patronage reports");

		super.checkListingExists();
		
		super.checkColumnHasValue(recordIndex, 0, sequenceNumber);
		super.checkColumnHasValue(recordIndex, 1, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("sequenceNumber", sequenceNumber);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("memorandum", memorandum);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		super.checkInputBoxHasValue("patronage.legalStuff", legalStuff);
		super.checkInputBoxHasValue("patronage.budget", budget);
	}
}
