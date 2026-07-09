package com.ust.sdet.tests;

import com.ust.sdet.support.Config;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import static org.junit.jupiter.api.Assertions.*;
//search -> add to cart -> verify -> sort and keyboard controls
public class SmokeTest {
    static WebDriver driver;
    @BeforeAll
    static void setUp(){
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Product catalog loads in a real Chrome session")
    void catalogLoads() {
        driver.get(Config.catalogUrl());
        By catalogHeading = By.cssSelector("[data-test='catalog-title']");

        assertAll(
                    () -> assertTrue(driver.getTitle().contains("Catalog")),
                    () -> assertTrue(driver.findElement(catalogHeading).isDisplayed()),
                    () -> assertEquals("Product Catalog", driver.findElement(catalogHeading).getText())
        );
    }
}
