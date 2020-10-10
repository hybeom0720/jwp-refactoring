package kitchenpos.service;

import static kitchenpos.utils.TestObjectUtils.createMenuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.application.MenuGroupService;
import kitchenpos.domain.MenuGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class MenuGroupServiceTest extends ServiceTest {

    @Autowired
    private MenuGroupService menuGroupService;

    @DisplayName("메뉴 그룹 생성")
    @Test
    void create() {
        MenuGroup menuGroup = createMenuGroup("추천 메뉴");

        MenuGroup savedMenuGroup = menuGroupService.create(menuGroup);

        assertAll(() -> {
            assertThat(savedMenuGroup).isNotNull();
            assertThat(savedMenuGroup.getId()).isNotNull();
            assertThat(savedMenuGroup.getName()).isEqualTo("추천 메뉴");
        });
    }

    @DisplayName("메뉴 그룹 전체 조회")
    @Test
    void findAll() {
        List<MenuGroup> list = menuGroupService.list();

        assertThat(list).isNotEmpty();
    }
}
