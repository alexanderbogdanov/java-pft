package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Alex")
              .withLastName("Shmalex")
              .withAddress("Home sweet home")
              .withPhoneNumber("3223322")
              .withEmail("alex@shmalex.com"));
    }
  }

  @Test
  public void testContactDeletion() {

    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    int index = before.size() - 1;
    app.contact().delete(index);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(deletedContact);
    Assert.assertEquals(before, after);
  }


}
