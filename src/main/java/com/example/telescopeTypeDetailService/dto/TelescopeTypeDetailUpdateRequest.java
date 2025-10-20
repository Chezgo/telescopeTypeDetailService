package com.example.telescopeTypeDetailService.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelescopeTypeDetailUpdateRequest(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 50, message = "Имя не может быть длиннее 50 символов")
        String name_telescope_type_detail,

        @Size(max = 255, message = "Описание не может быть длиннее 255 символов")
        String description
) {}
