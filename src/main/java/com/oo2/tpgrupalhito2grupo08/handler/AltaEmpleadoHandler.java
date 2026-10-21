package com.oo2.tpgrupalhito2grupo08.handler;


import com.oo2.tpgrupalhito2grupo08.events.AltaEmpleadoEvent;
import com.oo2.tpgrupalhito2grupo08.services.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class AltaEmpleadoHandler {

    private final IEmailService emailService;

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void enviarCredenciales(AltaEmpleadoEvent evento) {
        String mensaje =
                "USUARIO: " + evento.getUsuario()

                        + "\nCONTRASEÑA: " + evento.getPassword();

        try {
            emailService.enviarMail(
                    evento.getUsuario(),
                    "ALTA EMPLEADO",
                    mensaje
            );
        } catch (MailException error) {
            log.error(
                    "Falló el correo del empleado {}",
                    evento.getIdEmpleado(),
                    error
            );
        }
    }
}
