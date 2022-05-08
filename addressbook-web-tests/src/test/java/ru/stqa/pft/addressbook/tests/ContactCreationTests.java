package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().createContact(new ContactData("Alex", "Shmalex",
                    "Home sweet home", "3223322",
                    "alex@shmalex.com", "test1"));
    app.getNavigationHelper().gotoHomePage();

  }

}
