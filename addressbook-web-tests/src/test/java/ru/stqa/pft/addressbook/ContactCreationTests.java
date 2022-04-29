package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

@Test
  public void testContactCreationTests() throws Exception {
    wd.findElement(By.linkText("add new")).click();
    wd.get("http://localhost/addressbook/edit.php");
    wd.findElement(By.name("firstname")).click();
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys("Alex");
    wd.findElement(By.name("lastname")).click();
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys("Shmalex");
    wd.findElement(By.name("address")).click();
    wd.findElement(By.name("address")).clear();
    wd.findElement(By.name("address")).sendKeys("Home sweet home");
    wd.findElement(By.name("home")).click();
    wd.findElement(By.name("home")).clear();
    wd.findElement(By.name("home")).sendKeys("3223322");
    wd.findElement(By.name("email")).click();
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys("alex@shmalex.com");
    wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
    wd.findElement(By.linkText("home page")).click();
    logout();

}
}
