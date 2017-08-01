

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class denememodel {
    @SerializedName("Elma")
    @Expose
    private String Elma;
    @SerializedName("Armut")
    @Expose
    private String Armut;
    public void setElma(String Elma){

        this.Elma = Elma;
    }
    public String getElma(){

        return this.Elma;
    }
    public void setArmut(String Armut){

        this.Armut = Armut;
    }
    public String getArmut(){

        return this.Armut;
    }
}
