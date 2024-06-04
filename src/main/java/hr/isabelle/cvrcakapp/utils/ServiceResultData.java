package hr.isabelle.cvrcakapp.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResultData {
    private boolean success;
    private Object result;
}
