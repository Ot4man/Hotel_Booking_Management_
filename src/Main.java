import repository.UserRepository;
import service.AuthService;
import ui.Console;

public class Main {

    public static void main(String[] args) {
        // Dependency Injection
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        // Start UI
        Console consoleUI = new Console(authService);
        consoleUI.start();
    }
}

