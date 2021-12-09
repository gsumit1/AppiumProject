package com.qa;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseTest {

	@SuppressWarnings("rawtypes")
	public static AppiumDriver driver;
	protected static Properties prop;
	InputStream inputStream;

	public BaseTest() {

		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@SuppressWarnings("rawtypes")
	@BeforeSuite
	public void setup() {
		prop = new Properties();

		inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");

		try {
			prop.load(inputStream);
			DesiredCapabilities caps = new DesiredCapabilities();

			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_4");
			caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("androidAutomationName"));
			URL appURL = getClass().getClassLoader().getResource(prop.getProperty("androidAppLocation"));
			caps.setCapability(MobileCapabilityType.APP, "/Users/riyaanghosh/eclipse-workspace/MyTDDProject/src/test/resources/app/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
			caps.setCapability("appPackage", prop.getProperty("androidAppPackage"));
			caps.setCapability("appActivity", prop.getProperty("androidAppActivity"));
			URL url = new URL(prop.getProperty("appiumURL"));
			driver = new AndroidDriver(url, caps);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void waitForVisibility(MobileElement e) {
		WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void clear(MobileElement e) {
		waitForVisibility(e);
		e.clear();
	}

	public void click(MobileElement e) {
		waitForVisibility(e);
		e.click();
	}

	public void sendKeys(MobileElement e, String txt) {
		waitForVisibility(e);
		e.sendKeys(txt);
	}

	public String getAttribute(MobileElement e, String attribute) {
		waitForVisibility(e);
		return e.getAttribute(attribute);
	}
	
	 public String getText(MobileElement e) {
		  String txt = null;
		  txt = getAttribute(e, "text");
		  return txt;
	  }
	 
	  public MobileElement scrollToElement() {	  
			return (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
							+ "new UiSelector().description(\"test-Price\"));");
	  }
	  
	  public void closeApp() {
		  ((InteractsWithApps) driver).closeApp();
	  }
	  
	  public void launchApp() {
		  ((InteractsWithApps) driver).launchApp();
	  }

	@AfterSuite
	public void tearDown() {

	}

}
