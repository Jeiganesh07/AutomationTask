package com.connect_group.tests;

import com.connect_group.project_config.ScreenShotService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.FileInputStream;import java.io.IOException;import java.util.Properties;

import java.util.logging.Logger;

public class BaseTest {

  protected Logger LOGGER = Logger.getLogger(BaseTest.class.getName());

  @RegisterExtension
  protected ScreenShotService screenShotService = new ScreenShotService(this::getDriver);

  protected static WebDriver driver;

  private WebDriver getDriver() {
    return driver;
  }

  protected void open(String url) {
    getDriver().get(url);
    LOGGER.info("Opening URL - " + url);
  }

  @BeforeAll
  public static void beforeSuite() {
	  
    WebDriverManager.chromedriver().setup();

    driver = new ChromeDriver(getChromeOptions());
    driver.manage().window().maximize();
  }

  @AfterEach
  public void cleanUp() {
    driver.manage().deleteAllCookies();
  }

  @AfterAll
  public static void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  protected static ChromeOptions getChromeOptions() {
    return new ChromeOptions().setHeadless(false);
  }
}