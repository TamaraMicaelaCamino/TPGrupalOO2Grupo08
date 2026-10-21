package com.oo2.tpgrupalhito2grupo08.services;

import org.springframework.stereotype.Service;

import java.io.File;

public interface IEmailService {
    void enviarMail(String aUsuario, String asunto, String mensaje);

    void enviarMailConArchico(String aUsuario, String asunto, String mensaje, File file);

}
