package com.connect_group.pages.financecalculator;

import com.connect_group.pages.BasePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class PersonalisedQuotePage extends BasePage {

	public PersonalisedQuotePage(WebDriver driver) {
		super(driver);
	}

	private WebElement getPersonalisedQuotePage() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.className("fc-quote-page__container")));
	}

	public boolean isInitialized() {
		return getPersonalisedQuotePage().isDisplayed();
	}

	private WebElement getChangeVehicle() {
		return until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//a[@class='fc-cta__cta fc-cta__cta--default-light']//span[text()='Change Vehicle']")));
	}

	private WebElement getEngineDropDownDefault() {
		return until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[@data-di-id='#dropdown__button-engines']")));
	}

	private List<WebElement> getNumberofEngineOptionsAvailable() {
		return until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@id='dropdown__list-engines']//li")));
	}

	private WebElement getAnOptionInEngineDropDown(int option) {
		// String requiredXpath = "(//ul[@id='dropdown__list-engines']//li)[%s]";
		//ssample test quote//ssample test quote
		//ssample test quote//ssample test quote
		String requiredXpath = "(//ul[@id='dropdown__list-engines']//li//button)[%s]";
		requiredXpath = requiredXpath.replaceAll("%s", String.valueOf(option));
		return until(ExpectedConditions.visibilityOfElementLocated(By.xpath(requiredXpath)));
	}

	private WebElement getToolTip() {
		return until(
				ExpectedConditions.visibilityOfElementLocated(By.className("fc-tooltip-button-icon__tooltipIcon")));

	}

	private WebElement getinformationPopup() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[@class='fc-modal__container fc-modal__container--isOpening' and @aria-label='PERSONAL CONTRACT PURCHASE']")));

	}

	private String getinformationPopupTitle() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.id("modalTitle_PCP"))).getText();

	}

	private WebElement getInformationPopupCloseButton() {
		return until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-close-btn")));
	}

	private WebElement footerpopUp() {
		return until(
				ExpectedConditions.visibilityOfElementLocated(By.className("footer_banner footer_banner_animate")));
	}

	private WebElement closeFooterpopUp() {
		return until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='icon-close icon-close-message']")));
	}

	private String getDropDownLoading() {
		String myxpath = "//button[@data-di-id='#dropdown__button-engines']";
		return myxpath;
	}

	// Methods

	public void clickChangeVehicle() {
		if (getChangeVehicle().isEnabled()) {
			getChangeVehicle().click();
		}
	}

	public boolean navigateToEngineDropDown() throws InterruptedException {

		if (getEngineDropDownDefault().isDisplayed()) {
			// verifyfooterpopUp();
			JavascriptExecutor jsExec = (JavascriptExecutor) driver;
			jsExec.executeScript("arguments[0].scrollIntoView(true);", getEngineDropDownDefault());
			return true;
		} else {
			System.out.println("Engine DropDown is not displayed");
			return false;
		}
	}

	public boolean selectaDifferentEngineInDropDown_jei() throws InterruptedException {

		String secondValueText = null;
		// Count the number of options
		getEngineDropDownDefault().click();
		int numberOfOptionsAvailable = 0;
		String defaultEngineValue = getEngineDropDownDefault().getText();
		if (getEngineDropDownDefault().isDisplayed()) {
			numberOfOptionsAvailable = getNumberofEngineOptionsAvailable().size(); // slow
			System.out.println("NumberOfOptionsAvailable=" + numberOfOptionsAvailable);
			if (numberOfOptionsAvailable > 1) {
				System.out.println("NumberOfOptionsAvailable is > 1");
				for (int i = 0; i <= getNumberofEngineOptionsAvailable().size(); i++) {
					if (!getNumberofEngineOptionsAvailable().get(i).equals(defaultEngineValue)) {
						secondValueText = getAnOptionInEngineDropDown(i).getText();
						getAnOptionInEngineDropDown(i).click();
						break;
					}
				}
				// getAnOptionInEngineDropDown(2).click();
				waitForTextToAppear((By.xpath("//button[@data-di-id='#dropdown__button-engines']")), secondValueText);
				// assertTrue(getEngineDropDownDefault().getText().equals(secondValueText));
				isInitialized();
				System.out.println("Selected this:" + getEngineDropDownDefault().getText());
				String selectedEngineValue = getEngineDropDownDefault().getText();

				if (!defaultEngineValue.equalsIgnoreCase(selectedEngineValue)) {
					System.out.println("Selected value other than default");
					return true;
				}
			} else {
				System.out.println("There are no values to select other than default");
			}
		}
		return false;
	}

	public boolean selectaDifferentEngineInDropDown() throws InterruptedException {

		// Count the number of options
		isInitialized();
		getEngineDropDownDefault().click();
		int numberOfOptionsAvailable = 0;
		String defaultEngineValue = getEngineDropDownDefault().getText();
		if (getEngineDropDownDefault().isDisplayed()) {
			numberOfOptionsAvailable = getNumberofEngineOptionsAvailable().size(); // slow
			System.out.println("NumberOfOptionsAvailable=" + numberOfOptionsAvailable);
			if (numberOfOptionsAvailable > 1) {
				System.out.println("NumberOfOptionsAvailable is > 1");
				String secondValueText = getAnOptionInEngineDropDown(2).getText();
				getAnOptionInEngineDropDown(2).click();
				// String myxpath="//button[@data-di-id='#dropdown__button-engines']";
				waitForTextToAppear((By.xpath(getDropDownLoading())), secondValueText);
				// assertTrue(getEngineDropDownDefault().getText().equals(secondValueText));

				// Thread.sleep(10000);
				isInitialized();
				System.out.println("Selected this:" + getEngineDropDownDefault().getText());
				String selectedEngineValue = getEngineDropDownDefault().getText();

				if (!defaultEngineValue.equalsIgnoreCase(selectedEngineValue)) {
					System.out.println("Selected value other than default");
					return true;
				}
			} else {
				System.out.println("There are no values to select other than default");
			}
		}
		return false;
	}

	public void navigateToToolTip() {
		if (getToolTip().isDisplayed()) {
			getToolTip().click();
		}
	}

	public void verifyfooterpopUp() throws InterruptedException {
		if (footerpopUp().isDisplayed()) {
			closeFooterpopUp().click();
			Thread.sleep(2000);
		}
	}

	public boolean verifyInformationPopUp() {

		String expectedTitle = "PERSONAL CONTRACT PURCHASE";
		if (getinformationPopup().isDisplayed()) {
			getinformationPopup().click();
		}
		if (getinformationPopupTitle().contains(expectedTitle)) {
			getInformationPopupCloseButton().click();
			return true;
		}
		return false;
	}

}
