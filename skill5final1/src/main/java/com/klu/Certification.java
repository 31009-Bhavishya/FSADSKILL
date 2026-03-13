package com.klu;

import org.springframework.stereotype.Component;

@Component
public class Certification {

    private int id = 501;
    private String name = "AWS Cloud Certification";
    private String dateOfCompletion = "10-03-2026";

    public String toString() {
        return "Certification ID: " + id +
               "\nCertification Name: " + name +
               "\nDate Of Completion: " + dateOfCompletion;
    }
}