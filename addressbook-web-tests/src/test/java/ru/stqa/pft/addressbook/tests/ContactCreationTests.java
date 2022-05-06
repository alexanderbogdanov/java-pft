package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.initContactCreation();
    app.fillContactForm(new ContactData("Alex", "Shmalex",
            "Home sweet home", "3223322", "alex@shmalex.com"));
    app.submitContactCreation();
    app.gotoHomePage();

  }

}
