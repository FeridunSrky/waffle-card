package com.kafe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaffleEkleDto {

    @NotBlank(message = "Waffle türü boş olamaz")
    private String waffleTuru; // "BARDAK" veya "KASE"
}