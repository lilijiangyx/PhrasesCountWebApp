package test;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by jianl018 on 3/14/17.
 */

@Named
@RequestScoped
public class TimeBean {
    public TimeBean(){}

    public String getTime(){
        return new java.util.Date().toString();
    }
}
