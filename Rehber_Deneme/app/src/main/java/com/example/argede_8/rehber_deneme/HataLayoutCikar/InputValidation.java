
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class InputValidation {
    private Context context;


    public InputValidation(Context context) {
        this.context = context;
    }


public boolean TelefonKontrol(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if(value.length() != 10){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
        }
    return  true;
}
    public boolean KullaniciAdiKontrol(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if(value.length() < 2){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
        }
        return  true;
    }
    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
