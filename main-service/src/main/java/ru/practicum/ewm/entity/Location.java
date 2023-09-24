package ru.practicum.ewm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;
}
