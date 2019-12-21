package com.fiap.kafka.consumer.model;

public class Message {
    private String referenceMonth;
    private String effectiveMonth;
    private String state;
    private String cityCode;
    private String cityName;
    private String granteeNumber;
    private String granteeName;
    private String installmentAmount;

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(String referenceMonth) {
        this.referenceMonth = referenceMonth;
    }

    public String getEffectiveMonth() {
        return effectiveMonth;
    }

    public void setEffectiveMonth(String effectiveMonth) {
        this.effectiveMonth = effectiveMonth;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getGranteeNumber() {
        return granteeNumber;
    }

    public void setGranteeNumber(String granteeNumber) {
        this.granteeNumber = granteeNumber;
    }

    public String getGranteeName() {
        return granteeName;
    }

    public void setGranteeName(String granteeName) {
        this.granteeName = granteeName;
    }

    public String getInstallmentAmount() {
        return installmentAmount.replace(",", ".");
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }
}
