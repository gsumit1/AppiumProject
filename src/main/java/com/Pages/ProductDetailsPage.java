package com.Pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends BaseTest {
	TestUtils utils = new TestUtils();

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	private MobileElement SLBTitle;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]"
			+ "")
	private MobileElement SLBTxt;

	@AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
	private MobileElement backToProductsBtn;

	public String getSLBTitle() {
		String title = getText(SLBTitle);
		return title;
	}

	public String getSLBTxt() {
		String txt = getText(SLBTxt);
		return txt;
	}

	/*
	 * public String getSLBPrice() { String price = getText(SLBPrice);
	 * utils.log("price is - " + price); return price; }
	 */

	public String scrollToSLBPriceAndGetSLBPrice() {
		return getText(scrollToElement());
	}

	public ProductsPage pressBackToProductsBtn() {
		click(backToProductsBtn);
		return new ProductsPage();
	}

}
