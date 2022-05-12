package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
//    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Alex", "Shmalex",
              "Home sweet home", "3223322",
              "alex@shmalex.com", null));
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().initContactModification(before.size() - 1);

    app.getContactHelper().fillContactForm(new ContactData("Johnny", "Doe",
            "Antalya", "1234567", "joe@doe.com", "test1"), false );
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
  }
}
