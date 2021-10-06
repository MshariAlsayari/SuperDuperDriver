package com.udacity.jwdnd.course1.cloudstorage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	protected WebDriver driver;
	@LocalServerPort
	private int port;



	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void test_unauthorized_user() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	protected HomePage signUpAndLogin() {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPageM signupPageM = new SignupPageM(driver);
		signupPageM.setFirstName("John");
		signupPageM.setLastName("Lennon");
		signupPageM.setUserName("lennon");
		signupPageM.setPassword("julia");
		signupPageM.signUp();
		driver.get("http://localhost:" + this.port + "/login");
		LoginPageM loginPageM = new LoginPageM(driver);
		loginPageM.setUserName("lennon");
		loginPageM.setPassword("julia");
		loginPageM.login();

		return new HomePage(driver);
	}

}
