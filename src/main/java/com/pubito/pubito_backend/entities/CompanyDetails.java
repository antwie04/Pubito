package com.pubito.pubito_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "company_details")
public class CompanyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "website_url",columnDefinition = "TEXT")
    private String websiteUrl;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public CompanyDetails() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
