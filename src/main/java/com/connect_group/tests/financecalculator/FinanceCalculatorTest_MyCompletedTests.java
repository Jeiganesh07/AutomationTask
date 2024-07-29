package com.connect_group.tests.financecalculator;

import com.connect_group.pages.financecalculator.FilterByPage;
import com.connect_group.pages.financecalculator.ModelSelectPage;
import com.connect_group.pages.financecalculator.PersonalisedQuotePage;
import com.connect_group.tests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

class FinanceCalculatorTest_MyCompletedTests extends BaseTest {

	private ModelSelectPage modelSelectPage;
	private FilterByPage filterByPage;
	private PersonalisedQuotePage personalisedQuotePage;

	@BeforeEach
	void setupAndNavigateToUrl() {
		modelSelectPage = new ModelSelectPage(driver);
		filterByPage = new FilterByPage(driver);
		personalisedQuotePage = new PersonalisedQuotePage(driver);

		open("https://www.landrover.co.uk/offers-and-finance/finance-calculator.html");
	}

	// TODO: Complete the each of the tests under the "Tasks to Complete" section of
	// the README.md

	@Disabled("Skipping first test")
	@Test
	void vehicleModelsListTest() {
		modelSelectPage.closePopUp();
		assertTrue(modelSelectPage.isInitialized());
		List<WebElement> namePlatesList = modelSelectPage.getAllNameplates();
		assertEquals(8, namePlatesList.size());
		screenShotService.takeScreenshot("_VehicleNamePlateListTest_success");
	}

	@Test
	void monthlyPaymentUpgrade() throws InterruptedException {

		modelSelectPage.closePopUp();
		modelSelectPage.navigateToMonthlyPayment();
		modelSelectPage.reduceMonthlyPayment(750);
		modelSelectPage.enterDeposit(20000);
		modelSelectPage.clickAcceptValuesAndCalculateButton();
		assertTrue(modelSelectPage.validateResultsbasedOnMonthlyPayment());
		// add assert to check max deposit value also
		screenShotService.takeScreenshot("_MonthlyPaymentTab_success");
	}

	@Test
	void PersonalisedQuotePageNavigation() throws InterruptedException {

		modelSelectPage.closePopUp();
		assertTrue(modelSelectPage.isInitialized());
		modelSelectPage.selectModel();
		assertTrue(personalisedQuotePage.isInitialized());
		personalisedQuotePage.clickChangeVehicle();
		assertTrue(modelSelectPage.isInitialized());
		List<WebElement> namePlatesList = modelSelectPage.getAllNameplates();
		assertTrue(namePlatesList.size() > 0);
		screenShotService.takeScreenshot("_PersonalisedQuotePageNavigation_success");
	}
	
	void vehicleModelsListTestNew() {
		int expectedNumberOfVehicles = 7;
		assertTrue(modelSelectPage.isInitialized());
		modelSelectPage.closePopUp();
		assertTrue(modelSelectPage.verifyVehicleModelsLoading());
		List<WebElement> namePlatesList = modelSelectPage.getAllNameplates();
		assertEquals(expectedNumberOfVehicles, namePlatesList.size());
		screenShotService.takeScreenshot("_VehicleNamePlateListTest_success");
	}
	
	 @Test
	  void verifyEngineValueDropDownSelection() throws InterruptedException {
		  
		  String modelSelection="DISCOVERY SPORT";
		  modelSelectPage.closePopUp();
		  assertTrue(modelSelectPage.isInitialized());
		  modelSelectPage.selectRequiredModel(modelSelection);
		  assertTrue(personalisedQuotePage.isInitialized());
		  personalisedQuotePage.navigateToEngineDropDown();
		  assertTrue(personalisedQuotePage.selectaDifferentEngineInDropDown());
		  
	    screenShotService.takeScreenshot("_SelectDifferenttEngineValueDropDown_success");
	  }
	 
	  @Test
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