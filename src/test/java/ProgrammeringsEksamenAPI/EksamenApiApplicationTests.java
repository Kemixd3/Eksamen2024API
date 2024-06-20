/*package ProgrammeringsEksamenAPI;

import ProgrammeringsEksamenAPI.models.Delivery;
import ProgrammeringsEksamenAPI.models.Product;
import ProgrammeringsEksamenAPI.models.ProductOrder;
import ProgrammeringsEksamenAPI.repository.DeliveryRepository;
import ProgrammeringsEksamenAPI.repository.ProductOrderRepository;
import ProgrammeringsEksamenAPI.repository.ProductRepository;
import ProgrammeringsEksamenAPI.services.DeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;*/

import static org.assertj.core.api.Assertions.assertThat;

/*@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional*/
/*public class JavaSecurityApiApplicationTests {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateDelivery() {
        // Create Product
        Product product = new Product();
        product.setName("Product 1");
        product.setWeight(new BigDecimal("10.5"));
        product.setPrice(new BigDecimal("100.0"));
        product = productRepository.save(product);

        // Create ProductOrder
        ProductOrder productOrder = new ProductOrder();
        productOrder.setQuantity(new BigDecimal("2"));
        productOrder.setProduct(product);

        // Create Delivery
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(new Date());
        delivery.setFromWarehouse("Warehouse 1");
        delivery.setDestination(Delivery.Locations.house1);

        // Call service method to create delivery
        delivery = deliveryService.createDelivery(delivery, List.of(productOrder));

        // Assertions
        assertThat(delivery.getId()).isNotNull();
        assertThat(delivery.getProductOrders()).hasSize(1);

        ProductOrder savedProductOrder = productOrderRepository.findById(productOrder.getId()).orElse(null);
        assertThat(savedProductOrder).isNotNull();
        assertThat(savedProductOrder.getDelivery()).isEqualTo(delivery);
    }
}*/
