import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public class throws_an_illegal_argument_exception_when {
        @Test
        public void currency_is_not_found() {
            Assertions.fail();
        }
    }

    @Nested
    public class return_converted_value_when {
        
    }
}
