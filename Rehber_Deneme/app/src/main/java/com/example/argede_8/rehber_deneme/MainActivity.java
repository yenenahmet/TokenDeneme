package com.example.argede_8.rehber_deneme;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import com.example.argede_8.rehber_deneme.HataLayoutCikar.InputValidation;
import com.example.argede_8.rehber_deneme.Model.KayitOlusturModel;
import com.example.argede_8.rehber_deneme.Model.KayitOlusturModelCb;
import com.example.argede_8.rehber_deneme.Model.TokenCb;
import com.example.argede_8.rehber_deneme.Model.denememodel;
import com.example.argede_8.rehber_deneme.Servis.KayitAlServis;
import com.example.argede_8.rehber_deneme.Servis.TokenAl;
import com.example.argede_8.rehber_deneme.Servis.TokenDenemeServis;
import com.example.argede_8.rehber_deneme.Static.CircleTransformation;
import com.example.argede_8.rehber_deneme.Static.Myapi;
import com.example.argede_8.rehber_deneme.Static.ResimCozunurluk;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.example.argede_8.rehber_deneme.R.id.nestedScrollView;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private TextInputEditText KullaniciTelefon;
    private TextInputEditText KullaniciAdi;
    private AppCompatImageView KullaniciProfilResmi;
    private AppCompatButton KayıtOlButton;
    private TextInputLayout textlayoutTelefonNumarasi;
    private TextInputLayout Kullaniciaditextlayout;
    private ScrollView scrollView;
    private Bitmap Resimyolla;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView =(ScrollView)findViewById(R.id.nestedScrollView);
        KullaniciProfilResmi = (AppCompatImageView)findViewById(R.id.profilResmi);
        KullaniciProfilResmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResimEkle();
            }
        });
        Kullaniciaditextlayout =(TextInputLayout)findViewById(R.id.Kullaniciaditextlayout);
        KullaniciTelefon = (TextInputEditText)findViewById(R.id.telefonnumarası);
        KullaniciAdi = (TextInputEditText)findViewById(R.id.KullaniciAdi);
        KullaniciProfilResmi = (AppCompatImageView)findViewById(R.id.profilResmi);
        KayıtOlButton = (AppCompatButton)findViewById(R.id.KayıtOl);
        textlayoutTelefonNumarasi = (TextInputLayout)findViewById(R.id.textlayoutTelefonNumarasi);
        final  InputValidation ınputerr = new InputValidation(getApplicationContext());
        KayıtOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean durum = true;
               if(!ınputerr.TelefonKontrol(KullaniciTelefon,textlayoutTelefonNumarasi,"Telefon Numarısı Hatalı")){
                   durum =false;
               }
                if(!ınputerr.KullaniciAdiKontrol(KullaniciAdi,Kullaniciaditextlayout,"Kulanici Adiniz En Az iki harfli olabilir")){
                    durum =false;
                }
                try{
                    if(Resimyolla.getByteCount() ==0 ){
                        Snackbar.make(scrollView, "HATA! Resim yüklenmemiş!", Snackbar.LENGTH_LONG).show();
                        durum =false;
                    }
                }catch (NullPointerException ex){
                    Snackbar.make(scrollView, "HATA! Resim yüklenmemiş!", Snackbar.LENGTH_LONG).show();
                    durum =false;
                }

                if(durum){
                    Snackbar.make(scrollView, "Kayıt oluşturuluyor ...", Snackbar.LENGTH_LONG).show();
                    KayitOlustur();
                }
                Intent intent = new Intent(getApplicationContext(), explistview.class);
                startActivity(intent);
                finish();
            }
        });
        showContacts();
        denemeToken();
        TokenGeliyormu();
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 10);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            //ListeyiBas();
            ListeyiYazdir();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        switch (requestCode) {
            case 10:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ListeyiYazdir();
                } else {
                    // permission denied
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", getPackageName(), null));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                break;
            case 20:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1);
                } else {

                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            AppCompatImageView imageView = (AppCompatImageView) findViewById(R.id.profilResmi);
            CircleTransformation ab = new CircleTransformation();
            imageView.setBackground(null);
            imageView.setImageBitmap(ab.transform(BitmapFactory.decodeFile(picturePath)));
            Resimyolla = ResimCozunurluk.decodeSampledBitmapFromResource(picturePath,imageView.getHeight(),imageView.getWidth());
            Log.e("Byte", String.valueOf(Resimyolla.getByteCount()));
        }
    }
    private void ListeyiYazdir(){
        List<String> arraylist = new ArrayList<>();
        arraylist = ListeyiAl();
        for(int i=0;i<arraylist.size();i++){
            String[] kelime = null;
            kelime = arraylist.get(i).split(",");
            if(!fazlalikkontrol(kelime[1])){
                // whatp olarak kaydedilenleri engelle
                Log.e(i+". (1)deger =",arraylist.get(i)); // rehber Listesi
            }else{
                Log.e(i+". (2)deger =",arraylist.get(i)); // rehber Listesi
            }
        }
    }
    private List<String> ListeyiAl(){
        List<String> arraylist =new ArrayList<>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor people = getContentResolver().query(uri, projection, null, null, null);
        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        if(people.moveToFirst()) {
            do {
                String name   = people.getString(indexName);
                String number = people.getString(indexNumber);
                arraylist.add(name+","+number);
            } while (people.moveToNext());
        }
    return arraylist;
    }
    private boolean fazlalikkontrol(String number){
        boolean durum=false;
        for(int i=0;i<number.length();i++){
          int j =   number.indexOf(" ");
            if(j==-1){
                durum = true; // boşluk var
            }
            // boşluk yok // false
        }
        return durum;
    }
    private void denemeToken(){
            String token = "Bearer 93xLEVuUUouhcGCtzHy0KeP2XPEz13vVJxCXc3nw6cLpypQATnUfrv-E2EKcO1hX3toqxdcLBvD8o4ZRfwX36tlTJX_K8zT9zHP7oeGUgV9pYFMhY_tAD9SApspoJRTQKw-sNyjBqfmrYpy2ZfTIo6_qKJh-9QyEnkp-CS0Xf2_ykqDOqSXcbZxjRRLZbK2FFXYsp2mP8uW7dt8nD-ghm3V5WjL59hvf9U8vrRk-eow";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Myapi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TokenDenemeServis servis = retrofit.create(TokenDenemeServis.class);
            Call<denememodel> call = servis.getProfil(token);
            call.enqueue(new Callback<denememodel>() {
                @Override
                public void onResponse(Response<denememodel> response, Retrofit retrofit) {
                    if(response.isSuccess()){
                        Log.e("armut=",response.body().getArmut());
                        Log.e("elma =",response.body().getElma());
                    }else{
                        Log.e("elseyedüştü","else");
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("hata","patladi"+t.toString());
                }
            });
        }
    private void TokenGeliyormu(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Myapi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TokenAl servis = retrofit.create(TokenAl.class);
            Call<TokenCb> call = servis.konumyollaServis("password","Gokhan","123456");
            call.enqueue(new Callback<TokenCb>() {
                @Override
                public void onResponse(Response<TokenCb> response, Retrofit retrofit) {
                    if(response.isSuccess()){
                        Log.e("Token",response.body().getAccess_token());
                         preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                         editor  = preferences.edit();
                         editor.putString("Token","Bearer " +response.body().getAccess_token().trim().toString());
                         editor.commit();
                    }else{
                        Log.e("HATA","elseyedüştü");
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("HATA",t.toString());
                }
            });
        }
    private void ResimEkle(){
        if((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))

        {
            ActivityCompat.requestPermissions
                    (MainActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },20);
        }else{
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
        }

    }
    private void KayitOlustur(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Kayit oluşturuluyor...Lütfen Biraz Bekleyin");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Resimyolla.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Myapi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KayitAlServis servis = retrofit.create(KayitAlServis.class);
        KayitOlusturModel model = new KayitOlusturModel(KullaniciTelefon.getText().toString(),KullaniciAdi.getText().toString(),imageString.trim());
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = preferences.getString("Token","0");
        Call<KayitOlusturModelCb> call = servis.Kayital(model,token);
        call.enqueue(new Callback<KayitOlusturModelCb>() {
            @Override
            public void onResponse(Response<KayitOlusturModelCb> response, Retrofit retrofit) {
                progressDialog.cancel();
                if(response.isSuccess()){
                    if(response.body().getUserId().equals("1")){
                        Log.e("dönen",response.body().getUserId());
                        Snackbar.make(scrollView, "Kayıt oluşturulmuştur", Snackbar.LENGTH_LONG).show();
                    }else{
                        Log.e("Bir Hata oluştu!!",response.body().getUserId());
                        Snackbar.make(scrollView, "Bu kayit daha önceden Yapılmıştır veya  belirsiz bir hata oluştu !!", Snackbar.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Serviste Hata oluştu", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(), "Servisten veya İnternet bağlanıtınızda Kaynaklı Hata oluştu"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
