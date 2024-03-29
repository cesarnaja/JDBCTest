package runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"html:target/cucumber"},
        features = "src/test/resources/features",
        glue = "steps",
        tags = "@smokesuite",
        dryRun = false
)
public class Runner {
}
