package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Alex")
            .withLastName("Shmalex")
            .withAddress("Home sweet home")
            .withHomePhone("1111111")
            .withMobilePhone("2222222")
            .withWorkPhone("3333333")
            .withEmail1("alex@shmalex.com")
            .withGroup("test1");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }
}
