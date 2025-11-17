package com.pubito.pubito_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bar_id", nullable = false)
    private Bar bar;

    @Column(name = "position_name")
    @NotBlank
    private String positionName;

    private String type;

    @Column(name = "price", nullable = false)
    @Positive
    private float price;

    private String imgUrl;

    public Menu() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public @NotBlank String getPositionName() {
        return positionName;
    }

    public void setPositionName(@NotBlank String positionName) {
        this.positionName = positionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotNull
    public float getPrice() {
        return price;
    }

    public void setPrice(@NotNull float price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
