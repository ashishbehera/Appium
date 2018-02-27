package amazon;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class NativeAppTest {

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


        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/Apps/Amazon/");
        File app = new File(appDir, "in.amazon.mShop.android.shopping_2017-09-18.apk");


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0.1");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
        capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
        capabilities.setCapability("noReset","true");
        driver = new AndroidDriver(new URL(appiumServiceUrl), capabilities);
        Thread.sleep(30000);


    }

    @Test
    public void NativeAppDemo()throws MalformedURLException, InterruptedException
    {
        logger=extent.startTest("NativeAppDemo");
    /*    Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName);
            if (contextName.contains("NATIVE_APP")){
                driver.context(contextName);
                System.out.println(contextName);
              //  driver.findElement(By.xpath("//html/body/div/section/form/div[1]/label/input")).sendKeys("vijay0850");
                driver.findElementById("in.amazon.mShop.android.shopping:id/web_home_shop_by_department_label").click();
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                logger.log(LogStatus.PASS, "Shop By Category is clicked");


            }

        }*/

        driver.findElementById("in.amazon.mShop.android.shopping:id/web_home_shop_by_department_label").click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        logger.log(LogStatus.PASS, "Shop By Category is clicked");

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




   /* public static void main(String[] args) throws MalformedURLException, InterruptedException {

        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/Apps/Amazon/");
        File app = new File(appDir, "in.amazon.mShop.android.shopping_2017-09-18.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("deviceName", "Redmi");
        capabilities.setCapability("platformVersion", "6.0.1");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
        capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");

        try {

            driver = new AndroidDriver(new URL("http://127.0.0.1:5723/wd/hub"), capabilities);
            //driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
            //driver.switchTo().window("NATIVE_APP");
            driver.findElementById("web_home_shop_by_department_label").click();
            Thread.sleep(10000);


       *//* WebElement element = driver.findElement(By.id("in.amazon.mShop.android.shopping:id/skip_sign_in_button"));
        element.click();*//*
      *//*  WebElement element = driver.findElementByXPath("//*[@id='skip_sign_in_button']");
        element.click();*//*
            //driver.findElement(By.xpath("//*[@text='Skip sign in']")).click();
            //new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='search_edit_text']")));
            driver.findElement(By.xpath("//*[@id='web_home_shop_by_department_label']")).click();

            //driver.findElementByLinkText("Skip sign in").click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.quit();
        }catch(Exception ex)
        {
            System.out.println("Error is: "+ ex.getMessage());
            driver.quit();

        }finally {

        }

    }*/


}
