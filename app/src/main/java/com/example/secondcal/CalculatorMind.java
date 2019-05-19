package com.example.secondcal;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CalculatorMind {

    private String description;
    public String firstDes;
    public Boolean isPartial;

    public void setDescription(String des, String newInput){
        this.description = des + newInput;
    }

    public void setDescriptionWithRoot(String newInput) { this.description = newInput; }

    public String getDescription(){
        return description;
    }

}
