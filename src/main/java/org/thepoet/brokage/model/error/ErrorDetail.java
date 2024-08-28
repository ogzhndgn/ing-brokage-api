package org.thepoet.brokage.model.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thepoet.brokage.exception.ErrorCode;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetail {
    private ErrorCode errorCode;
    private String message;
    private String parameter;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> details;

}
