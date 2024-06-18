package hr.isabelle.cvrcakapp.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResultData {
    private Object data;
    private Boolean success;
    private Object result;

    public ServiceResultData(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }
}
