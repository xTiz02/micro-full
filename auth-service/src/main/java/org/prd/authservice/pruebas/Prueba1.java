package org.prd.authservice.pruebas;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Prueba1 {
    public static void main(String[] args) {
        System.out.println(Pattern.matches(".*/restrict/hellCheck$", "/auth/h/restrict/ol/hellCheck"));
        System.out.println(Pattern.matches("/auth/open/.*", "/auth/open/login"));
        System.out.println(Pattern.matches("/user/restrict/.*", "/user/restrict/all"));
        String a = null;
        var x = Objects.equals(a,null);
        System.out.println(x);
        String[] b = null;
        List<String> c = Arrays.asList(b);
        System.out.println(c.contains("hola"));
    }
}
