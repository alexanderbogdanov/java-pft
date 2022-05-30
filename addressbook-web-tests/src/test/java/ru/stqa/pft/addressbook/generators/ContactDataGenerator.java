package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws Exception {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander.newBuilder()
            .addObject(generator)
            .build()
            .parse(args);
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")) {
      saveAsCSV(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXML(contacts, new File(file));
    } else if (format.equals("json")) {
      saveAsJSON(contacts, new File(file));
    } else {
      System.out.println("Format unrecognized " + format);
    }
  }

  private void saveAsJSON(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsXML(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsCSV(List<ContactData> contacts, File file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      for (ContactData contact : contacts) {
        writer.write(String.format("%s;%s;%s\n",
                contact.getFirstName(), contact.getLastName(), contact.getAddress(),
                contact.getHomePhone(), contact.getMobilePhone(),
                contact.getWorkPhone(), contact.getEmail1(), contact.getEmail2(),
                contact.getEmail3()));
      }
    }
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withFirstName(String.format("firstName%s", i))
              .withLastName(String.format("lastName%s", i))
              .withAddress(String.format("address%s", i))
              .withHomePhone(String.format("111%s", i))
              .withMobilePhone(String.format("222%s", i))
              .withWorkPhone(String.format("333%s", i))
              .withEmail1(String.format("john%s@mail.com", i))
              .withEmail2(String.format("john%s@post.com", i))
              .withEmail3(String.format("john%s@microsoft.com", i)));
    }
    return contacts;
  }
}
