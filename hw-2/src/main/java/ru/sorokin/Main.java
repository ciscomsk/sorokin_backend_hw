package ru.sorokin;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sorokin.console.OperationsConsoleListener;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.sorokin")) {
            OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);
            consoleListener.runBank();
        }
    }
}
