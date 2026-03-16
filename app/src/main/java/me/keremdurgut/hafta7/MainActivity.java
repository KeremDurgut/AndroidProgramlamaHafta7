package me.keremdurgut.hafta7;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // 1. ADIM: Arayüz elemanlarını sınıf seviyesinde tanımlıyoruz ki tüm metodlar erişebilsin.
    private TextInputEditText kullaniciAdiEdtTxt;
    private TextInputEditText sifreEdtTxt;
    private RadioGroup meslekRadioGrp;
    private RadioGroup maasRadioGrp;
    private CheckBox kvkkChk;
    private Button onayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. ADIM: Elemanları XML'deki ID'leri ile eşleştiriyoruz.
        // Dikkat: KullaniciAdiTxtLyt yerine, içindeki EditText'in kendisini bulmalıyız.
        kullaniciAdiEdtTxt = findViewById(R.id.KullaniciAdiEdtTxt);
        sifreEdtTxt = findViewById(R.id.SifreEdtTxt);
        meslekRadioGrp = findViewById(R.id.MeslekRadioGrp);
        maasRadioGrp = findViewById(R.id.MaasRadioGrp);
        kvkkChk = findViewById(R.id.KvkkChk);
        onayBtn = findViewById(R.id.OnayBtn);


        // Uygulama ilk açıldığında buton pasif olsun
        onayBtn.setEnabled(false);

        // 3. ADIM: Dinleyicileri (Listeners) kuruyoruz.
        // Herhangi bir seçim değiştiğinde formuKontrolEt() metodunu çağırıyoruz.

        // KVKK Checkbox dinleyicisi
        kvkkChk.setOnCheckedChangeListener((buttonView, isChecked) -> {
            formuKontrolEt();
        });

        // Meslek RadioGroup dinleyicisi
        meslekRadioGrp.setOnCheckedChangeListener((group, checkedId) -> {
            formuKontrolEt();
        });

        // Maaş RadioGroup dinleyicisi
        maasRadioGrp.setOnCheckedChangeListener((group, checkedId) -> {
            formuKontrolEt();
        });

        // Yazı alanları (EditText) için metin değişim dinleyicisi
        TextWatcher metinDinleyici = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                formuKontrolEt();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        kullaniciAdiEdtTxt.addTextChangedListener(metinDinleyici);
        sifreEdtTxt.addTextChangedListener(metinDinleyici);

        // Butona tıklandığında çalışacak kodlar
        onayBtn.setOnClickListener(v -> {
            // Yeni sayfaya geçiş "niyetimizi" belirtiyoruz
            Intent intent = new Intent(MainActivity.this, MainActivity.class);

            // Verileri intent içerisine "anahtar-değer" (Key-Value) şeklinde paketliyoruz
            intent.putExtra("KULLANICI_ADI", kullaniciAdiEdtTxt.getText().toString());
            intent.putExtra("SIFRE", sifreEdtTxt.getText().toString());

            // Seçilen hesap türünü (Meslek) bulup paketliyoruz
            int seciliMeslekId = meslekRadioGrp.getCheckedRadioButtonId();
            RadioButton seciliMeslekBtn = findViewById(seciliMeslekId);
            intent.putExtra("HESAP_TURU", seciliMeslekBtn.getText().toString());

            // Seçilen maaşı bulup paketliyoruz
            int seciliMaasId = maasRadioGrp.getCheckedRadioButtonId();
            RadioButton seciliMaasBtn = findViewById(seciliMaasId);
            intent.putExtra("MAAS", seciliMaasBtn.getText().toString());

            // Yeni aktiviteyi başlatıyoruz!
            startActivity(intent);
        });

    }

    // 4. ADIM: Asıl logic katmanımız. Sadece bu metot her şeyi değerlendirir.
    private void formuKontrolEt() {
        // Kullanıcı adı boş mu? (Boşlukları silip uzunluğa bakıyoruz)
        boolean kullaniciAdiDolu = kullaniciAdiEdtTxt.getText() != null &&
                kullaniciAdiEdtTxt.getText().toString().trim().length() > 0;

        // Şifre boş mu?
        boolean sifreDolu = sifreEdtTxt.getText() != null &&
                sifreEdtTxt.getText().toString().trim().length() > 0;

        // Hesap Türü (Meslek) seçili mi? (-1 dönüyorsa hiçbir RadioButton seçilmemiştir)
        boolean meslekSecili = meslekRadioGrp.getCheckedRadioButtonId() != -1;

        // Maaş seçili mi?
        boolean maasSecili = maasRadioGrp.getCheckedRadioButtonId() != -1;

        // KVKK onaylı mı?
        boolean kvkkOnayli = kvkkChk.isChecked();

        // LOGIC KONTROLÜ: Eğer hepsi TRUE ise butonu aktif yap, biri bile FALSE ise pasif yap.
        if (kullaniciAdiDolu && sifreDolu && meslekSecili && maasSecili && kvkkOnayli) {
            onayBtn.setEnabled(true);
        } else {
            onayBtn.setEnabled(false);
        }
    }
}