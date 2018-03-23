package io.dojogeek.dtos;

public class UserDto {

    private String name;
    private String emailAddress;
    private BankCardDto cardDto;
    private String user;
    private AddressDto addressDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public BankCardDto getCardDto() {
        return cardDto;
    }

    public void setCardDto(BankCardDto cardDto) {
        this.cardDto = cardDto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

}