package com.ust.sdet.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Framework Hardening")
@Feature("Reporting Insights")
@Owner("SDET Trainer")
public class AllureReportInsightTest {

    @Test
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Categories split flaky, test and product failures so each bucket has an error")
    void categoriesSpecificFlakyRuleBeforeGenericBuckets() throws IOException
    {
        String categories  = Files.readString(Path.of("src/test/resources/allure/categories.json"));

        int flakyIndex = categories.indexOf("\"Flaky tests\"");
        int testDefectIndex = categories.indexOf("\"Test defects {broken}\"");
        int productDefectIndex = categories.indexOf("\"Product defects\"");

        assertTrue(flakyIndex>=0,"Flaky category must exist");
        assertTrue(testDefectIndex > flakyIndex,"Specific flaky rule must run before gen");
        assertTrue(productDefectIndex > flakyIndex ,"Specific flaky rule must fun before");
        assertFalse(categories.contains("\"flaky\": true"));
    }

    @Test
    @Story("Environment metadata")
    @Severity(SeverityLevel.NORMAL)
    @Description("Environment metadata answers which browser, base URL and build produced the report.")
    void environmentTemplateCarriesRunContext() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/test/resources/allure/environment.properties"));

        assertTrue(lines.stream().anyMatch(line -> line.startsWith("Browser=")));
        assertTrue(lines.stream().anyMatch(line -> line.startsWith("BaseURL=")));
        assertTrue(lines.stream().anyMatch(line -> line.startsWith("Build=")));
        assertFalse(lines.stream().anyMatch(line -> line.startsWith("OS=")));
    }

    @Test
    @Story("Executive overview")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The leadership view is the overview page once categories, history, severity and environment")
    void executiveViewNeedsFourSignals() {
        List<String> signals = List.of("status", "trend", "category split", "environment");

        assertEquals(4, signals.size());
        assertTrue(signals.contains("trend"));
        assertFalse(signals.contains("category split"));

    }

}
