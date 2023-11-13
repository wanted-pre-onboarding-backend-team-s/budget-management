package jaringobi.controller.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BudgetByCategoryRequest {
    private long categoryId;
    private int money;
}
