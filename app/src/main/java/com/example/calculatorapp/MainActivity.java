package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView txtDisplay;
    private double FirstNumber = 0;
    private String Operator = "";
    private boolean IsNewNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);
                    return insets;
                });

        txtDisplay = findViewById(R.id.txtDisplay);

        SetDigitButton(R.id.btn0, "0");
        SetDigitButton(R.id.btn1, "1");
        SetDigitButton(R.id.btn2, "2");
        SetDigitButton(R.id.btn3, "3");
        SetDigitButton(R.id.btn4, "4");
        SetDigitButton(R.id.btn5, "5");
        SetDigitButton(R.id.btn6, "6");
        SetDigitButton(R.id.btn7, "7");
        SetDigitButton(R.id.btn8, "8");
        SetDigitButton(R.id.btn9, "9");

        SetOperationButton(R.id.btnAdd, "+");
        SetOperationButton(R.id.btnSubtract, "-");
        SetOperationButton(R.id.btnMultiply, "*");
        SetOperationButton(R.id.btnDivide, "/");

        findViewById(R.id.btnEquals).setOnClickListener(v -> CalculateResult());

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            txtDisplay.setText("0");
            FirstNumber = 0;
            Operator = "";
            IsNewNumber = true;
        });

        findViewById(R.id.btnCE).setOnClickListener(v -> {
            txtDisplay.setText("0");
            IsNewNumber = true;
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            String CurrentText = txtDisplay.getText().toString();

            if (CurrentText.length() > 1) {
                txtDisplay.setText(
                        CurrentText.substring(0, CurrentText.length() - 1));
            } else {
                txtDisplay.setText("0");
            }
        });

        findViewById(R.id.btnSign).setOnClickListener(v -> {
            double Number = Double.parseDouble(
                    txtDisplay.getText().toString());
            txtDisplay.setText(FormatNumber(-Number));
        });

        findViewById(R.id.btnSqrt).setOnClickListener(v -> {
            double Number = Double.parseDouble(
                    txtDisplay.getText().toString());

            if (Number >= 0) {
                txtDisplay.setText(FormatNumber(Math.sqrt(Number)));
            } else {
                txtDisplay.setText("Error");
            }
            IsNewNumber = true;
        });

        findViewById(R.id.btnDecimal).setOnClickListener(v -> {
            if (IsNewNumber) {
                txtDisplay.setText("0.");
                IsNewNumber = false;
            } else if (!txtDisplay.getText().toString().contains(".")) {
                txtDisplay.append(".");
            }
        });
    }

    private void SetDigitButton(int ButtonId, String Digit) {
        Button DigitButton = findViewById(ButtonId);

        DigitButton.setOnClickListener(v -> {
            if (IsNewNumber || txtDisplay.getText().toString().equals("0")
                    || txtDisplay.getText().toString().equals("Error")) {
                txtDisplay.setText(Digit);
                IsNewNumber = false;
            } else {
                txtDisplay.append(Digit);
            }
        });
    }

    private void SetOperationButton(int ButtonId, String NewOperator) {
        findViewById(ButtonId).setOnClickListener(v -> {
            FirstNumber = Double.parseDouble(
                    txtDisplay.getText().toString());
            Operator = NewOperator;
            IsNewNumber = true;
        });
    }

    private void CalculateResult() {
        if (Operator.isEmpty()) {
            return;
        }

        double SecondNumber = Double.parseDouble(
                txtDisplay.getText().toString());

        double Result;

        switch (Operator) {
            case "+":
                Result = FirstNumber + SecondNumber;
                break;
            case "-":
                Result = FirstNumber - SecondNumber;
                break;
            case "*":
                Result = FirstNumber * SecondNumber;
                break;
            case "/":
                if (SecondNumber == 0) {
                    txtDisplay.setText("Error");
                    Operator = "";
                    IsNewNumber = true;
                    return;
                }
                Result = FirstNumber / SecondNumber;
                break;
            default:
                return;
        }

        txtDisplay.setText(FormatNumber(Result));
        Operator = "";
        IsNewNumber = true;
    }

    private String FormatNumber(double Number) {
        if (Number == (long) Number) {
            return String.valueOf((long) Number);
        }
        return String.valueOf(Number);
    }
}
