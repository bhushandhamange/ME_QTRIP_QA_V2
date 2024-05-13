package qtriptest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportSingleton {

    private static ExtentReports extentReports;

    private ReportSingleton() {
        // Private constructor to prevent instantiation
    }

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            extentReports = createExtentReports();
        }
        return extentReports;
    }

    private static ExtentReports createExtentReports() {
        String reportPath = "src/test/extentReport/report.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        // Customize your ExtentSparkReporter as needed

        sparkReporter.config().setReportName("QTrip Regression Suite");
		sparkReporter.config().setDocumentTitle("Regression test for QTrip");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        // Add any additional configurations or settings
        extentReports.setSystemInfo("Tester", "Bhushan Dhamange");

        return extentReports;
    }

    public static synchronized void flushExtentReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
