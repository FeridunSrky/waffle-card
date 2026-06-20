document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const errorEl = document.getElementById("loginError");
    errorEl.hidden = true;

	API.clearSession();
	
	//localStorage.removeItem("token");
	
    const kullaniciAdi = document.getElementById("kullaniciAdi").value.trim();
    const sifre = document.getElementById("sifre").value;

    try {
        const data = await API.login(kullaniciAdi, sifre);
        API.setSession(data.token, data.kullaniciAdi);
        window.location.href = "/panel.html";
    } catch (err) {
        errorEl.textContent = err.message;
        errorEl.hidden = false;
    }
});
