package com.example.telescopeTypeDetailService.service;

import com.example.telescopeTypeDetailService.dto.CreateTelescopeTypeDetailRequest;
import com.example.telescopeTypeDetailService.dto.TelescopeTypeDetailUpdateRequest;
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
    private final TelescopeTypeDetailRepository telescopeTypeDetailRepository;

    public TelescopeTypeDetailService(TelescopeTypeDetailRepository telescopeTypeDetailRepository) {
        this.telescopeTypeDetailRepository = telescopeTypeDetailRepository;
    }

    /**
     * Поиск всех типов деталей.
     *
     * @id739365412 (@return) найденный массив сущностей
     */
    public List<TelescopeTypeDetail> findAllTelescopeTypeDetail() {
        return telescopeTypeDetailRepository.findAll();
    }

    /**
     * Поиск типа детали телескопа по ID.
     *
     * @param id ID типа детали
     * @throws IllegalStateException если сущность не найдена
     * @id739365412 (@return) найденная сущность
     */
    public Optional<TelescopeTypeDetail> findTelescopeTypeDetail(Long id) {
        TelescopeTypeDetail entity = telescopeTypeDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Тип детали не найден"));
        return telescopeTypeDetailRepository.findById(id);
    }

    /**
     * Создаёт новый тип детали телескопа.
     * Проверяет уникальность имени, длину полей(на уровне контроллера).
     *
     * @id739365412 (@return) сохранённая сущность
     * @throws IllegalStateException если имя уже занято
     */
    public TelescopeTypeDetail createTelescopeTypeDetail(CreateTelescopeTypeDetailRequest request) {
        if (telescopeTypeDetailRepository.existsByName(request.name())) {
            throw new IllegalStateException("Тип детали с таким именем уже существует");
        }

        TelescopeTypeDetail entity = new TelescopeTypeDetail();
        entity.setName(request.name());
        entity.setDescription(request.description());

        log.info("Создание типа детали: {}", entity.getName());
        return telescopeTypeDetailRepository.save(entity);
    }


    /**
     * Удаляет тип детали телескопа по ID.
     * Проверяет наличие данных для удаления.
     *
     * @param id ID типа детали
     * @throws IllegalStateException если сущность не найдена
     */
    public void deleteTelescopeTypeDetail(Long id) {
        TelescopeTypeDetail entity = telescopeTypeDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Тип детали не найден"));
        telescopeTypeDetailRepository.deleteById(id);
    }


    /**
     * Обновляет тип детали телескопа по ID.
     * Проверяет уникальность имени и корректность данных.
     *
     * @param id ID типа детали
     * @id739365412 (@return) Обновлённая сущность
     * @throws IllegalStateException если сущность не найдена или имя занято
     */
    @Transactional
    public TelescopeTypeDetail updateTelescopeTypeDetail(Long id, TelescopeTypeDetailUpdateRequest request) {
        TelescopeTypeDetail entity = telescopeTypeDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Тип детали не найден"));

        if (request.name() != null && !request.name().equals(entity.getName())) {
            if (telescopeTypeDetailRepository.existsByName(request.name())) {
                throw new IllegalStateException("Тип детали с таким именем уже существует");
            }
            entity.setName(request.name());
        }

        if (request.description() != null) {
            entity.setDescription(request.description());
        }

        return telescopeTypeDetailRepository.save(entity);
    }
}