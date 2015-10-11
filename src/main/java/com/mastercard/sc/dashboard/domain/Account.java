package com.mastercard.sc.dashboard.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

    @XmlElement(name = "Status")
    private boolean status;

    @XmlElement(name = "Listed")
    private boolean listed;

    @XmlElement(name = "ReasonCode")
    private String reasonCode;

    @XmlElement(name = "Reason")
    private String reason;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isListed() {
        return listed;
    }

    public void setListed(boolean listed) {
        this.listed = listed;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Account{" +
                "status=" + status +
                ", listed=" + listed +
                ", reasonCode='" + reasonCode + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
