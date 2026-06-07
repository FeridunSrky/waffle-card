package com.kafe.dto;

import java.time.LocalDateTime;
import java.util.List;

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
public class WaffleKartiResponseDto {

    private Long kartiId;
    private Long kullaniciId;
    private String kullaniciAdSoyad;
    private List<String> waffleListesi;  // ["BARDAK", "KASE", "BARDAK", ...]
    private int toplamSayi;              // max 6
    private boolean kartDolu;           // toplamSayi == 6
    private boolean bedavaHakVar;       // dolu ve henüz kullanılmamış
    private boolean bedavaKullanildi;
    private LocalDateTime sonGuncelleme;
}