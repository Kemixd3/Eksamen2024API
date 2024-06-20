package dto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ProductOrderRequest {

    private BigDecimal quantity;
    private Long productId;
}
