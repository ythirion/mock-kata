import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class currency_converter_should {
    @BeforeEach
    public void init() {

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
