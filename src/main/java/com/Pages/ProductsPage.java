package com.Pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends BaseTest{
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView") 
	private MobileElement productTitleTxt;
	
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]") 
	private MobileElement SLBTitle;
	
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]") 
	private MobileElement SLBPrice;
	
public String getTitle() {
	String title = getText(productTitleTxt);
	return title;
}

public String getSLBTitle() {
	String title = getText(SLBTitle);
	return title;
}

public String getSLBPrice() {
	String price = getText(SLBPrice);
	return price;
}

public ProductDetailsPage pressSLBTitle() {
	click(SLBTitle);
	return new ProductDetailsPage();
}

}
