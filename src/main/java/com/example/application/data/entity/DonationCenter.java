package com.example.application.data.entity;


import javax.persistence.*;

@Entity
public class DonationCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    private Long id;

    private String name;
    private String address;
    private Integer startHour;
    private Integer endHour;
    private Integer maxDonationsPerDay;
    private String area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getNameAndAddress(){
        return this.name+", " + this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getMaxDonationsPerDay() {
        return maxDonationsPerDay;
    }

    public void setMaxDonationsPerDay(Integer maxDonationsPerDay) {
        this.maxDonationsPerDay = maxDonationsPerDay;
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DonationCenter)) {
            return false; // null or other class
        }
        DonationCenter other = (DonationCenter) obj;

        if (getId() != null) {
            return getId().equals(other.getId());
        }
        return super.equals(other);
    }
}
