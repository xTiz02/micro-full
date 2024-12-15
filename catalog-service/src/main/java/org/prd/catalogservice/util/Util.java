package org.prd.catalogservice.util;

public class Util {

    public static boolean isValidField(String field){
        if(field != null && !field.isEmpty()){
            return field.equals("name") || field.equals("code") ||
                    field.equals("description") || field.equals("price");
        }

        return false;
    }

    public static boolean isValidSort(String sort){
        if(sort != null && !sort.isEmpty()){
            return sort.equals("asc") || sort.equals("desc");
        }

        return false;
    }
}