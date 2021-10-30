package kitchenpos.application.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Optional;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderServiceCreateTest extends OrderServiceTest {

    @DisplayName("주문이 비어서 들어선 안 된다.")
    @Test
    void nullOrder() {
        //given
        standardOrder.setOrderLineItems(null);

        //when

        //then
        assertThatThrownBy(() -> orderService.create(standardOrder))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문의 리스트가 메뉴와 일치해선 안 된다.")
    @Test
    void withExistedItems() {
        //given
        given(menuDao.countByIdIn(Arrays.asList(1L))).willReturn(1L);

        //when

        //then
        assertThatThrownBy(() -> orderService.create(standardOrder))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블이 비어선 안 된다.")
    @Test
    void withOrderTable() {
        //given
        given(menuDao.countByIdIn(any())).willReturn(1L);
        given(orderTableDao.findById(1L)).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> orderService.create(standardOrder))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 추가한다.")
    @Test
    void createOrder() {
        //given
        given(menuDao.countByIdIn(any())).willReturn(1L);
        given(orderTableDao.findById(1L)).willReturn(Optional.of(standardOrderTable));
        given(orderDao.save(any())).willReturn(standardOrder);

        //when
        Order order = orderService.create(standardOrder);

        //then
        assertAll(
            () -> assertThat(order.getOrderTableId()).isEqualTo(1L),
            () -> assertThat(order.getOrderLineItems().size()).isEqualTo(1),
            () -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COOKING.name())
        );
    }

}
