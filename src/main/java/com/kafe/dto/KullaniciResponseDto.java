package com.kafe.dto;

import java.time.LocalDateTime;

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
public class KullaniciResponseDto {

    private Long id;
    private String ad;
    private String soyad;
    private String telefon;
    private LocalDateTime kayitTarihi;
}