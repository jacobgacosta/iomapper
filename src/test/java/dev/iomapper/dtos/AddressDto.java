package dev.iomapper.dtos;

/**
 * Created by norveo on 8/13/18.
 */
public class AddressDto {

    private String state;
    private String zip;
    private int totalPopulation;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(int totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

}
