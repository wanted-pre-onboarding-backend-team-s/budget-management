package jaringobi.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BudgetByCategoryRequest {

    @NotNull(message = "필수입니다.")
    @Min(value = 1, message = "{value} 이상 이어야 합니다.")
    private Long categoryId;

    @Min(value = 1, message = "{value} 이상 이어야 합니다.")
    private int money;
}
