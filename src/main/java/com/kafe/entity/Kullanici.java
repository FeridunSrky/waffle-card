package com.kafe.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kullanicilar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kullanici_id_gen")
    @SequenceGenerator(
        name = "kullanici_id_gen",
        sequenceName = "kullanici_id_seq",
        allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String ad;

    @Column(nullable = false)
    private String soyad;

    @Column(nullable = false, unique = true)
    private String telefon;

    @Column(name = "kayit_tarihi")
    private LocalDateTime kayitTarihi;

    @PrePersist
    public void prePersist() {
        this.kayitTarihi = LocalDateTime.now();
    }
}