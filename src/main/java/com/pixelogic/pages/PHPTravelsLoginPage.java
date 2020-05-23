package com.pixelogic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;

public class PHPTravelsLoginPage {
    // variables
    private WebDriver driver;
    private static String url = System.getProperty("gui.baseURL") + "login";

    public static String getUrl() {
	return url;
    }

    // locators
    private By userName = By.name("username");
    private By password = By.name("password");
    private By loginButton = By.xpath("//button[text()='Login']");

    // constructor
    public PHPTravelsLoginPage(ThreadLocal<WebDriver> driver) {
	this.driver = driver.get();
    }

    // actions
    public PHPTravelsLoginPage navigate() {
	BrowserActions.navigateToURL(driver, url);
	return this;
    }

    public void login(String[] login) {

	ElementActions.type(driver, userName, login[0]);
	ElementActions.typeSecure(driver, password, login[1]);
	ElementActions.click(driver, loginButton);
    }
}
