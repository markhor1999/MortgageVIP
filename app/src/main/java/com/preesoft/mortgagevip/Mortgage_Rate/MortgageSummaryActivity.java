package com.preesoft.mortgagevip.Mortgage_Rate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.preesoft.mortgagevip.R;

public class MortgageSummaryActivity extends AppCompatActivity {


    private TextView totalMonthlyPayment, downPaymentAmount, endDate,
            monthlyTaxPaid, monthlyHomeInsurance, annualPaymentAmount,
            pmi, downPaymentPercentage,     totalIntersetPaid, totalTaxPaid,
            totalHomeInsurance, totalPayment, month, monhtlyHOA, totalMonthlyMortageg;


    private String sTotalMonthlyPayment, sDownPaymentAmount, sEndDate,
            sMonthlyTaxPaid, sMonthlyHomeInsurance, sAnnualPaymentAmount,
            sPmi, sDownPaymentPercentage, sTotalIntersetPaid, sTotalTaxPaid,
            sTotalHomeInsurance, sTotalPayment, sMonth, sMonthlyHOA, sLoanTermInYear, monthInEng, sTotalMonthlyMortgage;

    float fMonthlyHomeInsurance, fMonthlyTaxPaid, fMonthlyPayment, fTotalMonthlyMortgage;


    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_summary);


        totalMonthlyPayment = findViewById(R.id.totalMonthlyPayment);
        downPaymentAmount = findViewById(R.id.downPayment);
        endDate = findViewById(R.id.endDate);
        monthlyTaxPaid = findViewById(R.id.monthlyTaxPaid);
        monthlyHomeInsurance = findViewById(R.id.monthlyHomeInsurance);
        annualPaymentAmount = findViewById(R.id.annualPaymentAmount);
        pmi = findViewById(R.id.pmi);
        downPaymentPercentage = findViewById(R.id.downPaymentPercentage);
        totalPayment = findViewById(R.id.finalPayment);
        month = findViewById(R.id.month);
        monhtlyHOA = findViewById(R.id.monthlyHOA);
        totalMonthlyMortageg = findViewById(R.id.totalMonthlyMortgage);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            sTotalMonthlyPayment = bundle.getString("total_monthly_payment");
            sDownPaymentAmount = bundle.getString("down_payment");
            sEndDate = bundle.getString("date");
            sMonthlyTaxPaid = bundle.getString("monthly_tax_paid");
            sMonthlyHomeInsurance = bundle.getString("monthly_home_insurance");
            sAnnualPaymentAmount = bundle.getString("annual_payment_amount");
//            sPmi = bundle.getString("");
            sDownPaymentPercentage = bundle.getString("down_payment_percentage");
//            sTotalIntersetPaid = bundle.getString("");
            sTotalTaxPaid = bundle.getString("total_tax_paid");
            sTotalHomeInsurance = bundle.getString("total_home_insurance");
            sTotalPayment = bundle.getString("total_final_payment");
            sMonth = bundle.getString("loan_term_in_month");
            sMonthlyHOA = bundle.getString("monthly_hoa");
            sLoanTermInYear = bundle.getString("loan_term_in_year");



            fMonthlyHomeInsurance = Float.parseFloat(sMonthlyHomeInsurance);
            fMonthlyTaxPaid = Float.parseFloat(sMonthlyTaxPaid);
            fMonthlyPayment = Float.parseFloat(sTotalMonthlyPayment);

            fTotalMonthlyMortgage = fMonthlyHomeInsurance + fMonthlyTaxPaid + fMonthlyPayment;
            sTotalMonthlyMortgage = String.valueOf(fTotalMonthlyMortgage);



            String toSplit = sEndDate;
            String result[] = toSplit.split("-");
            String returnValue = result[result.length - 1];

            String m = result[1];

            if (m.equals("1")) {

                monthInEng = "Dec";


            }
            if (m.equals("2")) {

                monthInEng = "Jan";
            }
            if (m.equals("3")) {

                monthInEng = "Feb";
            }
            if (m.equals("4")) {
                monthInEng = "Mar";

            }

            if (m.equals("5")) {

                monthInEng = "Apr";
            }
            if (m.equals("6")) {

                monthInEng = "May";
            }
            if (m.equals("7")) {
                monthInEng = "Jun";

            }
            if (m.equals("8")) {

                monthInEng = "Jul";
            }
            if (m.equals("9")) {

                monthInEng = "Aug";
            }
            if (m.equals("10")) {
                monthInEng = "Sep";

            }
            if (m.equals("11")) {
                monthInEng = "Oct";

            }
            if (m.equals("12")) {

                monthInEng = "Nov";
            }


            int intValue = Integer.parseInt(returnValue) + Integer.parseInt(sLoanTermInYear);
            date = String.valueOf(intValue);

//            Toast.makeText(this, ""+date, Toast.LENGTH_SHORT).show();
        }


        totalMonthlyPayment.setText("$" + sTotalMonthlyPayment);
        downPaymentAmount.setText("$" + sDownPaymentAmount);
        endDate.setText(monthInEng+", "+date);
        monthlyTaxPaid.setText("$" + sMonthlyTaxPaid);
        annualPaymentAmount.setText("$" + sAnnualPaymentAmount);
        downPaymentPercentage.setText(sDownPaymentPercentage + "%");
        totalPayment.setText("$" + sTotalPayment);
        monthlyHomeInsurance.setText("$" + sMonthlyHomeInsurance);
        month.setText("Total of " + sMonth + " " + "Payments");
        monhtlyHOA.setText("$" + sMonthlyHOA);
        totalMonthlyMortageg.setText("$" + sTotalMonthlyMortgage );


    }
}
