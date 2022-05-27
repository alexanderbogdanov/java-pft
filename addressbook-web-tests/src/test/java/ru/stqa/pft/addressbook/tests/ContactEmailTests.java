package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Alex")
              .withLastName("Shmalex")
              .withAddress("Home sweet home")
              .withHomePhone("1111111")
              .withMobilePhone("2222222")
              .withWorkPhone("3333333")
              .withEmail1("alex@shmalex.com")
              .withEmail2("bobby@shmobby.com")
              .withEmail2("jenny@shmenny.com")
              .withGroup("test1"));
    }
  }

  @Test
  public void testContactPhones() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));

  }

  private String mergeEmails(ContactData contact) {
    return Stream.of(contact.getEmail1(), contact.getEmail2(), contact.getEmail3())
            .filter((s) -> !s.equals(""))
            .map(ContactEmailTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "");
  }

}
