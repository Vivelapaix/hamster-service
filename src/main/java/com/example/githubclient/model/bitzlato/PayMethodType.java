package com.example.githubclient.model.bitzlato;

public enum PayMethodType {
        SBER("3547", "Sberbank"),
        TINKOFF("443", "Tinkoff"),
        UNDEFINED("", "");

        public String code;
        public String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    PayMethodType(String code, String name) {
        this.code = code;
        this.name = name;


    }
}
