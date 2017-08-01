

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class KayitOlusturModel {
    @SerializedName("KullaniciTelefonNumarasi")
    @Expose
    private String TelefonNO;
    @SerializedName("KullaniciAdi")
    @Expose
    private String KullaniciAdi;
    @SerializedName("ProfilResmi")
    @Expose
    private String ProfilResmi;
    public KayitOlusturModel(String TelefonNO, String KullaniciAdi, String ProfilResmi) {
        this.TelefonNO = TelefonNO;
        this.ProfilResmi = ProfilResmi;
        this.KullaniciAdi = KullaniciAdi;
    }
}
