package com.example.telescopeTypeDetailService.controller;

import com.example.telescopeTypeDetailService.dto.TelescopeTypeDetailUpdateRequest;
import com.example.telescopeTypeDetailService.repository.TelescopeTypeDetail;
import com.example.telescopeTypeDetailService.service.TelescopeTypeDetailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/typeDetail")
public class TelescopeTypeDetailController {

    private final TelescopeTypeDetailService telescopeTypeDetailService;

    public TelescopeTypeDetailController(TelescopeTypeDetailService telescopeTypeDetailService) {
        this.telescopeTypeDetailService = telescopeTypeDetailService;
    }

    @GetMapping
    public List<TelescopeTypeDetail> findTelescopeAllTypeDetail(){
        return telescopeTypeDetailService.findAllTelescopeTypeDetail();
    }

    @GetMapping("/{idTelescopeTypeDetail}")
    public ResponseEntity<TelescopeTypeDetail> getTelescopeTypeDetailById(
            @PathVariable Long idTelescopeTypeDetail
    ) {
        Optional<TelescopeTypeDetail> typeDetail = telescopeTypeDetailService.findTelescopeTypeDetail(idTelescopeTypeDetail);
        return typeDetail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TelescopeTypeDetail createTelescopeTypeDetail(@Valid @RequestBody TelescopeTypeDetail telescopeTypeDetail){
        return telescopeTypeDetailService.createTelescopeTypeDetail(telescopeTypeDetail);
    }

    @DeleteMapping("/{idTelescopeTypeDetail}")
    public ResponseEntity<Void> deleteTelescopeTypeDetail(@PathVariable Long idTelescopeTypeDetail) {
        telescopeTypeDetailService.deleteTelescopeTypeDetail(idTelescopeTypeDetail);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelescopeTypeDetail> updateTelescopeTypeDetail(
            @PathVariable Long id,
            @Valid@RequestBody TelescopeTypeDetailUpdateRequest request) {

        TelescopeTypeDetail updated = telescopeTypeDetailService.updateTelescopeTypeDetail(
                id, request.name_telescope_type_detail(), request.description()
        );
        return ResponseEntity.ok(updated);
    }

}
