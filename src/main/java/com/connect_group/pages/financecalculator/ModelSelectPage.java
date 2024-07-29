package com.connect_group.pages.financecalculator;

import com.connect_group.pages.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ModelSelectPage extends BasePage {

	public static int maxMonthlyPaymentValue;
	public static int maxDeposit;

	public ModelSelectPage(WebDriver driver) {
		super(driver);
	}

	private WebElement getFinanceCalculatorPage() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.className("fc-page-wrapper__container")));
	}

	private WebElement getModelSelectNameplateContainer() {
		return until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='fc-container__wrapper fc-nameplates__nameplates']")));
	}

	public List<WebElement> getAllNameplates() {
		return until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("fc-nameplate__content")));
	}

	public boolean isInitialized() {
		return getFinanceCalculatorPage().isDisplayed();
	}

	public boolean verifyVehicleModelsLoading() {
		waitForElementToAppear(getModelSelectNameplateContainer());
		return getModelSelectNameplateContainer().isDisplayed();

	}

	// TODO: Add methods in to support interacting with the model selection page.

	// Elements
	private WebElement getPopupClose() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.id("psyma_close_button_link")));
	}

	private WebElement getMonthlyPaymentTab() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.id("switcher-button-fbmp")));
	}

	private WebElement getPaymentSlider() {
		return until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='fc-range-slider__range-slider__handle' and @id='handle_max']")));
	}

	private WebElement getRequiedMaxValue(String maxValue) {
		String reruiredXpath = "//div[@class='fc-range-slider__range-slider__handle' and @aria-valuemax='%s']";
		reruiredXpath = reruiredXpath.replaceAll("%s", maxValue);
		return until(ExpectedConditions.visibilityOfElementLocated(By.xpath(reruiredXpath)));
	}

	private WebElement getdepositTextBox() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.id("deposit")));
	}

	private WebElement searchArea() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.className("fc-filter-bar__filter-bar__filters")));
	}

	private WebElement getAcceptValuesButton() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test-id='filter-cta']")));
	}

	private List<WebElement> getSearchResults() {
		return until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
				"//div[@class='fc-nameplate__nameplate fc-nameplate__nameplate--4-per-row']//div//span[@class='fc-nameplate-items__value' and @data-test-id='nameplate-items-From-value']")));
	}

	private WebElement getSearchResultsHeaderForMaxDeposit() {
		return until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[@data-test-id='fbmp-results-parameters']")));
	}

	private String getSearchResultCount() {
		return until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@data-test-id='fbmp-results-heading']")))
				.getText();
	}

	private WebElement getaModelSelectionLink() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.className("fc-nameplate__image")));
	}

	private WebElement getaModelAsPerSelectionRequired(String modelName) {
		String reruiredXpath = "//a[@type='button' and @aria-label = 'CHOOSE %s']";
		reruiredXpath = reruiredXpath.replaceAll("%s", modelName);
		System.out.println(reruiredXpath);
		return until(ExpectedConditions.visibilityOfElementLocated(By.xpath(reruiredXpath)));
	}

	// Methods
	public void closePopUp() {
		try {
			if (getPopupClose().isDisplayed()) {
				getPopupClose().click();
				System.out.println("Pop up displayed was closed.");
			}
		} catch (Exception e) {
			System.out.println("No alert was present.");
		}
	}

	public void navigateToMonthlyPayment() {
		WebElement paymentTab = getMonthlyPaymentTab();
		if (paymentTab.isEnabled()) {
			paymentTab.click();
		}
	}

	public void reduceMonthlyPayment(int maxValue) throws InterruptedException {
		maxMonthlyPaymentValue = maxValue;
		WebElement e = getPaymentSlider();

		int maxIteration = 50;
		for (int i = 0; i < maxIteration; i++) {
			actions().clickAndHold(e).moveByOffset(-5, 0).release().perform();
			String currentMax = e.getAttribute("aria-valuenow");
			System.out.println("currentMax=" + currentMax);
			if (currentMax.equals(String.valueOf(maxValue)) || currentMax.equals("250"))
				break;
		}

	}

	public void enterDeposit(int deposit) throws InterruptedException {
		maxDeposit = deposit;
		String depositVaue = String.valueOf(deposit);
		getdepositTextBox().clear();
		getdepositTextBox().sendKeys(depositVaue);
		searchArea().click();
	}

	public void clickAcceptValuesAndCalculateButton() throws InterruptedException {
		if (getAcceptValuesButton().isEnabled()) {
			getAcceptValuesButton().click();
		} else
			System.out.println("Accept And Calculate button is not displayed");
	}

	public boolean validateResultsbasedOnMonthlyPayment() throws InterruptedException {
		try {
			if (!getSearchResultCount().equalsIgnoreCase("0 results")) {
				List<Integer> searchResultMonthlyPaymentValues = new ArrayList<Integer>();
				for (WebElement searchResult : getSearchResults()) {
					String valueDisplayed = searchResult.getText(); // £524.24 per month
					valueDisplayed = valueDisplayed.substring(1, valueDisplayed.indexOf(".")).replaceAll(",", "");
					int value = Integer.parseInt(valueDisplayed);
					System.out.println("ValueDisplayed= " + value);
					searchResultMonthlyPaymentValues.add(value);
					Thread.sleep(3000);
				}
				return (compareValues(searchResultMonthlyPaymentValues, maxMonthlyPaymentValue)); // maxMonthlyPaymentValue
			}
			System.out.println("0 Search Results found for the inputs");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean compareValues(List<Integer> searchResultMonthlyPaymentValues, int maxMonthlyPaymentValue) {
		for (int i : searchResultMonthlyPaymentValues) {
			if (i > maxMonthlyPaymentValue)
				return false;
		}
		return true;

	}

	public void selectModel() {
		if (getaModelSelectionLink().isEnabled()) {
			getaModelSelectionLink().click();
		}
	}

	public void selectRequiredModel(String string) {
		if (getaModelAsPerSelectionRequired(string).isDisplayed()) {
			JavascriptExecutor jsExec = (JavascriptExecutor) driver;
			jsExec.executeScript("arguments[0].scrollIntoView(true);", getaModelAsPerSelectionRequired(string));
			jsExec.executeScript("arguments[0].click();", getaModelAsPerSelectionRequired(string));
		}
	}

	public boolean validateResultsbasedOnMaxValueProvided() {
		try {			
				if (getSearchResultsHeaderForMaxDeposit().isDisplayed()) {
					String actualResultHeader = getSearchResultsHeaderForMaxDeposit().getText();
					actualResultHeader = actualResultHeader
							.substring(actualResultHeader.indexOf("£") + 1, actualResultHeader.indexOf("."))
							.replaceAll(",", "");
					if (maxDeposit == Integer.parseInt(actualResultHeader))
						return true;
				}
			
				return false;	
		} catch (Exception e) {
			System.out.println("Given Max Value is not matching Results");
			e.printStackTrace();
		}
		return false;
	}

}