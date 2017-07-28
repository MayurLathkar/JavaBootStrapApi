package com.example.demo.util;

import com.example.demo.model.User;

/**
 * Created by mayurlathkar on 25/07/17.
 */
public class Validation {

    public static String checkForMissingParameter(User user) {

        if (isNull(user.getName(), "Name").equalsIgnoreCase("success"))
            if (isNull(user.getFirst_name(), "First name").equalsIgnoreCase("success"))
                if (isNull(user.getFirst_name(), "Last name").equalsIgnoreCase("success"))
                    if (isNull(user.getAddress(), "Address").equalsIgnoreCase("success"))
                        if (isNull(user.getPhone(), "Phone number").equalsIgnoreCase("success"))
                            if (isNull(user.getUser_type(), "Type").equalsIgnoreCase("success"))
                                return "success";
                            else return isNull(user.getUser_type(), "userType");
                        else return isNull(user.getPhone(), "phone");
                    else return isNull(user.getAddress(), "address");
                else return isNull(user.getFirst_name(), "last name");
            else return isNull(user.getFirst_name(), "first name");
        else return isNull(user.getName(), "name");
    }

    public static String isNull(String property, String parameter) {
        if (property == null)
            return "Missing parameter: " + parameter;
        else
            return isEmpty(property, parameter);
    }

    public static String isEmpty(String property, String parameter) {
        if (property.isEmpty())
            return parameter + " can not be empty";
        return "success";
    }
}
