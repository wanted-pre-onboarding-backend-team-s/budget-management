package jaringobi.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public record Payload<T>(List<T> list, Integer page, Boolean isEnd) {

    public static <T> Payload<T> of(List<T> data) {
        return new Payload<>(data, null, null);
    }
}
