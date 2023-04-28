package arguewise.demo.service;

public interface IEmailConfirmationSender {

    void sendEmailConfirmation(String userEmail, String userName);
}
