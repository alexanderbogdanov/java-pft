package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
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
//    app.getNavigationHelper().gotoHomePage();
    int index = before.size()-1;
    ContactData contact = new ContactData(before.get(index).getId(), "Johnny", "Doe",
            "Antalya", "1234567", "joe@doe.com", "test1");
    app.getContactHelper().initContactModification(index);

    app.getContactHelper().fillContactForm(contact,false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId );
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }
}
