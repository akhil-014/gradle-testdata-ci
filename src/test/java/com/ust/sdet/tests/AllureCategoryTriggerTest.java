package com.ust.sdet.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Framework Hardening")
@Feature("Allure Categories Demo")
public class AllureCategoryTriggerTest {

    @Test
    @Story("Flaky Category")
    @Severity(SeverityLevel.NORMAL)
    void shouldAppearInFlakyCategory() {
        List<String> signals = List.of("status", "trend", "category split", "environment");
        assertFalse(signals.contains("trend"), "timeout");
    }

    @Test
    @Story("Product Defect Category")
    @Severity(SeverityLevel.CRITICAL)
    void shouldAppearInProductDefectCategory() {
        fail("Business validation failed for order processing");
    }

    @Test
    @Story("Test Defect Category")
    @Severity(SeverityLevel.CRITICAL)
    void shouldAppearInBrokenCategory() {
        throw new NullPointerException("Framework setup failure");
    }
}