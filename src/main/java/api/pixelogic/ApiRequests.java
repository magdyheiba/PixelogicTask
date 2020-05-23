package api.pixelogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.ParametersType;
import com.shaft.api.RestActions.RequestType;
import com.shaft.tools.io.ReportManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

// Register API and the response of it you can check the response at Allure Report .
// Note : just edit the parameters in order to register new account.
@Test
public class ApiRequests {
    static String serviceURI = System.getProperty("api.baseURI");
    static String authServiceURI = System.getProperty("authapi.baseURI");

    public static void register() {
	List<List<Object>> formData = new ArrayList<List<Object>>();
	formData.add(Arrays.asList("firstname", "mag"));
	formData.add(Arrays.asList("lastname", "hei"));
	formData.add(Arrays.asList("phone", "122354"));
	formData.add(Arrays.asList("email", "mm@mmi.com"));
	formData.add(Arrays.asList("password", "F@11ki"));
	formData.add(Arrays.asList("confirmpassword", "F@11ki"));
	Response auth = new RestActions(authServiceURI).addHeaderVariable("content-type", "application/json")
		.performRequest(RequestType.POST, 200, "account/signup", formData, ParametersType.FORM,
			ContentType.URLENC);
	ReportManager.logDiscrete("The Response is " + auth);
    }
}
