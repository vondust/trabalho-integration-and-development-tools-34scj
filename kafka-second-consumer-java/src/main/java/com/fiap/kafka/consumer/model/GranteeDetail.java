package com.fiap.kafka.consumer.model;

public class GranteeDetail {
    private String granteeNumber;
    private String granteeName;
    private Double installmentAmount;
    private String cityName;
    private String state;

    public GranteeDetail() {
    }

    public GranteeDetail(final String granteeNumber, final String granteeName,
                         final Double installmentAmount, final String cityName,
                         final String state) {
        this.granteeNumber = granteeNumber;
        this.granteeName = granteeName;
        this.installmentAmount = installmentAmount;
        this.cityName = cityName;
        this.state = state;
    }

    public String getGranteeNumber() {
        return granteeNumber;
    }

    public void setGranteeNumber(final String granteeNumber) {
        this.granteeNumber = granteeNumber;
    }

    public String getGranteeName() {
        return granteeName;
    }

    public void setGranteeName(final String granteeName) {
        this.granteeName = granteeName;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(final Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }
}