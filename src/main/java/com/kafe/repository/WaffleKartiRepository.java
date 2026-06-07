package com.kafe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kafe.entity.WaffleKarti;

@Repository
public interface WaffleKartiRepository extends JpaRepository<WaffleKarti, Long> {

    Optional<WaffleKarti> findByKullaniciId(Long kullaniciId);

    boolean existsByKullaniciId(Long kullaniciId);
}