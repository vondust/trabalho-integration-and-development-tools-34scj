package com.fiap.kafka.consumer.model;

public class StateDetail {

    private String state;
    private Double installmentSum;
    private Integer granteeQuantity;

    public StateDetail() {
    }

    public StateDetail(String state, Double installmentSum, Integer granteeQuantity) {
        this.state = state;
        this.installmentSum = installmentSum;
        this.granteeQuantity = granteeQuantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getInstallmentSum() {
        return installmentSum;
    }

    public void setInstallmentSum(Double installmentSum) {
        this.installmentSum = installmentSum;
    }

    public Integer getGranteeQuantity() {
        return granteeQuantity;
    }

    public void setGranteeQuantity(Integer granteeQuantity) {
        this.granteeQuantity = granteeQuantity;
    }
}
