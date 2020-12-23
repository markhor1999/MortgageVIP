package com.preesoft.mortgagevip.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.preesoft.mortgagevip.Dashboard.HomeDashboradActivity;
import com.preesoft.mortgagevip.Mortgage_Rate.MortgageSummaryActivity;
import com.preesoft.mortgagevip.R;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText date;
    final Calendar[] calendar = new Calendar[1];
    final DatePickerDialog[] datePickerDialog = new DatePickerDialog[1];
    private Toolbar toolbar;
    private Button calculateBtn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextInputEditText homeValue, downPayment, loanAmount, interestRate,
            loanTerm, propertyTax, pmi, homeInsurance, monthlyHoa;

    private int day, month, year;
    private float intersetRateValue, floatMonthlyHOA, monthlyIntersetRate, monthlyPropertyTax, monthlyPMI, homeInsuranceMonthly,
            loanPercentage, finalPaymentResult, intDownPayment, intHomeValue, floatLoanAmount;
    private int percentageHomeValue;

    private int hValue, loanTermsInMonths, downPaymentValue, loanValue, monthlyIntersetValue;
    private String monthlyInterset, sLoanPercentage, sLoanAmount, loanAmountValue, loanTermValue, dateValue, sHomeValue,
            sDownPayment, sPropertyTax, sPMI, sHomeInsurance;

    private Spinner rafiOrBuy, laonType, creditRating;
    private String sRafi, sLoanType, sCrefitRating, loanTermInYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        date = findViewById(R.id.startDate);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        calculateBtn = findViewById(R.id.calculateBtn);
        homeValue = findViewById(R.id.homeValue);
        downPayment = findViewById(R.id.downPayment);
        loanAmount = findViewById(R.id.loanAmount);
        interestRate = findViewById(R.id.interestRate);
        loanTerm = findViewById(R.id.loanTerm);
        propertyTax = findViewById(R.id.propertyTax);
        pmi = findViewById(R.id.pmi);
        homeInsurance = findViewById(R.id.homeInsurance);
        monthlyHoa = findViewById(R.id.monthlyHoa);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton = findViewById(R.id.dollar);
        rafiOrBuy = findViewById(R.id.rafiOrBuy);
        laonType = findViewById(R.id.loanType);
        creditRating = findViewById(R.id.creditRating);

        radioButton.setChecked(true);

        if (radioButton.isChecked()) {

            loanAmountWithDollar();


        }

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalculatorActivity.this, HomeDashboradActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar[0] = Calendar.getInstance();
                day = calendar[0].get(Calendar.DAY_OF_MONTH);
                month = calendar[0].get(Calendar.MONTH);
                year = calendar[0].get(Calendar.YEAR);

                datePickerDialog[0] = new DatePickerDialog(CalculatorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                    }
                }, day, month, year);
                datePickerDialog[0].getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog[0].show();
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dollar:


                        loanAmountWithDollar();


                        break;
                    case R.id.percentage:

                        loanAmountWithPercentage();

                        break;
                }
            }
        });

        calculateBtn.setOnClickListener(this);

    }

    private void loanAmountWithDollar() {


        homeValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hValue = 0;
                try {
                    hValue = Integer.parseInt(homeValue.getText().toString());
                } catch (NumberFormatException e) {

                    loanAmount.setText("");

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hValue = 0;
                try {
                    hValue = Integer.parseInt(homeValue.getText().toString());
                } catch (NumberFormatException e) {

                    loanAmount.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        downPayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                downPaymentValue = 0;
                try {
                    downPaymentValue = Integer.parseInt(downPayment.getText().toString());
                    loanAmountValue = String.valueOf(hValue - downPaymentValue);


                    loanAmount.setText(loanAmountValue);
                } catch (NumberFormatException e) {

                    loanAmount.setText("");

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                downPaymentValue = 0;
                try {
                    downPaymentValue = Integer.parseInt(downPayment.getText().toString());
                    loanAmountValue = String.valueOf(hValue - downPaymentValue);


                    loanAmount.setText(loanAmountValue);
                } catch (NumberFormatException e) {

                    loanAmount.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    private void loanAmountWithPercentage() {


        homeValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hValue = 0;
                try {
                    hValue = Integer.parseInt(homeValue.getText().toString());
                } catch (NumberFormatException e) {

                    loanAmount.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        downPayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                downPaymentValue = 0;
                try {
                    downPaymentValue = Integer.parseInt(downPayment.getText().toString());


                    percentageHomeValue = hValue * downPaymentValue / 100;


                    loanAmountValue = String.valueOf(hValue - percentageHomeValue);




                    loanAmount.setText(loanAmountValue);
                } catch (NumberFormatException e) {

                    loanAmount.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == calculateBtn) {


            if (radioButton.isChecked()) {
                getCalculatedMortgage();
            } else {

                getCalculatedMortgagePercentage();
            }
        }
    }

    private void getCalculatedMortgagePercentage() {


        //getting loan amount
        sLoanAmount = loanAmount.getText().toString();


        //get monthly interest rate
        String insRate = interestRate.getText().toString();

        if (insRate.equals("")) {

            interestRate.setError("Enter interest rate");

        } else {
            intersetRateValue = Float.parseFloat(insRate);
            int percentageMonthly = 100 * 12;
            monthlyIntersetRate = intersetRateValue / percentageMonthly;
        }

        //getting value from loan term
        loanTermValue = loanTerm.getText().toString();
        if (loanTermValue.equals("")) {

            loanTerm.setError("Enter loan term");
        }

        //getting date
        dateValue = date.getText().toString();

        if (dateValue.equals("")) {


            date.setError("Select start date");

        }

        //getting homeValue
        sHomeValue = homeValue.getText().toString();
        if (sHomeValue.equals("")) {

            homeValue.setError("Enter home value");

        }

        //getting DownPayment
        sDownPayment = downPayment.getText().toString();
        if (sDownPayment.equals("")) {

            downPayment.setError("Enter down payment");

        }


        //getting Property Tax
        sPropertyTax = propertyTax.getText().toString();
        if (sPropertyTax.equals("")) {

            propertyTax.setError("Enter property tax");

        } else {

            float vPropertyTax = Float.parseFloat(sPropertyTax);
            monthlyPropertyTax = vPropertyTax / 12;

        }


        if (sDownPayment.equals("") || sHomeValue.equals("")) {


        } else {
            intDownPayment = Float.parseFloat(sDownPayment);
            intHomeValue = Float.parseFloat(sHomeValue);
        }


        //getting homeInsurance
        sHomeInsurance = homeInsurance.getText().toString();
        if (sHomeInsurance.equals("")) {

            homeInsurance.setError("Enter home insurance");

        } else {

            float vHomeInsurance = Float.parseFloat(sHomeInsurance);
            homeInsuranceMonthly = vHomeInsurance / 12;

        }

        //getting monthlyHOA
        String sMonthlyHoa = monthlyHoa.getText().toString();
        if (sMonthlyHoa.equals("")) {


            monthlyHoa.setError("Enter monthly HOA");

        } else {

            floatMonthlyHOA = Float.parseFloat(sMonthlyHoa);

        }


        // Applying mortgage formula
        if (sLoanAmount.equals("")) {

        } else {

            floatLoanAmount = Float.parseFloat(sLoanAmount);
        }
        float result = 1 + monthlyIntersetRate;
        if (loanTermValue.equals("") || sMonthlyHoa.equals("") || sHomeInsurance.equals("")) {


            Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show();

        } else {
            int intLoanTerm = Integer.parseInt(loanTermValue);

            loanTermsInMonths = intLoanTerm * 12;

            float result1 = (float) Math.pow(result, loanTermsInMonths);
            float result2 = monthlyIntersetRate * result1;
            float result3 = result1 - 1;

            float result4 = result2 / result3;
            float result5 = floatLoanAmount * result4;

            String resultValue = String.format("%2.02f", result5);

            float resultValueInFloat = Float.parseFloat(resultValue);

            finalPaymentResult = resultValueInFloat + homeInsuranceMonthly + monthlyPropertyTax + floatMonthlyHOA;


            String totalMonthlyPayment = String.format("%2.02f", finalPaymentResult);
            float annualPaymentAmount = finalPaymentResult * 12;

            float totalTaxPaid = monthlyPropertyTax * loanTermsInMonths;
            float totalHomeInsurance = homeInsuranceMonthly * loanTermsInMonths;
            float totalPayment = finalPaymentResult * loanTermsInMonths;
            int monthlyHOA = Integer.parseInt(sMonthlyHoa);
            float mHoa = monthlyHOA * loanTermsInMonths;

            String hoa = String.valueOf(mHoa);

            String tTaxPaid = String.valueOf(totalTaxPaid);
            String tHomeInsurance = String.valueOf(totalHomeInsurance);
            String tPayment = String.valueOf(totalPayment);
            String mTaxPaid = String.valueOf(monthlyPropertyTax);
            String aPaymentAmount = String.format("%2.02f", annualPaymentAmount);
            String sLoanTermsMonth = String.valueOf(loanTermsInMonths);
            String sHomeInsurance = String.format("%2.02f", homeInsuranceMonthly);
            String pHomeValue = String.valueOf(percentageHomeValue);


            loanPercentage = percentageHomeValue / intHomeValue * 100;
            sLoanPercentage = String.format("%2.02f", loanPercentage);
//        Toast.makeText(this, ""+ sLoanPercentage, Toast.LENGTH_SHORT).show();


            //getting PMI
            float checkPmiPercentage = Float.parseFloat(sLoanPercentage);
            sPMI = pmi.getText().toString();
            if (checkPmiPercentage < 20.00) {

                if (sPMI.equals("")) {

                    pmi.setError("Enter PMI");

                } else {


                    Intent calculatorIntent = new Intent(CalculatorActivity.this, MortgageSummaryActivity.class);
                    calculatorIntent.putExtra("total_monthly_payment", totalMonthlyPayment);
                    calculatorIntent.putExtra("down_payment", pHomeValue);
                    calculatorIntent.putExtra("date", dateValue);
                    calculatorIntent.putExtra("monthly_tax_paid", mTaxPaid);
                    calculatorIntent.putExtra("monthly_home_insurance", sHomeInsurance);
                    calculatorIntent.putExtra("annual_payment_amount", aPaymentAmount);
                    calculatorIntent.putExtra("down_payment_percentage", sDownPayment);
                    calculatorIntent.putExtra("total_tax_paid", tTaxPaid);
                    calculatorIntent.putExtra("total_home_insurance", tHomeInsurance);
                    calculatorIntent.putExtra("total_final_payment", tPayment);
                    calculatorIntent.putExtra("loan_term_in_month", sLoanTermsMonth);
                    calculatorIntent.putExtra("monthly_hoa", hoa);
                    calculatorIntent.putExtra("loan_term_in_year", loanTermValue);
                    startActivity(calculatorIntent);
//                    sPMI = pmi.getText().toString();
//                    float pmiPercentage = Float.parseFloat(sPMI);
//                    float fLoanAmount = Float.parseFloat(sLoanAmount);
//
//                    int pmiMonthly = 100 * 12;
//
//                    monthlyPMI = pmiPercentage / pmiMonthly * fLoanAmount;
                }
            } else {


                Intent calculatorIntent = new Intent(CalculatorActivity.this, MortgageSummaryActivity.class);
                calculatorIntent.putExtra("total_monthly_payment", totalMonthlyPayment);
                calculatorIntent.putExtra("down_payment", pHomeValue);
                calculatorIntent.putExtra("date", dateValue);
                calculatorIntent.putExtra("monthly_tax_paid", mTaxPaid);
                calculatorIntent.putExtra("monthly_home_insurance", sHomeInsurance);
                calculatorIntent.putExtra("annual_payment_amount", aPaymentAmount);
                calculatorIntent.putExtra("down_payment_percentage", sDownPayment);
                calculatorIntent.putExtra("total_tax_paid", tTaxPaid);
                calculatorIntent.putExtra("total_home_insurance", tHomeInsurance);
                calculatorIntent.putExtra("total_final_payment", tPayment);
                calculatorIntent.putExtra("loan_term_in_month", sLoanTermsMonth);
                calculatorIntent.putExtra("monthly_hoa", hoa);
                calculatorIntent.putExtra("loan_term_in_year", loanTermValue);
                startActivity(calculatorIntent);

            }
        }


    }


    private void getCalculatedMortgage() {


        //getting loan amount
        sLoanAmount = loanAmount.getText().toString();


        //get monthly interest rate
        String insRate = interestRate.getText().toString();

        if (insRate.equals("")) {

            interestRate.setError("Enter interest rate");

        } else {
            intersetRateValue = Float.parseFloat(insRate);
            int percentageMonthly = 100 * 12;
            monthlyIntersetRate = intersetRateValue / percentageMonthly;
        }

        //getting value from loan term
        loanTermValue = loanTerm.getText().toString();
        if (loanTermValue.equals("")) {

            loanTerm.setError("Enter loan term");
        }

        //getting date
        dateValue = date.getText().toString();

        if (dateValue.equals("")) {


            date.setError("Select start date");

        }

        //getting homeValue
        sHomeValue = homeValue.getText().toString();
        if (sHomeValue.equals("")) {

            homeValue.setError("Enter home value");

        }

        //getting DownPayment
        sDownPayment = downPayment.getText().toString();
        if (sDownPayment.equals("")) {

            downPayment.setError("Enter down payment");

        }


        //getting Property Tax
        sPropertyTax = propertyTax.getText().toString();
        if (sPropertyTax.equals("")) {

            propertyTax.setError("Enter property tax");

        } else {

            float vPropertyTax = Float.parseFloat(sPropertyTax);
            monthlyPropertyTax = vPropertyTax / 12;

        }


        if (sDownPayment.equals("") || sHomeValue.equals("")) {


        } else {
            intDownPayment = Float.parseFloat(sDownPayment);
            intHomeValue = Float.parseFloat(sHomeValue);
        }
        loanPercentage = intDownPayment / intHomeValue * 100;
        sLoanPercentage = String.format("%2.02f", loanPercentage);
//        Toast.makeText(this, ""+ sLoanPercentage, Toast.LENGTH_SHORT).show();


        //getting homeInsurance
        sHomeInsurance = homeInsurance.getText().toString();
        if (sHomeInsurance.equals("")) {

            homeInsurance.setError("Enter home insurance");

        } else {

            float vHomeInsurance = Float.parseFloat(sHomeInsurance);
            homeInsuranceMonthly = vHomeInsurance / 12;

        }

        //getting monthlyHOA
        String sMonthlyHoa = monthlyHoa.getText().toString();
        if (sMonthlyHoa.equals("")) {


            monthlyHoa.setError("Enter monthly HOA");

        } else {

            floatMonthlyHOA = Float.parseFloat(sMonthlyHoa);

        }


        // Applying mortgage formula

        if (sLoanAmount.equals("")) {

        } else {

            floatLoanAmount = Float.parseFloat(sLoanAmount);
        }
        float result = 1 + monthlyIntersetRate;
        if (loanTermValue.equals("") || sMonthlyHoa.equals("") || sHomeInsurance.equals("")) {


            Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show();

        } else {
            int intLoanTerm = Integer.parseInt(loanTermValue);

            loanTermsInMonths = intLoanTerm * 12;

            float result1 = (float) Math.pow(result, loanTermsInMonths);
            float result2 = monthlyIntersetRate * result1;
            float result3 = result1 - 1;

            float result4 = result2 / result3;
            float result5 = floatLoanAmount * result4;



            String resultValue = String.format("%2.02f", result5);

            float resultValueInFloat = Float.parseFloat(resultValue);

            finalPaymentResult = resultValueInFloat + homeInsuranceMonthly + monthlyPropertyTax + floatMonthlyHOA;


            String totalMonthlyPayment = String.format("%2.02f", finalPaymentResult);
            float annualPaymentAmount = finalPaymentResult * 12;

            float totalTaxPaid = monthlyPropertyTax * loanTermsInMonths;
            float totalHomeInsurance = homeInsuranceMonthly * loanTermsInMonths;
            float totalPayment = finalPaymentResult * loanTermsInMonths;
            int monthlyHOA = Integer.parseInt(sMonthlyHoa);
            float mHoa = monthlyHOA * loanTermsInMonths;

            String hoa = String.valueOf(mHoa);

            String tTaxPaid = String.valueOf(totalTaxPaid);
            String tHomeInsurance = String.valueOf(totalHomeInsurance);
            String tPayment = String.valueOf(totalPayment);
            String mTaxPaid = String.valueOf(monthlyPropertyTax);
            String aPaymentAmount = String.format("%2.02f", annualPaymentAmount);
            String sLoanTermsMonth = String.valueOf(loanTermsInMonths);
            String sHomeInsurance = String.format("%2.02f", homeInsuranceMonthly);


            //getting PMI
            float checkPmiPercentage = Float.parseFloat(sLoanPercentage);
            sPMI = pmi.getText().toString();

            if (checkPmiPercentage < 20.00) {
                if (sPMI.equals("")) {

                    pmi.setError("Enter PMI");

                }else {

                    Intent calculatorIntent = new Intent(CalculatorActivity.this, MortgageSummaryActivity.class);
                    calculatorIntent.putExtra("total_monthly_payment", totalMonthlyPayment);
                    calculatorIntent.putExtra("down_payment", sDownPayment);
                    calculatorIntent.putExtra("date", dateValue);
                    calculatorIntent.putExtra("monthly_tax_paid", mTaxPaid);
                    calculatorIntent.putExtra("monthly_home_insurance", sHomeInsurance);
                    calculatorIntent.putExtra("annual_payment_amount", aPaymentAmount);
                    calculatorIntent.putExtra("down_payment_percentage", sLoanPercentage);
                    calculatorIntent.putExtra("total_tax_paid", tTaxPaid);
                    calculatorIntent.putExtra("total_home_insurance", tHomeInsurance);
                    calculatorIntent.putExtra("total_final_payment", tPayment);
                    calculatorIntent.putExtra("loan_term_in_month", sLoanTermsMonth);
                    calculatorIntent.putExtra("monthly_hoa", hoa);
                    calculatorIntent.putExtra("loan_term_in_year", loanTermValue);
                    startActivity(calculatorIntent);
                }
//                else {
//                    sPMI = pmi.getText().toString();
//
//                    float pmiPercentage = Float.parseFloat(sPMI);
//                    float fLoanAmount = Float.parseFloat(sLoanAmount);
//
//                    int pmiMonthly = 100 * 12;
//
//                    monthlyPMI = pmiPercentage / pmiMonthly * fLoanAmount;
//
//
//                }

            } else  {


                Intent calculatorIntent = new Intent(CalculatorActivity.this, MortgageSummaryActivity.class);
                calculatorIntent.putExtra("total_monthly_payment", totalMonthlyPayment);
                calculatorIntent.putExtra("down_payment", sDownPayment);
                calculatorIntent.putExtra("date", dateValue);
                calculatorIntent.putExtra("monthly_tax_paid", mTaxPaid);
                calculatorIntent.putExtra("monthly_home_insurance", sHomeInsurance);
                calculatorIntent.putExtra("annual_payment_amount", aPaymentAmount);
                calculatorIntent.putExtra("down_payment_percentage", sLoanPercentage);
                calculatorIntent.putExtra("total_tax_paid", tTaxPaid);
                calculatorIntent.putExtra("total_home_insurance", tHomeInsurance);
                calculatorIntent.putExtra("total_final_payment", tPayment);
                calculatorIntent.putExtra("loan_term_in_month", sLoanTermsMonth);
                calculatorIntent.putExtra("monthly_hoa", hoa);
                calculatorIntent.putExtra("loan_term_in_year", loanTermValue);
                startActivity(calculatorIntent);

            }


        }


    }
}



