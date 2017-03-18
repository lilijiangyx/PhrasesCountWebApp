package test;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
/**
 * Created by jianl018 on 3/14/17.
 */

@Named(value = "registration")
@RequestScoped
public class RegistrationJSBean {
    private String lastName;
    private String firstName;
    private String mi;
    private String gender;
    private String major;
    private String[] minor;
    private String[] hobby;
    private String remarks;
    


    public RegistrationJSBean(){

    }

    public String getFirstName() {
        return firstName;
    }

    public String getMi() {
        return mi;
    }

    public String getGender() {
        return gender;
    }

    public String getMajor() {
        return major;
    }

    public String[] getMinor() {
        return minor;
    }

    public String[] getHobby() {
        return hobby;
    }

    public String getRemarks() {
        return remarks;
    }


    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setMinor(String[] minor) {
        this.minor = minor;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getResponse(){
        if (lastName == null){
            return "";//Request has not been made
        }else{
            String allMinor = "";
            for (String s:minor){
                allMinor += s + " ";
            }

            String allHobby = "";
            for (String s: hobby){
                allHobby += s + " ";
            }

            return "<p style=\"color:red\">You entered <br />" +
                    "Last Name: " + lastName + "<br />" +
                    "First Name: " + firstName + "<br />" +
                    "MI: " + mi + "<br />" +
                    "Gender: " + gender + "<br />" +
                    "Major: " + major + "<br />" +
                    "Minor: " + allMinor + "<br />" +
                    "Hobby: " + allHobby + "<br />" +
                    "Remarks: " + remarks + "</p>";
        }
    }
}
