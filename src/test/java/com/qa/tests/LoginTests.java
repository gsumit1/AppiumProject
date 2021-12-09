package com.qa.tests;

import com.Pages.LoginPage;
import com.Pages.ProductsPage;
import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.InputStream;
import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;

	@BeforeClass
	public void beforeClass() throws Exception {
		closeApp();
		launchApp();
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println("\n" + "****** starting test:" + m.getName() + "******" + "\n");
		loginPage = new LoginPage();
	}

	@AfterMethod
	public void afterMethod() {
	}

	@Test
	public void invalidUserName() {
		loginPage.enterUserName("invalidusername");
		loginPage.enterPassword("secret_sauce");
		loginPage.pressLoginBtn();

		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = "err_invalid_username_or_password";

		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	}

	@Test
	public void invalidPassword() {
		loginPage.enterUserName("standard_user");
		loginPage.enterPassword("invalidpassword");
		loginPage.pressLoginBtn();

		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = "err_invalid_username_or_password";

		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	}

	@Test
	public void successfulLogin() {
		loginPage.enterUserName("standard_user");
		loginPage.enterPassword("secret_sauce");
		productsPage = loginPage.pressLoginBtn();

		String actualProductTitle = productsPage.getTitle();
		String expectedProductTitle = "product_title";

		Assert.assertEquals(actualProductTitle, expectedProductTitle);
	}
}
