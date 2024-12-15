package org.prd.bookservice.util;

import java.util.Objects;

public class Util {

    public static final String defaultUri = "https://img.pikbest.com/templates/20241024/corporate-and-unique-premium-vector-business-book-cover-design-ai_10997970.jpg!w700wp";

    public static boolean isValidField(String field){
        if(field != null && !field.isEmpty()){
            return field.equals("name") || field.equals("code") ||
                    field.equals("description") || field.equals("price") ||
                    field.equals("createdAt") || field.equals("updatedAt") ||
                    field.equals("active");
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