package com.connect_group.tests.helloworld;

import com.connect_group.pages.helloworld.ReceiptPage;
import com.connect_group.pages.helloworld.SignUpPage;
import com.connect_group.tests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HelloWorldTest extends BaseTest {

  private SignUpPage signUpPage;
  private ReceiptPage receiptPage;

  @BeforeEach
  void navigateToURL() {
    signUpPage = new SignUpPage(driver);
    receiptPage = new ReceiptPage(driver);
    open("http://www.kimschiller.com/page-object-pattern-tutorial/index.html");
  }

  @Test
  void helloWorldTest() throws InterruptedException {
	  Thread.sleep(5000);
    assertTrue(signUpPage.isInitialized());

    signUpPage.enterName("First", "Last");
    signUpPage.enterAddress("123 Street", "12345");

    receiptPage = signUpPage.submit();
    assertTrue(receiptPage.isInitialized());

    assertEquals("Thank you!", receiptPage.confirmationHeader());
    screenShotService.takeScreenshot("_success");
  }
}