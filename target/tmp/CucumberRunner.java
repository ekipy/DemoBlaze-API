
import io.cucumber.core.cli.Main;

public class CucumberRunner {
  public static void main(String[] args) {
    System.out.println("Cucumber feature: DEMOBLAZETEST/src/test/resources/features/homepage.feature");
    String[] cucumberArgs = new String[] {
      "DEMOBLAZETEST/src/test/resources/features/homepage.feature",
      "--glue", "demoblaze.steps",
      "--plugin", "pretty"
    };
    Main.main(cucumberArgs);
  }
}