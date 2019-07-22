package com.connect_group.tests.helloWorld;

import com.connect_group.BaseTest;

import com.connect_group.pages.helloWorld.ReceiptPage;
import com.connect_group.pages.helloWorld.SignUpPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorld extends BaseTest {

  @BeforeEach
  void navigateToURL() {
    open("https://www.kimschiller.com/page-object-pattern-tutorial/index.html");
  }

  @Test
  void helloWorldTest() {
    SignUpPage signUpPage = new SignUpPage(driver);
    assertTrue(signUpPage.isInitialized());

    signUpPage.enterName("First", "Last");
    signUpPage.enterAddress("123 Street", "12345");

    ReceiptPage receiptPage = signUpPage.submit();
    assertTrue(receiptPage.isInitialized());

    assertEquals("Thank you!", receiptPage.confirmationHeader());
    screenShotService.takeScreenshot("_success");
  }
}