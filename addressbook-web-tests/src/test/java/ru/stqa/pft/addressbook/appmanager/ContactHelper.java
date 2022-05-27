package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;


public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver wd) {
    super(wd);
  }


  public void returnToHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }
  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail1());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail1());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());

  }


  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }
  private void selectContactByID(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }
  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }
  private Contacts contactCache = null;
  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();

      contactCache.add(new ContactData()
              .withId(id)
              .withFirstName(firstname)
              .withLastName(lastname)
              .withAddress(address)
              .withAllPhones(allPhones)
              .withAllEmails(allEmails));
    }
    return new Contacts(contactCache);
  }
  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId())
            .withFirstName(firstname)
            .withLastName(lastname)
            .withAddress(address)
            .withHomePhone(home)
            .withMobilePhone(mobile)
            .withWorkPhone(work)
            .withEmail1(email1)
            .withEmail2(email2)
            .withEmail3(email3);

  }
  public void initContactModificationById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
  }

  // alternatives
  /*
  public void initContactModificationByIdAlternative(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s]", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();

  //  wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();
  //  wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click();
  }
   */

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact);
    submitContactCreation();
    contactCache = null;
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact,false);
    submitContactModification();
    contactCache = null;
    returnToHomePage();
  }

  public void delete(int index) {
   selectContact(index);
   deleteSelectedContacts();
   returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactByID(contact.getId());
    deleteSelectedContacts();
    contactCache = null;
    returnToHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.cssSelector("img[alt='Edit']"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

//  private Contacts contactCache = null;
//  public Contacts all() {
//    if (contactCache != null) {
//      return new Contacts(contactCache);
//    }
//    contactCache = new Contacts();
//    List<WebElement> rows = wd.findElements(By.name("entry"));
//    for (WebElement row : rows) {
//      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value")); //not sure
//      List<WebElement> cells = row.findElements(By.tagName("td"));
//      String lastname = cells.get(1).getText();
//      String firstname = cells.get(2).getText();
//      contactCache.add(new ContactData()
//              .withId(id)
//              .withFirstName(firstname)
//              .withLastName(lastname));
//    }
//    return new Contacts(contactCache);
//  }






}