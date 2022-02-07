package com.example.javaactivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int numberOfCoffee = 2;

    public void submitOrder(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"order@coffee.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + UserName());
        emailIntent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Context context = getApplicationContext();
            CharSequence text = "No App found for mailing";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayMessage(createOrderSummary());
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increaseQuantity(View view) {
        if (numberOfCoffee < 10)
            display(++numberOfCoffee);
        else {
            Context context = getApplicationContext();
            CharSequence text = "Order at most 10 coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayPrice(numberOfCoffee);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decreaseQuantity(View view) {
        if (numberOfCoffee > 1)
            display(--numberOfCoffee);
        else {
            Context context = getApplicationContext();
            CharSequence text = "Order at least 1 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayPrice(numberOfCoffee);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantityNo_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the price on the screen.
     */
    private void displayPrice(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.order_summary_text_view);
        quantityTextView.setText("" + getPrice(number) + "/-");
    }

    /**
     * This method displays the total price  on the screen.
     *
     * @param number is the Number of Coffee.
     */
    private String getPrice(int number) {
        int basePrice = 5;
        if (hasWhippedCream())
            basePrice += 1;
        if (hasChocolate())
            basePrice += 2;
        return NumberFormat.getCurrencyInstance().format(basePrice * number);
    }

    /**
     * This method displays the summary of an order on the screen
     */
    private String createOrderSummary() {
        String orderSummary = "";
        String name = "\n" + getString(R.string.name_text, UserName());
        String customized = "\n" + getString(R.string.Whipped_Cream) + hasWhippedCream() + "\n" + getString(R.string.Chocolate) + hasChocolate();
        String quantity = "\n" + getString(R.string.quantity) + numberOfCoffee;
        String total = "\n" + getString(R.string.total) + getPrice(numberOfCoffee) + "/-";
        orderSummary += name + customized + quantity + total + "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

    /**
     * This method display message in order_summary_text_view.
     *
     * @param message is order summary
     */
    private void displayMessage(String message) {
        TextView summary = (TextView) findViewById(R.id.order_summary_text_view);
        summary.setText(message);
    }

    /**
     * This method return whether whipped cream true or false.
     *
     * @return true or false for whipped cream.
     */
    private boolean hasWhippedCream() {
        boolean mhasWhippedCream = false;
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        if (checkBox.isChecked())
            mhasWhippedCream = true;
        return mhasWhippedCream;
    }

    /**
     * This method return whether chocolate true or false.
     */
    private boolean hasChocolate() {
        boolean mhasChocolate = false;
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        if (checkBox.isChecked())
            mhasChocolate = true;
        return mhasChocolate;
    }

    /**
     * This method reads the Input Name from EditView.
     *
     * @return username.
     */
    private String UserName() {
        String userName = "";
        EditText editText = (EditText) findViewById(R.id.name_field);
        userName = editText.getText().toString();
        userName = userName.trim();
        if (userName.isEmpty())
            return "Unknown";
        else
            return userName;
    }

}