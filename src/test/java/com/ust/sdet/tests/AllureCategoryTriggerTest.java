package com.ust.sdet.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@Epic("Framework Hardening")
@Feature("Allure Categories Demo")
public class AllureCategoryTriggerTest {

    @Test
    @Story("Flaky Category")
    @Severity(SeverityLevel.NORMAL)
    void shouldAppearInFlakyCategory() {
        fail("timeout while waiting for element to become clickable");
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