# Basit Java Web Sunucusu (Socket Programlama)

## 🎯 Amaç
Bu proje, **Java programlama dili** ve **Socket programlama** kullanarak üçüncü parti kütüphaneler olmadan basit bir **web sunucusu** geliştirmeyi amaçlar.  
Sunucu, bir tarayıcı üzerinden 1989 portuna bağlanıldığında kişisel bilgileri HTML formatında ekrana yansıtır.

---

## 💻 Proje Özeti
Sunucu 1989 numaralı port üzerinden gelen HTTP isteklerini dinler.  
Bir istemci (örneğin Chrome veya Edge tarayıcısı) `http://localhost:1989` adresine bağlandığında:

- `H1` etiketiyle **Ad Soyad** görüntülenir  
- `H2` etiketiyle **Öğrenci Numarası** gösterilir  
- Renk, font ve biçimlendirme içeren kısa bir **biyografi** HTML formatında sunulur  

---

## 👩‍💻 Bilgiler
- **Ad Soyad:** Amine Aksu  
- **Öğrenci No:** 1240505053  
- **Bölüm:** Yazılım Mühendisliği  
- **Yaş:** 19  
- **Üniversite:** Kırklareli Üniversitesi  
- **Yaşadığı Yer:** İstanbul  

---

## ⚙️ Kullanılan Kavramlar

### 🔹 Socket
Socket, iki cihaz arasında ağ üzerinden veri alışverişi yapılmasını sağlayan uç noktadır.  
Bu projede `ServerSocket` nesnesi, sunucunun belirli bir port üzerinden istemci bağlantılarını dinlemesini sağlar.

### 🔹 Port
Port, bilgisayarın ağ üzerinden farklı uygulamaları ayırt etmesini sağlar.  
Bu projede **1989 portu** kullanılmıştır.

### 🔹 IP (Internet Protocol)
IP adresi, bir cihazın ağ üzerindeki kimliğidir.  
Yerel testlerde **localhost (127.0.0.1)** IP’si kullanılır.

---

## 🚀 Çalıştırma Adımları

### 1️⃣ Dosyayı derle
Terminal veya komut istemcisinde proje klasörüne gir ve aşağıdaki komutu çalıştır:
```bash
javac SimpleWebServer.java
http://localhost:1989
