package Hackathon;

import java.util.List;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

@Test(priority = 2)
public class UsedCars extends HackathonTest {

	//@Test(priority = 2)
	public void usedCars() throws InterruptedException
	{
		testlog=report.createTest("Test Used Cars");
		
	try {
		Actions action1 = new Actions(driver);
		WebElement usedCars = driver.findElement(HackathonPOM.usedCarsHover);
		action1.moveToElement(usedCars).perform();
		logPass("Hover on usedCars - DONE");
		
		driver.findElement(HackathonPOM.city).click();
		
		logPass("Chennai clicked - DONE");
		VerifyTitle("Used Cars in Chennai - Certified Second Hand Cars for Sale @ ZigWheels");
		testlog.log(Status.INFO,(driver.findElement(HackathonPOM.popularfilter).getText()));
		List<WebElement> usedCarsNames = driver.findElements(HackathonPOM.nameOfUsedCars);
		List<WebElement> usedCarsPrice = driver.findElements(HackathonPOM.priceOfUsedCars);
		
		testlog.log(Status.INFO,("The most popular cars used in Chennai are listed below:"));
		takeScreenshot();
		for(int j = 0; j<3;j++)
		{
		testlog.log(Status.INFO,("Name of the car : " + usedCarsNames.get(j).getText()));
		testlog.log(Status.INFO,("Price of the car : " + usedCarsPrice.get(j).getText()));
		}
		takeScreenshot();
		driver.navigate().to(URL);
		logPass("navigation to homepage - DONE");
	} catch (Exception e) {
		logFail(e.getMessage());
	}

	}
}
