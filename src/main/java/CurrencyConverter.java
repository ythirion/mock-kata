public class CurrencyConverter {
    private final ChangeRateService changeRateService;

    public CurrencyConverter(ChangeRateService changeRateService) {
        this.changeRateService = changeRateService;
    }

    public double convert(
            String fromCurrency,
            String toCurrency,
            double amount) {
        checkAmount(amount);
        var rate = getRate(fromCurrency, toCurrency);
        checkRate(rate);

        var converted = convert(amount, rate);
        checkConversionResult(converted);

        return converted;
    }

    private double convert(double amount, double rate) {
        return amount * rate;
    }

    private void checkConversionResult(double conversion) {
        if (conversion >= Double.MAX_VALUE) {
            throw new IllegalArgumentException("Result out of bound");
        }
    }

    private double getRate(String fromCurrency, String toCurrency) {
        try {
            return changeRateService.getChangeRate(fromCurrency, toCurrency);
        } catch (Exception unknownEx) {
            throw new IllegalArgumentException("An unexpected error has been thrown during the conversion", unknownEx);
        }
    }

    private static void checkRate(double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("Retrieved rate not equal or greater than zero");
        }
    }

    private static void checkAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be equal or greater than zero");
        }
    }
}