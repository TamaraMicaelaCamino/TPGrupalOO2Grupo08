package com.oo2.tpgrupalhito2grupo08.components;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class GeneradorPassword {

    private static final String CARACTERES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "abcdefghijklmnopqrstuvwxyz"
                    + "0123456789-_*";

    private final SecureRandom random = new SecureRandom();

    public String generar() {
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int posicion = random.nextInt(CARACTERES.length());
            char caracter = CARACTERES.charAt(posicion);

            password.append(caracter);
        }

        return password.toString();
    }
}