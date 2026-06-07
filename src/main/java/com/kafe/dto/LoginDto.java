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
public class LoginDto {

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    private String kullaniciAdi;

    @NotBlank(message = "Şifre boş olamaz")
    private String sifre;
}