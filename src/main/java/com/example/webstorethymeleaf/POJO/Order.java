package com.example.webstorethymeleaf.POJO;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Customer customer;

    @OneToMany
    @JoinColumn
    private List<Item> items;

    private LocalDate localDate;
    public Order(LocalDate localDate, Customer customer, List<Item> items){
        this.localDate = localDate;
        this.customer = customer;
        this.items = items;
    }

}
