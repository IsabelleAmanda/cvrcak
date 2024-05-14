package hr.isabelle.cvrcakapp.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("class")
public class ServiceResultData {
    Boolean success;
    Object result;

    ServiceResultData(){}
    public ServiceResultData(Boolean success, Object result){
        this.success = success;
        this.result = result;
    }

}
