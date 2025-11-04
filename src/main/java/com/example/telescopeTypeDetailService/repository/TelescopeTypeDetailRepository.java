package com.example.telescopeTypeDetailService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface TelescopeTypeDetailRepository extends JpaRepository<TelescopeTypeDetail, Long> {

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM t_telescope_type_detail WHERE name_telescope_type_detail = :name", nativeQuery = true)
    Optional<TelescopeTypeDetail> findByTypeTelescopeName(String name);
}