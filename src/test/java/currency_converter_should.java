import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class currency_converter_should {
    private final static String FROM_CURRENCY = "USD";
    private final static String TO_CURRENCY = "EUR";

    private CurrencyConverter currencyConverter;

    @Mock
    private ChangeRateService changeRateService;

    @BeforeEach
    public void init() {
        currencyConverter = new CurrencyConverter(changeRateService);
    }

    @Nested
    public class throw_an_illegal_argument_exception_when {
        @Test
        public void currency_is_not_found() {
            when(changeRateService.getChangeRate(anyString(), anyString())).thenThrow(new IllegalArgumentException("Currency not found"));

            var exception = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> currencyConverter.convert(FROM_CURRENCY, TO_CURRENCY, 9));

            Assertions.assertEquals("An unexpected error has been thrown during the conversion", exception.getMessage());
        }

        @Test
        public void amount_to_convert_lower_than_0() {
            var exception = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> currencyConverter.convert(FROM_CURRENCY, TO_CURRENCY, -1));

            Assertions.assertEquals("Amount must be equal or greater than zero", exception.getMessage());
        }

        @Test
        public void change_rate_lower_than_0() {
            when(changeRateService.getChangeRate(anyString(), anyString())).thenReturn(-1.78);

            var exception = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> currencyConverter.convert(FROM_CURRENCY, TO_CURRENCY, 6767));

            Assertions.assertEquals("Retrieved rate not equal or greater than zero", exception.getMessage());
        }

        @Test
        public void result_greater_than_max_value() {
            when(changeRateService.getChangeRate(anyString(), anyString())).thenReturn(Double.MAX_VALUE);

            var exception = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> currencyConverter.convert(FROM_CURRENCY, TO_CURRENCY, 6767));

            Assertions.assertEquals("Result out of bound", exception.getMessage());
        }
    }

    @Nested
    public class return_converted_value_when {
        @Test
        public void change_rate_found_and_valid() {
            when(changeRateService.getChangeRate(anyString(), anyString())).thenReturn(0.89);
            var result = currencyConverter.convert(FROM_CURRENCY, TO_CURRENCY, 4);
            Assertions.assertEquals(3.56, result);
        }
    }
}