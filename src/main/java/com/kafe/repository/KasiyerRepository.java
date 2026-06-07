package com.kafe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kafe.entity.Kasiyer;

@Repository
public interface KasiyerRepository extends JpaRepository<Kasiyer, Long> {

    Optional<Kasiyer> findByKullaniciAdi(String kullaniciAdi);

    boolean existsByKullaniciAdi(String kullaniciAdi);
}