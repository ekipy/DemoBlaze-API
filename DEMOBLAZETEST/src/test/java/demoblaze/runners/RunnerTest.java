package demoblaze.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"demoblaze.steps", "demoblaze.util"},
    plugin = {
        "pretty", 
        "html:report/cucumber-reports.html",
        "summary",
        "json:build/cucumber-report.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }
)

public class RunnerTest {}