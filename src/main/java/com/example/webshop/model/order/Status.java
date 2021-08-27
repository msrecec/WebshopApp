package com.example.webshop.model.order;

public enum Status {
    DRAFT("DRAFT"), SUBMITTED("SUBMITTED");

    public final String status;

    private Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                '}';
    }
}
