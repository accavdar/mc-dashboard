package com.mastercard.sc.dashboard.domain;

public enum Client {
    WEB("Web"),
    IOS("IOS"),
    ANDROID("Android");

    private String name;

    Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
