package com.example.telescopeTypeDetailService.service;

import com.example.telescopeTypeDetailService.repository.TelescopeTypeDetail;
import com.example.telescopeTypeDetailService.repository.TelescopeTypeDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;


@Service
public class TelescopeTypeDetailService {

    private static final Logger log = LoggerFactory.getLogger(TelescopeTypeDetailService.class);

    private boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private final TelescopeTypeDetailRepository telescopeTypeDetailRepository;

    public TelescopeTypeDetailService(TelescopeTypeDetailRepository telescopeTypeDetailRepository) {
        this.telescopeTypeDetailRepository = telescopeTypeDetailRepository;
    }


    public List<TelescopeTypeDetail> findAllTelescopeTypeDetail(){
        return telescopeTypeDetailRepository.findAll();
    }

    public Optional<TelescopeTypeDetail> findTelescopeTypeDetail(Long idTelescopeTypeDetail){
        return telescopeTypeDetailRepository.findById(idTelescopeTypeDetail);
    }

    /**
     * Создаёт новый тип детали телескопа.
     * Проверяет уникальность имени, длину полей и непустое имя.
     *
     * @param telescopeTypeDetail объект с данными
     * @return сохранённая сущность
     * @throws IllegalArgumentException если данные некорректны
     * @throws IllegalStateException если имя уже занято
     */
    public TelescopeTypeDetail createTelescopeTypeDetail(TelescopeTypeDetail telescopeTypeDetail) {
        if (telescopeTypeDetail == null) {
            throw new IllegalArgumentException("Объект не может быть null");
        }

        String name = telescopeTypeDetail.getName();
        String description = telescopeTypeDetail.getDescription();

        if (!isNotBlank(name)) {
            throw new IllegalArgumentException("Имя обязательно для заполнения");
        }

        if (name.length() > 50) {
            throw new IllegalArgumentException("Имя не может быть длиннее 50 символов");
        }

        if (description != null && description.length() > 255) {
            throw new IllegalArgumentException("Описание не может быть длиннее 255 символов");
        }

        if (description != null) {
            String trimmed = description.trim();
            if (trimmed.isEmpty()) {
                telescopeTypeDetail.setDescription(null);
            } else {
                telescopeTypeDetail.setDescription(trimmed);
            }
        }

        Optional<TelescopeTypeDetail> existingByName =
                telescopeTypeDetailRepository.findByTypeTelescopeName(name);
        if (existingByName.isPresent()) {
            throw new IllegalStateException("Тип детали с таким именем уже существует");
        }
        log.info("Создание типа детали: {}", telescopeTypeDetail.getName());

        return telescopeTypeDetailRepository.save(telescopeTypeDetail);
    }

    public void deleteTelescopeTypeDetail(Long idTelescopeTypeDetail){
        telescopeTypeDetailRepository.deleteById(idTelescopeTypeDetail);
    }
    /**
     * Обновляет тип детали телескопа по ID.
     * Проверяет уникальность имени и корректность данных.
     *
     * @param id ID типа детали
     * @param nameTelescopeTypeDetail Новое имя (опционально)
     * @param description Новое описание (опционально)
     * @return Обновлённая сущность
     * @throws IllegalStateException если сущность не найдена или имя занято
     * @throws IllegalArgumentException если данные некорректны
     */
    @Transactional
    public TelescopeTypeDetail updateTelescopeTypeDetail(
            Long id,
            String nameTelescopeTypeDetail,
            String description) {

        Optional<TelescopeTypeDetail> optionalTelescopeTypeDetail =
                telescopeTypeDetailRepository.findById(id);

        if (optionalTelescopeTypeDetail.isEmpty()) {
            throw new IllegalStateException("Type telescope not found");
        }

        TelescopeTypeDetail telescopeTypeDetail = optionalTelescopeTypeDetail.get();

        if (isNotBlank(nameTelescopeTypeDetail) &&
                !nameTelescopeTypeDetail.equals(telescopeTypeDetail.getName()))  {

            Optional<TelescopeTypeDetail> existingByName =
                    telescopeTypeDetailRepository.findByTypeTelescopeName(nameTelescopeTypeDetail);

            if (nameTelescopeTypeDetail.length() > 50) {
                throw new IllegalArgumentException("Имя не может быть длиннее 50 символов");
            }

            if (existingByName.isPresent()) {
                throw new IllegalStateException("Type telescope is not unique");
            }

            telescopeTypeDetail.setName(nameTelescopeTypeDetail);
        }

        if (isNotBlank(description)) {
            if (description.length() > 255) {
                throw new IllegalArgumentException("Описание не может быть длиннее 255 символов");
            }
            if (!description.equals(telescopeTypeDetail.getDescription())) {
                telescopeTypeDetail.setDescription(description);
            }
        }

        return telescopeTypeDetailRepository.save(telescopeTypeDetail);
    }


}
