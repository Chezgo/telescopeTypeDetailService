package com.example.telescopeTypeDetailService.repository;

import jakarta.persistence.*;

@Entity
@Table(name = "t_telescope_type_detail")
public class TelescopeTypeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_telescope_type_detail")
    private Long id;
    @Column(name = "name_telescope_type_detail")
    private String name;
    private String description;


    public TelescopeTypeDetail(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public TelescopeTypeDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
