package base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass
    public void setUp() {
        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("Starting Test Class: {}", this.getClass().getSimpleName());
        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("Test execution timestamp: {}", System.currentTimeMillis());
    }

    @AfterClass
    public void tearDown() {
        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("Completed Test Class: {}", this.getClass().getSimpleName());
        logger.info("═══════════════════════════════════════════════════════════");
    }

    protected void logSection(String sectionName) {
        logger.info("\n");
        logger.info("─────────────────────────────────────────────────────────────");
        logger.info("TEST: {}", sectionName);
        logger.info("─────────────────────────────────────────────────────────────");
    }

    protected void logStep(int stepNumber, String stepDescription) {
        logger.info("Step {}: {}", stepNumber, stepDescription);
    }

    protected void logSuccess(String message) {
        logger.info("✓ SUCCESS: {}", message);
    }

    protected void logFailure(String message) {
        logger.error("✗ FAILURE: {}", message);
    }

    protected void logInfo(String message) {
        logger.info("ℹ INFO: {}", message);
    }
}

