package Cash;

import Enums.Currency;
public class CashInRUB extends Cash {
    public CashInRUB(double amountOfMoney) {
        super(amountOfMoney, Currency.RUB);
    }

    @Override
    public CashInUSD convertCurrency() {
        return new CashInUSD(this.amountOfMoney / Cash.GetConversionCoefficient());
    }
}

