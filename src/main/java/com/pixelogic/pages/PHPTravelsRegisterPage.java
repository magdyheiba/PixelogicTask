package com.pixelogic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.commons.validator.routines.EmailValidator;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;
import com.shaft.validation.Verifications;

public class PHPTravelsRegisterPage {
    // variables
    private WebDriver driver;
    private static String url = System.getProperty("gui.baseURL") + "register";

    public static String getUrl() {
	return url;
    }

    // locators
    private By firstName = By.name("firstname");
    private By lastName = By.name("lastname");
    private By phoneNumber = By.name("phone");
    private By email = By.name("email");
    private By password = By.name("password");
    private By confirmPassword = By.name("confirmpassword");
    private By registerButton = By.xpath("//button[text()=' Sign Up']");

    // constructor
    public PHPTravelsRegisterPage(ThreadLocal<WebDriver> driver) {
	this.driver = driver.get();
    }

    // actions
    public PHPTravelsRegisterPage navigate() {
	BrowserActions.navigateToURL(driver, url);
	return this;
    }

    public PHPTravelsRegisterPage inputRegisterFields(String[] register) {
	ElementActions.type(driver, firstName, register[0]);
	ElementActions.type(driver, lastName, register[1]);
	ElementActions.type(driver, phoneNumber, register[2]);
	ElementActions.type(driver, email, register[3]);
	ElementActions.typeSecure(driver, password, register[4]);
	ElementActions.typeSecure(driver, confirmPassword, register[5]);
	return this;
    }

    public PHPTravelsRegisterPage verifyRegisterFields(String[] register) {
	if (!register[0].isEmpty()) {
	    Verifications.verifyElementAttribute(driver, firstName, "text", register[0]);
	    boolean firstNameIsUpperCase = Character.isUpperCase(register[0].charAt(0));
	    Verifications.verifyTrue(firstNameIsUpperCase, "First name starts with capital letter");
	} else {
	    ReportManager.logDiscrete(" First Name field is empty. ");
	}
	if (!register[1].isEmpty()) {
	    Verifications.verifyElementAttribute(driver, lastName, "text", register[1]);
	    boolean lastNameIsUpperCase = Character.isUpperCase(register[1].charAt(0));
	    Verifications.verifyTrue(lastNameIsUpperCase, "Last name starts with capital letter");
	} else {
	    ReportManager.logDiscrete(" First Name field is empty. ");
	}
	if (!register[2].isEmpty()) {
	    Verifications.verifyElementAttribute(driver, phoneNumber, "text", register[2]);
	    boolean PhoneIsNumbers = register[2].matches("[0-9]+") && register[2].length() > 2;
	    Verifications.verifyTrue(PhoneIsNumbers, "Phone number contains Number.");
	} else {
	    ReportManager.logDiscrete(" Phone number field is empty. ");
	}
	if (!register[3].isEmpty()) {
	    Verifications.verifyElementAttribute(driver, email, "text", register[3]);
	    boolean emailIsValid = EmailValidator.getInstance().isValid(register[3]);
	    Verifications.verifyTrue(emailIsValid, "Email Address is valid.");
	} else {
	    ReportManager.logDiscrete(" Phone number field is empty. ");
	}
	if (!register[4].isEmpty()) {
	    String Patern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
	    boolean passwordIsValid = register[4].matches(Patern);
	    Verifications.verifyTrue(passwordIsValid,
		    "Password must have capital letter, small letter, with a limit of 8 characters");
	} else {
	    ReportManager.logDiscrete(" Password field doesn't have the required format ");
	}
	if (!register[5].isEmpty()) {
	    Boolean confirmPasswordMatchesPassword = register[5].equals(register[4]);
	    Verifications.verifyTrue(confirmPasswordMatchesPassword, "Password matches Confirm Password");

	} else {
	    ReportManager.logDiscrete(" Password doesn't match Confirm Password ");
	}
	return this;
    }

    public void clickOnRegisterButton() {
	ElementActions.click(driver, registerButton);
    }

    public String checkTheNameOfRegisteredUser() {
	By registeredUserName = By.xpath("//h3[contains(@class,'text-align-left')]");
	String registeredUserNameText = ElementActions.getText(driver, registeredUserName);
	return registeredUserNameText;
    }

    public String checkExpectedName(String[] register) {
	String ExpectedName = "Hi, " + "" + register[0] + " " + register[1] + "";
	return ExpectedName;
    }

}
