package ru.sorokin;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sorokin.config.ApplicationConfiguration;
import ru.sorokin.console.OperationsConsoleListener;


public class Main {
    static void main() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
            OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);
            consoleListener.start();
        }
    }
}
