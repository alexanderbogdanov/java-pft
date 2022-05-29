package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.xml"));
    StringBuilder xml = new StringBuilder();
    String line = reader.readLine();
    while (line != null) {
      xml.append(line);
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml.toString());
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();

  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupsPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt()))));
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupsPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test'");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }
}
