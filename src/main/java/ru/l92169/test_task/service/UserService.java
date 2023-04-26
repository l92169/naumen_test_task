package ru.l92169.test_task.service;

import ru.l92169.test_task.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    private void init() {
    }

    List<User> getUsers(String name) throws Exception;

    List<User> workWithExternalService(String name);

    Map<User, Integer> getFrequencyFromFile(boolean bool);

    List<User> findNamesWithMaxAge(boolean bool);
}