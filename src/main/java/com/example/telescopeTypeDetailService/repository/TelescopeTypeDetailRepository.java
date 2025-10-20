package com.example.telescopeTypeDetailService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TelescopeTypeDetailRepository extends JpaRepository<TelescopeTypeDetail, Long> {


    @Query(value = "SELECT * FROM t_telescope_type_detail WHERE name_telescope_type_detail = :name_telescope_type_detail", nativeQuery = true)
    Optional<TelescopeTypeDetail> findByTypeTelescopeName(String name_telescope_type_detail);
}
