package com.Pages;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest {

	@AndroidFindBy(accessibility = "test-Username")
	private MobileElement usernameTxtFld;

	@AndroidFindBy(accessibility = "test-Password")
	private MobileElement passwordTxtFld;

	@AndroidFindBy(accessibility = "test-LOGIN")
	private MobileElement loginBtn;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
	private MobileElement errTxt;

	public LoginPage enterUserName(String username) {
		clear(usernameTxtFld);
		sendKeys(usernameTxtFld, username);
		return this;
	}

	public LoginPage enterPassword(String password) {
		clear(passwordTxtFld);
		sendKeys(passwordTxtFld, password);
		return this;
	}

	public ProductsPage pressLoginBtn() {
		click(loginBtn );
		return new ProductsPage();
	}

	public ProductsPage login(String username, String password) {
		enterUserName(username);
		enterPassword(password);
		return pressLoginBtn();
	}

	public String getErrTxt() {
		String err = getText(errTxt);
		return err;
	}

}
