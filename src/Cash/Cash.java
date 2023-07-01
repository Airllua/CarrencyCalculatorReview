package Cash;

import Enums.Currency;
import MyExceptions.*;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class Cash {
    protected double amountOfMoney;
    protected Currency currency;

    public Currency getCurrency() { return currency; }
    public double getAmountOfMoney() { return amountOfMoney; }
    public Cash(double amountOfMoney, Currency currency) {
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
    }


    private static final char dollarIdentifier = '$';
    private static final char rubbleIdentifier = 'р';

    private static final String configurationPath = "src\\Configuration\\config.properties";
    public static double GetConversionCoefficient() {
        Properties properties = new Properties();

        try (FileInputStream configFile = new FileInputStream(configurationPath)) {
            properties.load(configFile);
        } catch (IOException e) { e.printStackTrace(); }

        return Double.parseDouble(properties.getProperty("USD_TO_RUB"));
    }

    public static Cash determineCurrency(String checkedCash) {
        double definiteMoney = Double.parseDouble(checkedCash.replaceAll("[^\\d.,+-]", "")
                .replace(',', '.'));

        if(checkedCash.charAt(0) == dollarIdentifier)
            return new CashInUSD(definiteMoney);
        else if (checkedCash.charAt(checkedCash.length() - 1) == rubbleIdentifier)
            return new CashInRUB(definiteMoney);
        else throw new UnknownCurrencyException();
    }

    public void SumCash(Cash cash) {
        if(this.currency == cash.currency)
            this.amountOfMoney += cash.amountOfMoney;
        else throw new DifferentCurrencyException();
    }

    public void MinusCash(Cash cash) {
        if(this.currency == cash.currency)
            this.amountOfMoney -= cash.amountOfMoney;
        else throw new DifferentCurrencyException();
    }

    public abstract Cash convertCurrency();

    @Override
    public String toString(){
        double roundedResult = Math.round(getAmountOfMoney() * 100.0) / 100.0;
        return roundedResult + " " + getCurrency();
    }
}
