package org.prd.orderservice.util;

public class Util {
    public static boolean isValidField(String field){
        if(field != null && !field.isEmpty()){
            return field.equals("orderNum") || field.equals("userUUID") ||
                    field.equals("createdAt") || field.equals("updatedAt") ||
                    field.equals("status");
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