package ru.l92169.test_task.service;

import ru.l92169.test_task.entity.User;

import java.util.List;

public interface FileService {
    List<User> readFile();
}
