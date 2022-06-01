package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> contactsFromJSON() throws IOException {
    StringBuilder json;
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"))) {
      json = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        json.append(line);
        line = reader.readLine();
      }
    }
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(String.valueOf(json), new TypeToken<List<ContactData>>() {
    }.getType());
    return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
  }

  @Test(dataProvider = "contactsFromJSON")
  public void testContactCreation(ContactData contact) {
    Contacts before = app.db().contacts();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }

  @Test
  public void testContactCreationWithPhoto() {
    Contacts before = app.db().contacts();
    ContactData contact = new ContactData()
            .withFirstName("Alex")
            .withLastName("Shmalex")
            .withAddress("Home sweet home")
            .withHomePhone("1111111")
            .withMobilePhone("2222222")
            .withWorkPhone("3333333")
            .withEmail1("alex@shmalex.com")
            .withEmail2("dobby@shmobby.com")
            .withEmail3("johnny@shmonny.com")
            .withPhoto(new File("src/test/resources/pic.jpg"));
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }
}
