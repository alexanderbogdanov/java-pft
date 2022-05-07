package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {


  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoGroupsPage() {
    click(By.linkText("groups"));
  }

  public void returnToGroupsPage() {
    click(By.linkText("group page"));
  }

  public void gotoHomePage() {
    click(By.linkText("home page"));
  }
}
