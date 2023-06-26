package Cash;

import Enums.Currency;
public class CashInUSD extends Cash {
    public CashInUSD(double amountOfMoney) {
        super(amountOfMoney, Currency.USD);
    }

    @Override
    public CashInRUB convertCurrency() {
        return new CashInRUB(this.amountOfMoney * Cash.GetConversionCoefficient());
    }
}