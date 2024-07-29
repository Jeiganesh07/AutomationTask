package com.connect_group.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class BasePage {
  protected WebDriver driver;
  private WebDriverWait wait;

  private static final int TIMEOUT = 10;
  private static final int POLLING = 100;

  public BasePage(WebDriver driver) {
    this.driver = driver;

    driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, TIMEOUT, POLLING);

    PageFactory.initElements(driver, this);
  }

  protected WebDriver getDriver() {
    return driver;
  }

  protected <V> V until(Function<? super WebDriver, V> isTrue) {
    return new WebDriverWait(getDriver(), TIMEOUT).until(isTrue);
  }

  protected <V> V until(Function<? super WebDriver, V> isTrue, String messageToPrintOnFailure) {
    return new WebDriverWait(getDriver(), TIMEOUT).withMessage(messageToPrintOnFailure).until(isTrue);
  }

  protected void waitForElementToAppear(By locator) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected void waitForElementToAppear(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  protected void waitForElementToDisappear(By locator) {
    wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  protected void waitForElementToDisappear(WebElement element) {
    wait.until(ExpectedConditions.invisibilityOf(element));
  }

  protected void waitForTextToDisappear(By locator, String text) {
    wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, text)));
  }

  protected void waitForTextToAppear(By locator, String text) {
	    wait.until(ExpectedConditions.textToBe(locator, text));
	  }

  protected Actions actions() {
    return new Actions(getDriver());
  }
}