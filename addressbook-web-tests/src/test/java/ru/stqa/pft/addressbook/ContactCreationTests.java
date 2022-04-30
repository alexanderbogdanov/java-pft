package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Alex", "Shmalex",
            "Home sweet home", "3223322", "alex@shmalex.com"));
    submitContactCreation();
    gotoHomePage();
    logout();

  }

}
