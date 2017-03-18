package test;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by jianl018 on 3/14/17.
 */

@Named(value = "calculator")
@RequestScoped
public class Calculator {
    private Double number1;
    private Double number2;
    private Double result;

    public Calculator(){
    }

    public Double getNumber1() {
        return number1;
    }

    public void setNumber1(Double number1) {
        this.number1 = number1;
    }

    public Double getNumber2() {
        return number2;
    }

    public void setNumber2(Double number2) {
        this.number2 = number2;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public void add(){
        result = number1 + number2;
    }

    public void subtract(){
        result = number1 - number2;
    }

    public void divide(){
        result = number1/number2;
    }

    public void multiply(){
        result = number1*number2;
    }

}
