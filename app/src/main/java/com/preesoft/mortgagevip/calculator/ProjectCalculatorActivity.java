package com.preesoft.mortgagevip.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.preesoft.mortgagevip.R;

public class ProjectCalculatorActivity extends AppCompatActivity {


    private EditText purchasePrice, constructionPrice, acquisitionPercentage, constructionPercentage, sale,
            months;
    private TextView totalCost, initialLoan, constructionLoan, total, dealPurchasePrice, closingCost,
            dealConstruction;
    private float pPrice, cPrice, cPercentage, aPercentage, aPercentageValue, cPercentageValue,
            totalValue, fClosingCost, fSale, fProfitValue, allSum, s, fRate, fPoint, fMonths, x, fEquity,
            fPojectedReturn, fInterest, fClosing, fDealPurchasePrice, fEquityReturn, fLoanToValue ;
    private String cPriceValue, sPercentageValue, sAPercentageValue, sDealPurchasePrice, sLoanToCost,
            sRateSpinner, sPointSpinner, sMonths, sEquity;
    private TextView closingCostTv, interestTv, allCost, loanToCost, loanToValue, equityReturn;
    private TextView returnProfit, projectedReturn, loan, interest, points, equity;
    private Spinner rateSpinner, pointSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_calculator);

        purchasePrice = findViewById(R.id.purchasePrice);
        constructionPrice = findViewById(R.id.constructionPrice);
        totalCost = findViewById(R.id.totalCost);
        acquisitionPercentage = findViewById(R.id.acquisitionPercentage);
        constructionPercentage = findViewById(R.id.constructionPercentage);
        initialLoan = findViewById(R.id.initialLoan);
        constructionLoan = findViewById(R.id.constructionLoan);
        total = findViewById(R.id.total);
        dealPurchasePrice = findViewById(R.id.dealPurchasePrice);
        closingCost = findViewById(R.id.closingCost);
        dealConstruction = findViewById(R.id.dealConsturction);
        closingCostTv = findViewById(R.id.closingCostTv);
        interestTv = findViewById(R.id.interestTv);
        allCost = findViewById(R.id.allCost);
        sale = findViewById(R.id.sale);
        returnProfit = findViewById(R.id.returnProfit);
        projectedReturn = findViewById(R.id.projectedReturn);
        loanToCost = findViewById(R.id.loanToCost);
        loanToValue = findViewById(R.id.loanToValue);
        rateSpinner = findViewById(R.id.rateSpinner);
        pointSpinner = findViewById(R.id.pointSpinner);
        loan = findViewById(R.id.loan);
        interest = findViewById(R.id.interest);
        months = findViewById(R.id.months);
        points = findViewById(R.id.points);
        equity = findViewById(R.id.equity);
        equityReturn = findViewById(R.id.equityReturn);


        calculateValues();


    }

    private void calculateValues() {


        rateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (rateSpinner.getSelectedItemPosition() > 0) {
                    sRateSpinner = rateSpinner.getSelectedItem().toString();
                    sRateSpinner = sRateSpinner.substring(0, sRateSpinner.length() - 1);
                    fRate = Float.parseFloat(sRateSpinner);
                } else {
                }

                sMonths = months.getText().toString();
                fMonths = Float.parseFloat(sMonths);

                float iRate = (fRate * totalValue / 100) * (fMonths / 12);

                String i = String.format("%2.02f", iRate);
                interest.setText(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        pointSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (pointSpinner.getSelectedItemPosition() > 0) {
                    sPointSpinner = pointSpinner.getSelectedItem().toString();

                    sPointSpinner = sPointSpinner.substring(0, 1);

//                    Toast.makeText(ProjectCalculatorActivity.this, "" + sPointSpinner, Toast.LENGTH_SHORT).show();
                    fPoint = Float.parseFloat(sPointSpinner);
                } else {
                }


                float iPoint = fPoint * totalValue / 100;
                 x = 0;
                 x = totalValue + iPoint;

                String i = String.format("%2.02f", iPoint);
                String x1 = String.format("%2.02f", x);

                points.setText(i);
                loan.setText(x1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        purchasePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pPrice = 0;
                try {
                    pPrice = Float.parseFloat(purchasePrice.getText().toString());


                } catch (NumberFormatException e) {

                    totalCost.setText("");

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pPrice = 0;
                try {
                    pPrice = Float.parseFloat(purchasePrice.getText().toString());


                    fClosingCost = pPrice * 2 / 100;
                    String sCost = String.valueOf(fClosingCost);

                    sDealPurchasePrice = String.format("%2.02f", pPrice);
                    dealPurchasePrice.setText("$" + sDealPurchasePrice);
                    closingCost.setText("$" + sCost);
                } catch (NumberFormatException e) {

                    totalCost.setText("0.00");
                    dealPurchasePrice.setText("0.00");
                    closingCost.setText("0.00");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        constructionPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cPrice = 0;
                try {
                    cPrice = Float.parseFloat(constructionPrice.getText().toString());
                    s = cPrice + pPrice;
                    cPriceValue = String.format("%2.02f", s);


                    totalCost.setText("$" + cPriceValue);
                } catch (NumberFormatException e) {

                    totalCost.setText("0.00");

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cPrice = 0;
                try {
                    cPrice = Float.parseFloat(constructionPrice.getText().toString());

                    s = cPrice + pPrice;
                    cPriceValue = String.format("%2.02f", s);
                    String c = String.format("%2.02f", cPrice);
                    dealConstruction.setText("$" + c);

                    totalCost.setText("$" + cPriceValue);
                } catch (NumberFormatException e) {

                    totalCost.setText("0.00");
                    dealConstruction.setText("0.00");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        acquisitionPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                aPercentage = 0;
                try {
                    aPercentage = Float.parseFloat(acquisitionPercentage.getText().toString());
                    aPercentageValue = pPrice * aPercentage / 100;
                    sAPercentageValue = String.format("%2.02f", aPercentageValue);

                    fEquity = pPrice + fClosingCost - aPercentageValue;
                    sEquity = String.valueOf(fEquity);
                    equity.setText(sEquity);

                    initialLoan.setText("$" + sAPercentageValue);
                } catch (NumberFormatException e) {

                    initialLoan.setText("0.00");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        constructionPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cPercentage = 0;
                try {
                    cPercentage = Float.parseFloat(constructionPercentage.getText().toString());
                    cPercentageValue = cPrice * cPercentage / 100;
                    sPercentageValue = String.format("%2.02f", cPercentageValue);
                    constructionLoan.setText("$" + sPercentageValue);

                    totalValue = aPercentageValue + cPercentageValue;


                    float fLoanToCost = 100 * totalValue / s;

                    sLoanToCost = String.format("%2.02f", fLoanToCost);


                    String tValue = String.format("%2.02f", totalValue);

                    total.setText("$" + tValue);
                    loan.setText("$" + tValue);

                    loanToCost.setText(sLoanToCost + "%");




                } catch (NumberFormatException e) {

                    constructionLoan.setText("0.00");
                    loanToCost.setText("0.00");
                    projectedReturn.setText("0.00%");


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        sale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                fSale = 0;
                try {

                    fSale = Float.parseFloat(sale.getText().toString());


                    float a = fSale * 7 / 100;
                    String b = String.valueOf(a);
                    closingCostTv.setText("$" + b);


//                    float pRetrun = 100 * fSaleValue / allSum;
//                    String p = String.format("%2.02f", pRetrun);

                    String interestSplit = interestTv.getText().toString();

                    String interestResult = interestSplit.substring(interestSplit.lastIndexOf("$") + 1);

                    fInterest = Float.parseFloat(interestResult);

                    String closingSplit = closingCostTv.getText().toString();

                    String closingResult = closingSplit.substring(closingSplit.lastIndexOf("$") + 1);

                    fClosing = Float.parseFloat(closingResult);

                    fDealPurchasePrice = Float.parseFloat(sDealPurchasePrice);

                    allSum = fDealPurchasePrice + fClosingCost + cPrice + fInterest + fClosing;
                    String sAllSum = String.valueOf(allSum);

                    allCost.setText("$" + sAllSum);


                    fProfitValue = fSale - allSum;
                    String d = String.format("%2.02f", fProfitValue);
                    returnProfit.setText("$" + d);

                    fPojectedReturn = pPrice + fClosingCost + cPrice + fInterest;
                    fPojectedReturn = 100 * fProfitValue / fPojectedReturn;
                    String x = String.format("%2.02f",fPojectedReturn);
                    projectedReturn.setText(x + "%");
//                    projectedReturn.setText("$" + p + "%");


                    fEquityReturn =  100 * fProfitValue / fEquity ;
                    String sER = String.format("%2.02f", fEquityReturn);
                    equityReturn.setText(sER + "%");


                    fLoanToValue =100 * totalValue / fSale;
                    String sLTV= String.format("%2.02f", fLoanToValue);
                    loanToValue.setText(sLTV + "%");


                } catch (NumberFormatException e) {

                    returnProfit.setText("0.00");
                    projectedReturn.setText("0.00");
                    closingCostTv.setText("0.00");

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        months.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try{
                    sMonths = months.getText().toString();
                    fMonths = Float.parseFloat(sMonths);

                    float iRate = (fRate * totalValue / 100) * (fMonths / 12);

                    String i = String.format("%2.02f", iRate);
                    interest.setText(i);

                } catch (NumberFormatException e){}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
