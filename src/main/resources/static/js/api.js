const API = {
    tokenKey: "waffle_token",
    kasiyerKey: "waffle_kasiyer",

    getToken() {
        return localStorage.getItem(this.tokenKey);
    },

    setSession(token, kasiyerAdi) {
        localStorage.setItem(this.tokenKey, token);
        localStorage.setItem(this.kasiyerKey, kasiyerAdi);
    },

    clearSession() {
        localStorage.removeItem(this.tokenKey);
        localStorage.removeItem(this.kasiyerKey);
    },

    requireAuth() {
        if (!this.getToken()) {
            window.location.href = "/index.html";
        }
    },

    async request(url, options = {}) {
        const headers = {
            "Content-Type": "application/json",
            ...(options.headers || {})
        };

        const token = this.getToken();
        if (token) {
            headers.Authorization = `Bearer ${token}`;
        }

        const response = await fetch(url, { ...options, headers });

        if (response.status === 401 || response.status === 403) {
            this.clearSession();
            window.location.href = "/index.html";
            return null;
        }

        const data = await response.json().catch(() => ({}));

        if (!response.ok) {
            throw new Error(data.mesaj || "İstek başarısız");
        }

        return data;
    },

    login(kullaniciAdi, sifre) {
        return this.request("/api/auth/login", {
            method: "POST",
            body: JSON.stringify({ kullaniciAdi, sifre })
        });
    },

    kartGetir(kullaniciId) {
        return this.request(`/api/kartlar/kullanici/${kullaniciId}`);
    },

    waffleEkle(kullaniciId, waffleTuru) {
        return this.request(`/api/kartlar/kullanici/${kullaniciId}/waffle`, {
            method: "POST",
            body: JSON.stringify({ waffleTuru })
        });
    },

    bedavaVer(kullaniciId) {
        return this.request(`/api/kartlar/kullanici/${kullaniciId}/bedava`, {
            method: "POST"
        });
    },

    waffleSil(kullaniciId, index) {
        return this.request(`/api/kartlar/kullanici/${kullaniciId}/waffle/${index}`, {
            method: "DELETE"
        });
    },

    kartlariTemizle(kullaniciId) {
        return this.request(`/api/kartlar/kullanici/${kullaniciId}/temizle`, {
            method: "POST"
        });
    },

    kayitOl(ad, soyad, telefon) {
        return this.request("/api/kullanicilar", {
            method: "POST",
            body: JSON.stringify({ ad, soyad, telefon })
        });
    }
};
