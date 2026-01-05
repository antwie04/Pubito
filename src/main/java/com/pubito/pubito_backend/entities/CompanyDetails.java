package com.pubito.pubito_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_details")
public class CompanyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_id", nullable = false, unique = true)
    private Bar bar;

    @Column(name = "website_url",columnDefinition = "TEXT")
    private String websiteUrl;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

}
