

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TokenCb {
    @SerializedName("access_token")
    @Expose
    private String access_token;
    public void setAccess_token(String access_token){

        this.access_token = access_token;
    }
    public String getAccess_token(){

        return this.access_token;
    }
}
