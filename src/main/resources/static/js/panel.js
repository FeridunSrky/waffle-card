API.requireAuth();

let aktifKullaniciId = null;

const panelError = document.getElementById("panelError");
const kartSection = document.getElementById("kartSection");
const bedavaBanner = document.getElementById("bedavaBanner");
const bedavaBtn = document.getElementById("bedavaBtn");
const temizleBtn = document.getElementById("temizleBtn");
const bardakBtn = document.getElementById("bardakBtn");
const kaseBtn = document.getElementById("kaseBtn");

document.getElementById("kasiyerAdi").textContent =
    localStorage.getItem(API.kasiyerKey) || "Kasiyer";

document.getElementById("logoutBtn").addEventListener("click", () => {
    API.clearSession();
    window.location.href = "/index.html";
});

function hataGoster(mesaj) {
    panelError.textContent = mesaj;
    panelError.hidden = false;
}

function hataTemizle() {
    panelError.hidden = true;
}

function kutulariGuncelle(kart) {
    const kutular = document.querySelectorAll(".kutu");
    const liste = kart.waffleListesi || [];

    kutular.forEach((kutu, index) => {
        kutu.classList.remove("dolu");
        kutu.querySelector(".kutu-tur").textContent = "";

        if (index < liste.length) {
            kutu.classList.add("dolu");
            kutu.querySelector(".kutu-tur").textContent = liste[index];
        }
    });

    document.getElementById("musteriAdSoyad").textContent = kart.kullaniciAdSoyad;
    document.getElementById("musteriIdGoster").textContent = kart.kullaniciId;
    document.getElementById("dolulukGoster").textContent = `${kart.toplamSayi} / 6`;

    const bedavaVar = kart.bedavaHakVar;
    bedavaBanner.hidden = !bedavaVar;
    bedavaBtn.hidden = !bedavaVar;

    const kartDolu = kart.kartDolu;
    bardakBtn.disabled = kartDolu;
    kaseBtn.disabled = kartDolu;
    temizleBtn.hidden = !kartDolu;
}

function kartGoster(kart) {
    aktifKullaniciId = kart.kullaniciId;
    kartSection.hidden = false;
    kutulariGuncelle(kart);
}

document.getElementById("kartGetirBtn").addEventListener("click", async () => {
    hataTemizle();

    const id = document.getElementById("kullaniciId").value;
    if (!id) {
        hataGoster("Müşteri ID girin");
        return;
    }

    try {
        const kart = await API.kartGetir(id);
        kartGoster(kart);
    } catch (err) {
        hataGoster(err.message);
        kartSection.hidden = true;
    }
});

async function waffleEkle(tur) {
    if (!aktifKullaniciId) return;
    hataTemizle();

    try {
        const kart = await API.waffleEkle(aktifKullaniciId, tur);
        kartGoster(kart);
    } catch (err) {
        hataGoster(err.message);
    }
}

bardakBtn.addEventListener("click", () => waffleEkle("BARDAK"));
kaseBtn.addEventListener("click", () => waffleEkle("KASE"));

bedavaBtn.addEventListener("click", async () => {
    if (!aktifKullaniciId) return;
    hataTemizle();

    try {
        const kart = await API.bedavaVer(aktifKullaniciId);
        kartGoster(kart);
    } catch (err) {
        hataGoster(err.message);
    }
});

temizleBtn.addEventListener("click", async () => {
    if (!aktifKullaniciId) return;
    hataTemizle();

    try {
        const kart = await API.kartlariTemizle(aktifKullaniciId);
        kartGoster(kart);
    } catch (err) {
        hataGoster(err.message);
    }
});

document.getElementById("kutuGrid").addEventListener("click", async (e) => {
    const silBtn = e.target.closest(".kutu-sil");
    if (!silBtn || !aktifKullaniciId) return;

    const kutu = silBtn.closest(".kutu");
    if (!kutu.classList.contains("dolu")) return;

    const index = parseInt(kutu.dataset.index, 10);
    hataTemizle();

    try {
        const kart = await API.waffleSil(aktifKullaniciId, index);
        kartGoster(kart);
    } catch (err) {
        hataGoster(err.message);
    }
});

document.getElementById("kayitForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    hataTemizle();

    const kayitMesaj = document.getElementById("kayitMesaj");
    kayitMesaj.hidden = true;

    const ad = document.getElementById("ad").value.trim();
    const soyad = document.getElementById("soyad").value.trim();
    const telefon = document.getElementById("telefon").value.trim();

    try {
        const kullanici = await API.kayitOl(ad, soyad, telefon);
        kayitMesaj.textContent =
            `Kayıt oluştu. Müşteri ID: ${kullanici.id} — kart otomatik açıldı.`;
        kayitMesaj.hidden = false;

        document.getElementById("kullaniciId").value = kullanici.id;
        e.target.reset();

        const kart = await API.kartGetir(kullanici.id);
        kartGoster(kart);
    } catch (err) {
        hataGoster(err.message);
    }
});
