package com.qa.tests;

import com.Pages.LoginPage;
import com.Pages.ProductsPage;
import com.qa.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginTests extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;
	InputStream details;
	JSONObject loginUsers;

	@BeforeClass
	public void beforeClass() throws Exception {
		
		
		details = new FileInputStream("/Users/riyaanghosh/eclipse-workspace/MyTDDProject/src/test/resources/Data/data.json");
		JSONTokener jsonToken = new JSONTokener(details);
		loginUsers = new JSONObject(jsonToken);
		closeApp();
		launchApp();
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println("\n" + "****** starting test:" + m.getName() + "******" + "\n");
		loginPage = new LoginPage();
	}

	@Test
	public void invalidUserName() {
		loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
		loginPage.pressLoginBtn();

		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = "Username and password do not match any user in this service.";

		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	}

	@Test
	public void invalidPassword() {
		loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		loginPage.pressLoginBtn();

		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = "Username and password do not match any user in this service.";

		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	}

	@Test
	public void successfulLogin() {
		loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		productsPage = loginPage.pressLoginBtn();

		String actualProductTitle = productsPage.getTitle();
		String expectedProductTitle = "PRODUCTS";

		Assert.assertEquals(actualProductTitle, expectedProductTitle);
	}

	@AfterClass
	public void afterClass() throws IOException {
		details.close();
	}

	@AfterMethod
	public void afterMethod() {
	}
}
