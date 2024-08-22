package empapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateEmployeeSeleniumIT {

    WebDriver webDriver;

    @BeforeEach
    void init() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        webDriver = new ChromeDriver(options);
    }

    @AfterEach
    void destroy() {
        webDriver.close();
    }

    @Test
    void testCreateEmployee() {
        webDriver.get("http://localhost:8080/empapp");

        webDriver.findElement(By.linkText("English")).click();

        webDriver.findElement(By.linkText("Create employee")).click();
        webDriver.findElement(By.id("create-form:name-input")).click();
        String name = "John Doe" + System.currentTimeMillis();
        webDriver.findElement(By.id("create-form:name-input")).sendKeys(name);
        webDriver.findElement(By.id("create-form:save-button")).click();

        // 2. screen
        webDriver.findElement(By.id("create-form:save-button")).click();

        String message = webDriver.findElement(By.cssSelector("body > div > ul > li")).getText();
        assertEquals("Employee has been created", message);

        List<WebElement> elements = webDriver.findElements(By.cssSelector("tr > td:nth-child(2)"));
        assertTrue(elements.stream().map(WebElement::getText)
                .peek(System.out::println)
                .anyMatch(s -> s.equals(name)));
    }
}
