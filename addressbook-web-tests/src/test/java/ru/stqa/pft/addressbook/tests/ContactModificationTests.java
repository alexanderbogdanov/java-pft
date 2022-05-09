package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoGroupsPage();
      if (!app.getGroupHelper().isThereAGroup()) {
        app.getNavigationHelper().gotoGroupsPage();
        app.getGroupHelper().createGroup(new GroupData("test1", null, null));
      }
      app.getNavigationHelper().gotoHomePage();
      app.getContactHelper().createContact(new ContactData("Alex", "Shmalex",
              "Home sweet home", "3223322",
              "alex@shmalex.com", "test1"));
    }
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Johnny", "Doe",
            "Antalya", "1234567", "joe@doe.com", "test1"), false );
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().gotoHomePage();
  }
}
