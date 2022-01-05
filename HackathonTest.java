package Hackathon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Properties;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;



public class HackathonTest extends HackathonReadExcelData
{

HackathonReadExcelData obj = new HackathonReadExcelData();

WebDriver driver;

 String URL, browser;
 
 
 public static ExtentHtmlReporter exthtml;
 public static ExtentReports report;
 public static ExtentTest testlog; 
 private Properties prop;
 

//FUNCTION TO ACCESS EXTERNAL PROPERTIES FILE
@BeforeClass
public void readConfigProperties() throws Exception
{
startReport();
testlog=report.createTest("Test Add");
FileInputStream fis = new FileInputStream("src\\test\\resources\\config.properties");
Properties properties=new Properties( );
properties.load(fis);
browser= properties.getProperty("browser");
URL=properties.getProperty("baseURL");
invokeBrowser(browser, URL);
}

//Creating Extent report

  public void startReport() 
  { prop = new Properties(); 
  try
  {
  prop.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/Config.properties")); 
  } catch (Exception e)
  {
  System.out.println("Error in the Properties File");
  }
  exthtml = new ExtentHtmlReporter( prop.getProperty("reportlocation") + "\\Hackathon_project " + getFileName() + ".html"); 
  report = new ExtentReports();
  report.attachReporter(exthtml);
  report.setSystemInfo("Host Name", "Nandha");
  report.setSystemInfo("Environment", "Test Environment");
  report.setSystemInfo("User Name", "Nandhakumar"); report.setSystemInfo("OS", "Windows 10 ");
  exthtml.config().setDocumentTitle("Hackathon Project");
  exthtml.config().setReportName("Hackathon Project");
   exthtml.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
  }
  
  //To pass the Status as Success to the Report 
  public void logPass(String message) 
  {
  testlog.log(Status.PASS, message);
  System.out.println(message);
  }
  
   //To pass the Status as Information to the Report 
  public void logInfo(String message)
  {
	  testlog.log(Status.INFO, message);
	  System.out.println(message); 
	  }
  
   //To pass the Status as Failure to the report
  public void logFail(String message)
  {
  testlog.log(Status.FAIL, message);
  System.err.println(message);
   }
 
//To generate the FileName as current date and time

  public String getFileName() 
  { Date date = new Date();
  return date.toString().replace(":", "_").toString().replace(" ", "_");
  }
 

//FUNCTION TO INVOKE THE WEB BROWSER
public void invokeBrowser(String browser,String URL) throws Exception
{
switch(browser)
{
case "Firefox":
{
System.setProperty("webdriver.gecko.driver","src\\test\\resources\\geckodriver.exe" );
driver=new FirefoxDriver();
break;
}
case "Edge":
{
System.setProperty("webdriver.edge.driver","src\\test\\resources\\msedgedriver.exe" );
driver=new EdgeDriver();
break;
}
case "Chrome":
{
System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
driver=new ChromeDriver();
}

}
driver.manage().window().maximize();
getUrl(URL);
}
//FUNCTION TO OPEN THE URL
public void getUrl(String URL) throws Exception
{
try {
	driver.get(URL);
	logPass("Browser Opened");
	takeScreenshot();
} catch (Exception e) {
	
	logFail(e.getMessage());
}
}

public void VerifyTitle(String expectedTitle)
{
  String Title=driver.getTitle();
  if(Title.contains("ZigWheels"))
  {
	  logPass("Title verified");
  }
 
}


public void takeScreenshot() throws IOException {
	

	try {
	File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd-hh-mm-ss");
	String d = df.format(date);
	FileUtils.copyFile(ss, new File("target\\" + d + ".png"));
	} catch (WebDriverException e) {
	
	logFail(e.getMessage());
	}
	}

//FUNCTION TO CLOSE THE BROWSER
@AfterClass
public void CloseBrowser()
{
try {
	driver.quit();
	logPass("Browser closed successfully");
	report.flush();
} catch (Exception e) {
	
	logFail("Exception:" + e.getMessage());
}
}


}



























































