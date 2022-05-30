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
    BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"));
    StringBuilder json = new StringBuilder();
    String line = reader.readLine();
    while (line != null) {
      json.append(line);
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(String.valueOf(json), new TypeToken<List<ContactData>>() {
    }.getType());
    return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
  }

  @Test(dataProvider = "contactsFromJSON")
  public void testContactCreation(ContactData contact) {
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }

  @Test(dataProvider = "contactsFromJSON")
  public void testContactCreationWithPhoto() {
    File photo = new File("src/test/resources/pic.jpg");
    Contacts before = app.contact().all();
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
            .withPhoto(photo);
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }
}
