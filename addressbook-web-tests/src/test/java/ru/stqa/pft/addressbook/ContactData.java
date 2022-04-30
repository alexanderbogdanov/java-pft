package ru.stqa.pft.addressbook;

public class ContactData {
  private final String firstName;
  private final String lastName;
  private final String address;
  private final String phoneNumber;
  private final String email;

  public ContactData(String firstName, String lastName, String address, String phoneNumber, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }
}
