
package com.connect_group.tests.financecalculator;

import com.connect_group.pages.financecalculator.FilterByPage;
import com.connect_group.pages.financecalculator.ModelSelectPage;
import com.connect_group.pages.financecalculator.PersonalisedQuotePage;
import com.connect_group.tests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FinanceCalculatorTest extends BaseTest {

	private ModelSelectPage modelSelectPage;
	private FilterByPage filterByPage;
	private PersonalisedQuotePage personalisedQuotePage;
	private Properties properties;

	@BeforeEach
	void setupAndNavigateToUrl() throws IOException {
		modelSelectPage = new ModelSelectPage(driver);
		filterByPage = new FilterByPage(driver);
		personalisedQuotePage = new PersonalisedQuotePage(driver);

		properties = new Properties();
		InputStream input = new FileInputStream("Inputs.properties");
		properties.load(input);
		String url = properties.getProperty("URL_financeCalculator");
		open(url);

	}

	// TODO: Complete the each of the tests under the "Tasks to Complete" section of
	// the README.md

	@Test
	@Order(1)
	void vehicleModelsListTest() throws InterruptedException {
		int expectedNumberOfVehicles = Integer.parseInt(properties.getProperty("expectedNumberOfVehicles"));
		assertTrue(modelSelectPage.isInitialized());
		modelSelectPage.closePopUp();
		assertTrue(modelSelectPage.verifyVehicleModelsLoading());
		List<WebElement> namePlatesList = modelSelectPage.getAllNameplates();
		
		assertEquals(expectedNumberOfVehicles, namePlatesList.size());
		screenShotService.takeScreenshot("_VehicleNamePlateListTest_success");
	}

	@Test
	@Order(2)
	void monthlyPaymentUpgrade() throws InterruptedException {
		int monthlyPayment = Integer.parseInt(properties.getProperty("monthlyPayment"));
		int maxDeposit = Integer.parseInt(properties.getProperty("maxDeposit"));
		modelSelectPage.closePopUp();
		modelSelectPage.navigateToMonthlyPayment();
		modelSelectPage.reduceMonthlyPayment(monthlyPayment);
		modelSelectPage.enterDeposit(maxDeposit);
		modelSelectPage.clickAcceptValuesAndCalculateButton();

		assertTrue(modelSelectPage.validateResultsbasedOnMonthlyPayment());
		assertTrue(modelSelectPage.validateResultsbasedOnMaxValueProvided());
		screenShotService.takeScreenshot("_MonthlyPaymentTab_success");
	}

	@Test

	@Order(3)
	void PersonalisedQuotePageNavigation() throws InterruptedException {

		modelSelectPage.closePopUp();
		assertTrue(modelSelectPage.isInitialized());
		modelSelectPage.selectModel();
		assertTrue(personalisedQuotePage.isInitialized());
		screenShotService.takeScreenshot("_NavigateToPersonalisedQuotePageNavigation_success");
		personalisedQuotePage.clickChangeVehicle();
		assertTrue(modelSelectPage.isInitialized());

		screenShotService.takeScreenshot("_NavigateBackToModelSelectionPage_success");
	}

	@Test
	@Order(4)
	void verifyEngineValueDropDownSelection() throws InterruptedException {

		String modelSelection = properties.getProperty("modelSelection");
		modelSelectPage.closePopUp();
		assertTrue(modelSelectPage.isInitialized());
		modelSelectPage.selectRequiredModel(modelSelection);
		assertTrue(personalisedQuotePage.isInitialized());
		
		assertTrue(personalisedQuotePage.navigateToEngineDropDown());
		assertTrue(personalisedQuotePage.selectaDifferentEngineInDropDown());

		screenShotService.takeScreenshot("_SelectDifferenttEngineValueDropDown_success");
	}

	@Test

	@Order(5)
	void verifyToolTipinPersonaliseQuote() throws InterruptedException {

		modelSelectPage.closePopUp();
		assertTrue(modelSelectPage.isInitialized());
		modelSelectPage.selectModel();
		assertTrue(personalisedQuotePage.isInitialized());
		personalisedQuotePage.navigateToToolTip();
		
		assertTrue(personalisedQuotePage.verifyInformationPopUp());
		screenShotService.takeScreenshot("_SelectDifferenttEngineValueDropDown_success");
	}

}
