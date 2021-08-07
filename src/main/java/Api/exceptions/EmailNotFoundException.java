package Api.exceptions;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {
        super("E-mail não encontrado no sistema");
    }
}
