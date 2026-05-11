package com.example.driver_service.command;

public interface CommandHandler<C extends Command<R>, R> {
    R handle(C command);
}