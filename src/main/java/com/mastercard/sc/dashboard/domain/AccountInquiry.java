package com.mastercard.sc.dashboard.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AccountInquiry")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountInquiry {

    @XmlElement(name = "AccountNumber")
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "AccountInquiry{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
