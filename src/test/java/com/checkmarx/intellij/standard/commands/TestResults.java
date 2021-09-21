package com.checkmarx.intellij.standard.commands;

import com.checkmarx.intellij.Bundle;
import com.checkmarx.intellij.Resource;
import com.checkmarx.intellij.commands.results.ResultGetState;
import com.checkmarx.intellij.commands.results.Results;
import com.checkmarx.intellij.standard.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TestResults extends BaseTest {

    @Test
    public void testGetResults() {
        CompletableFuture<ResultGetState> getFuture = Results.getResults("");
        ResultGetState results = Assertions.assertDoesNotThrow((ThrowingSupplier<ResultGetState>) getFuture::get);
        Assertions.assertNotEquals(results.getMessage(), Bundle.message(Resource.LATEST_SCAN_ERROR));
        Assertions.assertTrue(Objects.equals(results.getMessage(), Bundle.message(Resource.NO_RESULTS))
                              || results.getResultOutput() != Results.emptyResults);
        Assertions.assertEquals("", results.getScanIdFieldValue());
        Assertions.assertDoesNotThrow(() -> UUID.fromString(results.getScanId()));
    }
}
