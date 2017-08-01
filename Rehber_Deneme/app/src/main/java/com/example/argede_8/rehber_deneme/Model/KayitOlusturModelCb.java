

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class KayitOlusturModelCb {

    @SerializedName("UserId")
    @Expose
    private String UserId;
    public void setUserId(String UserId){

        this.UserId = UserId;
    }
    public String getUserId(){

        return this.UserId;
    }
}
