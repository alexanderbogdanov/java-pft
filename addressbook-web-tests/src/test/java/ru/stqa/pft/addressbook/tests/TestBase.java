package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

import java.io.IOException;



public class TestBase {

  protected static final ApplicationManager app;

  static {
    try {
      app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


//  protected static final ApplicationManager app = new ApplicationManager(CHROME);

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

}
