package io.dojogeek.sayamapper;

public class UserDto {

    private String name;
    private int age;
    private String email;
    private String address;
    private BankCardDto cardDto;
    private JobDto jobDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BankCardDto getCardDto() {
        return cardDto;
    }

    public void setCardDto(BankCardDto cardDto) {
        this.cardDto = cardDto;
    }

    public JobDto getJobDto() {
        return jobDto;
    }

    public void setJobDto(JobDto jobDto) {
        this.jobDto = jobDto;
    }
}