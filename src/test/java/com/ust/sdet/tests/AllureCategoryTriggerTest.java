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
        assertAll(
                () -> assertEquals(2, signals.size()),
                () -> { throw new NullPointerException("timeout"); }
        );
    }

    @Test
    @Story("Test Defect Category")
    @Severity(SeverityLevel.CRITICAL)
    void shouldAppearInBrokenCategory() {
        throw new NullPointerException("Framework setup failure");
    }
}