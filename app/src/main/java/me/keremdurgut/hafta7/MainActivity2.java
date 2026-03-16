package me.keremdurgut.hafta7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // XML'deki TextView'ı buluyoruz
        TextView sonucTxt = findViewById(R.id.SonucTxt);

        // Bize gelen Intent paketini alıyoruz
        Intent gelenIntent = getIntent();

        // Paketin içinden verileri aynı anahtar kelimelerle çıkartıyoruz
        String gelenKullaniciAdi = gelenIntent.getStringExtra("KULLANICI_ADI");
        String gelenSifre = gelenIntent.getStringExtra("SIFRE");
        String gelenHesapTuru = gelenIntent.getStringExtra("HESAP_TURU");
        String gelenMaas = gelenIntent.getStringExtra("MAAS");

        // Ekrana yazdırılacak derli toplu bir metin oluşturuyoruz
        String ekranaYazdirilacakMetin = "Hesap Başarıyla Oluşturuldu!\n\n" +
                "Kullanıcı Adı: " + gelenKullaniciAdi + "\n" +
                "Şifre: " + gelenSifre + "\n" +
                "Hesap Türü: " + gelenHesapTuru + "\n" +
                "Maaş: " + gelenMaas;

        // Metni TextView'a atıyoruz
        sonucTxt.setText(ekranaYazdirilacakMetin);
    }
}