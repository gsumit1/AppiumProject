package com.qa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseTest {

	@SuppressWarnings("rawtypes")
	public static AppiumDriver driver;
	protected static Properties prop;
	InputStream inputStream;
	private static AppiumDriverLocalService server;
	public BaseTest() {

		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@BeforeSuite
	@Parameters({"platformName", "udid", "deviceName"})
	public void setup(String platformName, String udid, String deviceName) throws AppiumServerHasNotBeenStartedLocallyException, Exception {
		prop = new Properties();

		inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
		
		
		server = getAppiumService(); // -> If using Mac, uncomment this statement and comment below statement
		//server = getAppiumServerDefault(); // -> If using Windows, uncomment this statement and comment above statement
		if(!checkIfAppiumServerIsRunnning(4723)) {
			server.start();
			server.clearOutPutStreams(); // -> Comment this if you don't want to see server logs in the console
			System.out.println("Appium server started");
		} else {
			System.out.println("Appium server already running");
		}	

		try {
			prop.load(inputStream);
			DesiredCapabilities caps = new DesiredCapabilities();

			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.UDID, udid);
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("androidAutomationName"));
			URL appURL = getClass().getClassLoader().getResource(prop.getProperty("androidAppLocation"));
			caps.setCapability(MobileCapabilityType.APP,
					"/Users/riyaanghosh/eclipse-workspace/MyTDDProject/src/test/resources/app/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
			caps.setCapability("appPackage", prop.getProperty("androidAppPackage"));
			caps.setCapability("appActivity", prop.getProperty("androidAppActivity"));
			URL url = new URL(prop.getProperty("appiumURL"));
			driver = new AndroidDriver(url, caps);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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
		return (MobileElement) ((FindsByAndroidUIAutomator) driver)
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
						+ ".scrollable(true)).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));");
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
	
	
	
	public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
	    boolean isAppiumServerRunning = false;
	    ServerSocket socket;
	    try {
	        socket = new ServerSocket(port);
	        socket.close();
	    } catch (IOException e) {
	    	System.out.println("1");
	        isAppiumServerRunning = true;
	    } finally {
	        socket = null;
	    }
	    return isAppiumServerRunning;
	}
	
	@AfterSuite (alwaysRun = true)
	public void afterSuite() {
		  if(server.isRunning()){
			  server.stop();
			  System.out.println("Appium server stopped");
		  }
	}

	// for Windows
	public AppiumDriverLocalService getAppiumServerDefault() {
		return AppiumDriverLocalService.buildDefaultService();
	}

	// for Mac. Update the paths as per your Mac setup
	public AppiumDriverLocalService getAppiumService() {
		HashMap<String, String> environment = new HashMap<String, String>();
		environment.put("PATH", "/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin:/Users/riyaanghosh/Library/Android/sdk/tools:/Users/riyaanghosh/Library/Android/sdk/platform-tools:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin" + System.getenv("PATH"));
		environment.put("ANDROID_HOME", "/Users/riyaanghosh/Library/Android/sdk");
		return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File("/usr/local/bin/node"))
				.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
				.usingPort(4723)
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
//				.withArgument(() -> "--allow-insecure","chromedriver_autodownload")
				.withEnvironment(environment)
				.withLogFile(new File("ServerLogs/server.log")));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
