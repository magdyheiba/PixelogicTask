package tests.gui.pixelogicPages;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.browser.BrowserFactory;
import com.shaft.tools.io.ExcelFileManager;

import io.qameta.allure.Description;
import pixelogicPages.PHPTravelsLoginPage;
import pixelogicPages.PHPTravelsRegisterPage;

public class Pixelogic_Test {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ThreadLocal<ExcelFileManager> pixelogic = new ThreadLocal<>();

    private String[] register;
    private String[] login;

    @Test(description = "Register - Insert", groups = { "gui" })
    @Description("When I enter Register page, then all data should be saved successfully and a user should have an account")
    public void createNewAccount() {
	new PHPTravelsRegisterPage(driver).navigate().inputRegisterFields(register).verifyRegisterFields(register)
		.clickOnRegisterButton();

    }

    @Test(description = "Login - Insert", dependsOnMethods = { "createNewAccount" }, groups = { "gui" })
    @Description("When user registers with a new account , and login with the same account , then he should be able to login successfully")
    public void login() {
	new PHPTravelsLoginPage(driver).navigate().login(login);
    }

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

    @BeforeClass
    public void beforeClass() {
	pixelogic.set(new ExcelFileManager(System.getProperty("testDataFolderPath") + "Pixelogic.xlsx"));

	// read test data
	register = readRegisterTestData();
	login = readLoginTestData();

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
