package com.kafe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class KullaniciKayitDto {

    @NotBlank(message = "Ad boş olamaz")
    private String ad;

    @NotBlank(message = "Soyad boş olamaz")
    private String soyad;

    @NotBlank(message = "Telefon boş olamaz")
    @Pattern(regexp = "^[0-9]{10}$", message = "Telefon 10 haneli olmalıdır (05xx değil, 5xx formatında)")
    private String telefon;
}