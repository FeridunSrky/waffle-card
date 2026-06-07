# 1. Aşama: Uygulamayı derleme (Java 21 tabanlı Maven imajı)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

# Maven wrapper dosyasına Linux çalıştırma yetkisi veriyoruz (Hata çözümü)
RUN chmod +x mvnw

# Projeyi paketliyoruz (Testleri atlayarak)
RUN ./mvnw clean package -DskipTests

# 2. Aşama: Uygulamayı çalıştıracak hafif ve güncel Java 21 JRE imajı
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# İlk aşamada üretilen JAR dosyasını buraya kopyalıyoruz
COPY --from=build /app/target/*.jar app.jar

# Uygulamanın dış dünyaya açacağı port
EXPOSE 8080

# Uygulamayı başlatan komut
ENTRYPOINT ["java", "-jar", "app.jar"]