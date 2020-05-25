package tests.gui.pixelogicPages;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pixelogic.pages.PHPTravelsLoginPage;
import com.pixelogic.pages.PHPTravelsRegisterPage;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.browser.BrowserFactory;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.tools.io.ReportManager;
import com.shaft.validation.Assertions;
import com.shaft.validation.Assertions.AssertionComparisonType;
import com.shaft.validation.Assertions.AssertionType;

import io.qameta.allure.Description;

public class Pixelogic_Test {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ThreadLocal<ExcelFileManager> pixelogic = new ThreadLocal<>();

    private String[] register;
    private String[] login;
    private String[] result;

    // Test Cases
    @Test(description = "Register - Insert", groups = { "gui" })
    @Description("When I enter Register page, then all data should be saved successfully and a user should have an account")
    public void createNewAccount() {
	new PHPTravelsRegisterPage(driver).navigate().inputRegisterFields(register).verifyRegisterFields(register)
		.clickOnRegisterButton();
	String ExpectedResult = new PHPTravelsRegisterPage(driver).checkExpectedName(result);
	String ActualResult = new PHPTravelsRegisterPage(driver).checkTheNameOfRegisteredUser();
	ReportManager.logDiscrete("Expected Result is " + ExpectedResult);
	ReportManager.logDiscrete("Actual Result is " + ActualResult);
	Assertions.assertEquals(ExpectedResult, ActualResult, AssertionComparisonType.CONTAINS, AssertionType.POSITIVE);

    }

    @Test(description = "Login - Insert", dependsOnMethods = { "createNewAccount" }, groups = { "gui" })
    @Description("When user registers with a new account , and login with the same credentials , then he should be able to login successfully")
    public void login() {
	new PHPTravelsLoginPage(driver).navigate().login(login);
	String ExpectedResult = new PHPTravelsRegisterPage(driver).checkExpectedName(result);
	String ActualResult = new PHPTravelsRegisterPage(driver).checkTheNameOfRegisteredUser();
	ReportManager.logDiscrete("Expected Result is " + ExpectedResult);
	ReportManager.logDiscrete("Actual Result is " + ActualResult);
	Assertions.assertEquals(ExpectedResult, ActualResult, AssertionComparisonType.CONTAINS, AssertionType.POSITIVE);
    }

    // Read from Excel Methods
    private String[] readRegisterTestData() {
	ArrayList<String> Register = new ArrayList<String>();
	String colName = "Data1";
	String sheetName = "Register";
	Register.add(pixelogic.get().getCellData(sheetName, "First Name", colName));
	Register.add(pixelogic.get().getCellData(sheetName, "Last Name", colName));
	Register.add(pixelogic.get().getCellData(sheetName, "Mobile Number", colName));
	Register.add(pixelogic.get().getCellData(sheetName, "Email", colName));
	Register.add(pixelogic.get().getCellData(sheetName, "Password", colName));
	Register.add(pixelogic.get().getCellData(sheetName, "Confirm Password", colName));
	return Register.toArray(new String[0]);

    }

    private String[] readLoginTestData() {
	ArrayList<String> Login = new ArrayList<String>();
	String colName = "Data1";
	String sheetName = "Register";
	Login.add(pixelogic.get().getCellData(sheetName, "Email", colName));
	Login.add(pixelogic.get().getCellData(sheetName, "Password", colName));
	return Login.toArray(new String[0]);
    }

    private String[] readResultTestData() {
	ArrayList<String> result = new ArrayList<String>();
	String colName = "Data1";
	String sheetName = "Register";
	result.add(pixelogic.get().getCellData(sheetName, "First Name", colName));
	result.add(pixelogic.get().getCellData(sheetName, "Last Name", colName));
	return result.toArray(new String[0]);
    }

    @BeforeClass
    public void beforeClass() {
	pixelogic.set(new ExcelFileManager(System.getProperty("testDataFolderPath") + "Pixelogic.xlsx"));

	// read test data
	register = readRegisterTestData();
	login = readLoginTestData();
	result = readResultTestData();

    }

    @BeforeMethod(onlyForGroups = { "gui" })
    public void beforeMethod() {
	driver.set(BrowserFactory.getBrowser());
    }

    @AfterMethod(onlyForGroups = { "gui" })
    public void afterMethod() {
	BrowserActions.closeCurrentWindow(driver.get());
    }

}
