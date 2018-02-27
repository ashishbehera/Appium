package amazon;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;


import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class WebAppTest {

    private static AndroidDriver driver;
    ExtentReports extent;
    ExtentTest logger;
    AppiumDriverLocalService appiumService;
    String appiumServiceUrl;

    @BeforeTest
    public  void start() throws MalformedURLException, InterruptedException
    {
        appiumService = AppiumDriverLocalService.buildDefaultService();
/*        appiumService = AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder().usingAnyFreePort()
                        .usingDriverExecutable(new File("C:/Program Files (x86)/Appium/node.exe"))
                        .withAppiumJS(new File("C:/Program Files (x86)/Appium/node_modules/appium/bin/appium.js")));*/
        appiumService.start();
        appiumServiceUrl = appiumService.getUrl().toString();
        System.out.println("Appium Service Address : - "+ appiumServiceUrl);
        extent = new ExtentReports (System.getProperty("user.dir") +"/target/surefire-reports/STMExtentReport.html", true);
        extent
                .addSystemInfo("Host Name", "Amazon App")
                .addSystemInfo("Environment", "Automation Testing")
                .addSystemInfo("User Name", "Ashish Kumar Behera");
        //loading the external xml file (i.e., extent-config.xml) which was placed under the base directory
        //You could find the xml file below. Create xml file in your project and copy past the code mentioned below
        extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));





        //DesiredCapabilities capabilities = new DesiredCapabilities();
        DesiredCapabilities capabilities = new DesiredCapabilities().chrome();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "MOTO_E_4_PLUS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1");
       // capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability("noReset","true");
      //  capabilities.setCapability("automationName","selendroid");
        //WebView.setWebContentsDebuggingEnabled(true);
      /*  ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("androidPackage", "com.android.chrome");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        File chromeDriverFile = new File(System.getProperty("user.dir")+"\\Resources\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());*/
        driver = new AndroidDriver(new URL(appiumServiceUrl), capabilities);
        Thread.sleep(10000);



    }

    @Test
    public void WebAppDemo()throws MalformedURLException, InterruptedException
    {
        logger=extent.startTest("WebAppDemo");
       // WebView.setWebContentsDebuggingEnabled(true);
// WebView.setWebContentsDebuggingEnabled(true);

        driver.get("http://www.facebook.com");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        logger.log(LogStatus.PASS, "URL is opened");

        driver.findElement(By.name("email")).sendKeys("ash110uce@gmail.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        logger.log(LogStatus.PASS, "Email Value is Entered");

   /*     Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName);
            if (contextName.contains("WEBVIEW")) {
                driver.context(contextName);
                System.out.println(contextName);
                Thread.sleep(1500);
                driver.findElementByLinkText("My Account").click();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                logger.log(LogStatus.PASS, "Search Criteria is Entered");
            }
        }
*/
    /*            //driver.context("NATIVE_APP"); // set context to NATIVE
        driver.context("WEBVIEW_MyApp"); // set context to WEBVIEW
        driver.get("http://www.store.demoqa.com");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        logger.log(LogStatus.PASS, "URL is opened");
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println("Context Name is " +contextName);
            if (contextName.contains("WEBVIEW")){
                driver.context(contextName);
                //driver.findElementById("menu_button").click();
                driver.findElementByLinkText("My Account").click();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                logger.log(LogStatus.PASS, "Search Criteria is Entered");
            }
        }*/


    }
    @AfterMethod
    public void getResult(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
            logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
        }else if(result.getStatus() == ITestResult.SKIP){
            logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
        }
        // ending test
        //endTest(logger) : It ends the current test and prepares to create HTML report
        extent.endTest(logger);
    }
    @AfterTest
    public void endReport(){


        // writing everything to document
        //flush() - to write or update test information to your report.
        extent.flush();
        //Call close() at the very end of your session to clear all resources.
        //If any of your test ended abruptly causing any side-affects (not all logs sent to ExtentReports, information missing), this method will ensure that the test is still appended to the report with a warning message.
        //You should call close() only once, at the very end (in @AfterSuite for example) as it closes the underlying stream.
        //Once this method is called, calling any Extent method will throw an error.
        //close() - To close all the operation
        extent.close();
        driver.quit();
        appiumService.stop();
    }







}
