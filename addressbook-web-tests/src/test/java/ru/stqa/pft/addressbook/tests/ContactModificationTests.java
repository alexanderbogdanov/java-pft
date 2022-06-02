package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Alex")
              .withLastName("Shmalex")
              .withAddress("Home sweet home")
              .withHomePhone("1111111")
              .withMobilePhone("2222222")
              .withWorkPhone("3333333")
              .withEmail1("alex@shmalex.com")
              .withPhoto(new File("src/test/resources/pic.jpg"))
              .withGroup("test1"));

    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withFirstName("Johnny")
            .withLastName("Doe")
            .withAddress("Antalya")
            .withHomePhone("1234567")
            .withMobilePhone("232323")
            .withWorkPhone("989898989")
            .withEmail1("joe@doe.com")
            .withGroup("test1");
    app.contact().modify(contact);
    assertEquals(app.contact().count(), before.size());
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI(); // -DverifyListUI=true в конфигурации запуска
  }

}
