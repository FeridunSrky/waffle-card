package com.kafe.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "waffle_kartlari")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaffleKarti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "kullanici_id", nullable = false, unique = true)
    private Kullanici kullanici;

    // Waffle türleri virgülle ayrılmış saklanır
    // Örnek: "BARDAK,KASE,BARDAK"
    @Column(name = "waffle_listesi")
    private String waffleListesi;

    @Column(name = "toplam_sayi")
    private int toplamSayi = 0;

    @Column(name = "bedava_kullanildi")
    private boolean bedavaKullanildi = false;

    @Column(name = "son_guncelleme")
    private LocalDateTime sonGuncelleme;

    @PrePersist
    @PreUpdate
    public void preUpdate() {
        this.sonGuncelleme = LocalDateTime.now();
    }

    public void waffleEkle(String waffleTuru) {
        if (kartDoluMu()) {
            throw new IllegalStateException("Kart dolu. Önce bedava hakkı kullanılmalı veya kart sıfırlanmalı.");
        }
        if (this.waffleListesi == null || this.waffleListesi.isEmpty()) {
            this.waffleListesi = waffleTuru;
        } else {
            this.waffleListesi += "," + waffleTuru;
        }
        this.toplamSayi++;
    }

    public void bedavaWaffleKullan() {
        if (!bedavaHakKazandiMi()) {
            throw new IllegalStateException("Bedava waffle hakkı yok.");
        }
        this.waffleListesi = null;
        this.toplamSayi = 0;
        this.bedavaKullanildi = false;
    }

    public void waffleSil(int index) {
        List<String> liste = waffleListesiGetir();
        if (index < 0 || index >= liste.size()) {
            throw new IllegalStateException("Geçersiz kutu indeksi.");
        }
        liste.remove(index);
        if (liste.isEmpty()) {
            this.waffleListesi = null;
        } else {
            this.waffleListesi = String.join(",", liste);
        }
        this.toplamSayi = liste.size();
    }

    public void kartlariTemizle() {
        if (this.toplamSayi == 0) {
            throw new IllegalStateException("Temizlenecek kutu yok.");
        }
        this.waffleListesi = null;
        this.toplamSayi = 0;
    }

    // Listeyi döndür
    public List<String> waffleListesiGetir() {
        if (this.waffleListesi == null || this.waffleListesi.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(List.of(this.waffleListesi.split(",")));
    }

    // Kart dolu mu? (6 waffle)
    public boolean kartDoluMu() {
        return this.toplamSayi >= 6;
    }

    // Bedava hak kazandı mı?
    public boolean bedavaHakKazandiMi() {
        return kartDoluMu() && !bedavaKullanildi;
    }
}