package ru.l92169.test_task.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.l92169.test_task.entity.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FileRepository {
    private static final String path = "data/names";

    public List<User> fileReader() {
        List<User> userList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path), StandardCharsets.UTF_8));
            String line = reader.readLine();
            while (line != null) {
                try {
                    String[] parts = line.trim().split("_");
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    userList.add(new User(name, age, 0, true));
                    line = reader.readLine();
                } catch (Exception e) {
                    log.info(String.format("User %s has problems!", line));
                    line = reader.readLine();
                }
            }
            reader.close();
            log.info("File has been read!");
        } catch (IOException e) {
            log.info("Problems with file!");
            userList = new ArrayList<>();
        }
        return userList;
    }
}
