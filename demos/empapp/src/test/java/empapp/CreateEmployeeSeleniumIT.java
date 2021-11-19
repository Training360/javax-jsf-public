package empapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateEmployeeSeleniumIT {

    private WebDriver webDriver;

    @Before
    public void init() {
        webDriver = new FirefoxDriver();
    }

    @After
    public void destroy() {
        webDriver.close();
    }

    @Test
    public void testCreateEmployee() {
        webDriver.get("http://localhost:8080/empapp");

        webDriver.findElement(By.linkText("Create employee")).click();
        webDriver.findElement(By.id("create-form:name-input")).click();
        String name = "John Doe" + System.currentTimeMillis();
        webDriver.findElement(By.id("create-form:name-input")).sendKeys(name);
        webDriver.findElement(By.id("create-form:save-button")).click();

        String message = webDriver.findElement(By.xpath("/html/body/ul/li")).getText();
        assertEquals("Employee has created", message);

        List<WebElement> elements = webDriver.findElements(By.xpath("//tr/td[2]"));
        assertTrue(elements.stream().map(WebElement::getText).anyMatch(s -> s.equals(name)));
    }
}
