package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@CucumberOptions(features="D:\\SeleniumWorkspace\\APIBDDFrameworkRShetty\\src\\test\\java\\features",plugin = "json:target/jsonReports/cucumber-report.json",
glue= {"stepsDefinations"}) //tags= "@DeletePlace")

@RunWith(Cucumber.class)
public class TestRunner {

}
