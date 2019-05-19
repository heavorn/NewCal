package com.example.secondcal;

import android.icu.util.BuddhistCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView displayToScreen;
    TextView description;
    String lastOperator, currentStringToDisplay, lastString;
    double currentValue;
    Button btnDot;
    Boolean isEqual, isDot, hasOnlyOneOperand;
    CalculatorMind calculatorMind;
    DecimalFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayToScreen = (TextView) findViewById(R.id.display);
        description = (TextView) findViewById(R.id.descrip);
        btnDot = (Button) findViewById(R.id.dot);
        calculatorMind = new CalculatorMind();
        calculatorMind.isPartial = false;
        currentValue = 0.0;
        currentStringToDisplay = "";
        lastString = "";
        isEqual = true;
        isDot = true;
        hasOnlyOneOperand = true;

        format = new DecimalFormat("0.#");
    }

    public void typeDigit(View view){

        Button digitButton = (Button) findViewById(view.getId());
        String input = digitButton.getText().toString();

        if (!isEqual){
            resetAll();
        }

        calculatorMind.setDescription(getCurrentDesText(), input);
        description.setText(calculatorMind.getDescription());
        currentDisplay(input);
        calculatorMind.isPartial = true;
        isEqual = true;

    }

    public void typeOperator(View view){
        if (calculatorMind.isPartial){
            double newValue = Double.parseDouble(currentStringToDisplay);

            if (!hasOnlyOneOperand){
                currentValue = performCalculation(currentValue, newValue, lastOperator);
                Log.d("tag: ", "" + currentValue);
            } else {
                currentValue = newValue;
                Log.d("tag: ", "" + currentValue);
                hasOnlyOneOperand = false;
            }

            calculatorMind.firstDes = calculatorMind.getDescription();
            Log.d("tag", "" + calculatorMind.firstDes);
            Button operatorBUTTON = (Button) findViewById(view.getId());
            lastOperator = operatorBUTTON.getText().toString();
            addDescription(lastOperator);
            description.setText(calculatorMind.getDescription());
            currentStringToDisplay = "";
//            calculatorMind.isPartial = false;
            isDot = true;
            isEqual = true;
        }
    }

    public void equalOnClick(View view){
        double total;
        lastString = currentStringToDisplay;
        if (isEqual) {
            if (hasOnlyOneOperand){

                Log.d("tag", "G1");
                Log.d("tag", "" + hasOnlyOneOperand);
                currentStringToDisplay = String.valueOf(format.format(currentValue));

            } else {

                double secondOperand = 0.0;

                if (currentStringToDisplay.equals("")){
                    secondOperand = currentValue;
                    lastString = format.format(secondOperand) + lastOperator + format.format(secondOperand);
                    calculatorMind.setDescriptionWithRoot(lastString);
                    description.setText(calculatorMind.getDescription());
                } else {
                    secondOperand = Double.parseDouble(currentStringToDisplay);
                }

                Log.d("tag", "G2");
                total = performCalculation(currentValue, secondOperand, lastOperator);
                currentStringToDisplay = String.valueOf(format.format(total));
                currentValue = total;
                isEqual = false;

            }
        } else {
            Log.d("tag", "G3");
            currentStringToDisplay = String.valueOf(format.format(currentValue));
            isEqual = true;
        }

        Log.d("tag: ", "" + currentValue);
        displayToScreen.setText(currentStringToDisplay);
        hasOnlyOneOperand = true;

    }

    public void rootOnClick(View view){
        if(calculatorMind.isPartial){
            Button operatorBUTTON = (Button) findViewById(view.getId());
            String des = "";

            if (hasOnlyOneOperand){
                if (!isEqual){
                    Log.d("tag", "1");
                    des = getString(R.string.root) + "(" + calculatorMind.firstDes + lastOperator + lastString + ")" ;
                    currentValue = Math.sqrt(currentValue);
                } else {
                    Log.d("tag", "2");
                    currentValue = Math.sqrt(Double.parseDouble(calculatorMind.getDescription()));
                    des = getString(R.string.root) + "(" + calculatorMind.getDescription() + ")";
                }

                currentStringToDisplay = String.valueOf(format.format(currentValue));


            } else {
                Log.d("tag", "3");
                double sqrtValue = Math.sqrt(Double.parseDouble(currentStringToDisplay));
                currentValue = performCalculation(currentValue, sqrtValue, lastOperator);
                des = calculatorMind.firstDes + lastOperator + getString(R.string.root) + "(" + currentStringToDisplay + ")";
                currentStringToDisplay =  String.valueOf(format.format(sqrtValue));
                isEqual = false;
            }

//            lastOperator = operatorBUTTON.getText().toString();
//            addDescription(lastOperator);

//            description.setText(calculatorMind.getDescription());
            calculatorMind.setDescriptionWithRoot(des);
            description.setText(calculatorMind.getDescription());
            Log.d("tag: ", "" + currentValue);
            displayToScreen.setText(currentStringToDisplay);
        }
    }

    public void acOnClick(View view){
        resetAll();
    }

    public void resetAll(){
        displayToScreen.setText("0");
        currentStringToDisplay = "";
        lastString = "";
        currentValue = 0.0;
        description.setText("");
        lastOperator = "";
        calculatorMind.isPartial = false;
        isDot = true;
        isEqual = true;
        hasOnlyOneOperand = true;
    }

    public Double performCalculation(Double first, Double second, String operator){
        double total = 0.0;
        switch (operator){
            case "+":
                total = first + second;
                break;
            case "-":
                total = first - second;
                break;
            case "*":
                total = first * second;
                break;
            case "/":
                total = first / second;
                break;
            case "%":
                total = first % second;
                break;
            default:
                total = Math.pow(first, second);
                break;

        }

        return total;
    }

    public void currentDisplay(String inputText){
        currentStringToDisplay = currentStringToDisplay + inputText;
        displayToScreen.setText(currentStringToDisplay);
    }

    public void addDescription(String input){
        calculatorMind.setDescription(getCurrentDesText(), input);
    }


    public String getCurrentDesText(){
        return description.getText().toString();
    }

    public void dotOnClick(View view){
        if (isDot == true) {
            addDescription(".");
            description.setText(calculatorMind.getDescription());
            currentDisplay(".");

            isDot = false;
        }
    }

}

//    public void displayOnDescription(String inputText) {
//
//        String previousText = description.getText().toString();
//        String newText = previousText + inputText;
//        description.setText(newText);
//
//    }
