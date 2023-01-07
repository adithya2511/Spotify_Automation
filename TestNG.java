package spotify.automation;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNG {
	@Test
	public void NormalTestCase() {
		System.out.println("***********this is normal test case method***********");
	}
	@BeforeMethod
	public void Testcase1() {
		System.out.println("this is @BeforeMethod method");
	}
	@AfterMethod
	public void Testcase2() {
		System.out.println("this is @AfterMethod method");
	}
	@BeforeClass
	public void Testcase3() {
		System.out.println("this is @BeforeClassmethod");
	}
	@AfterClass
	public void Testcase4() {
		System.out.println("this is @AfterClass method");
	}
	@BeforeSuite
	public void Testcase5() {
		System.out.println("this is @BeforeSuite method");
	}
	@AfterSuite
	public void Testcase6() {
		System.out.println("this is @AfterSuite method");
	}
	@BeforeTest
	public void Testcase7() {
		System.out.println("this is @BeforeTest method");
	}
	@AfterTest
	public void Testcase8() {
		System.out.println("this is @AfterTest method");
	}
}
