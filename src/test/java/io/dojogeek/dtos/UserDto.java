package io.dojogeek.dtos;

public class UserDto {

    private String name;
    private String firstSurname;
    private String secondSurname;
    private String emailAddress;
    private BankCardDto cardDto;
    private String user;
    private AddressDto addressDto;
    private ScholarShipDto scholarShipDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
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

    public ScholarShipDto getScholarShipDto() {
        return scholarShipDto;
    }

    public void setScholarShipDto(ScholarShipDto scholarShipDto) {
        this.scholarShipDto = scholarShipDto;
    }

}