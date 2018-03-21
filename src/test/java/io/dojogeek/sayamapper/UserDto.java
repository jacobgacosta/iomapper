package io.dojogeek.sayamapper;

public class UserDto {

    private String name;
    private int age;
    private String emailAddress;
    private BankCardDto cardDto;
    private JobDto jobDto;
    private String user;

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

    public JobDto getJobDto() {
        return jobDto;
    }

    public void setJobDto(JobDto jobDto) {
        this.jobDto = jobDto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}