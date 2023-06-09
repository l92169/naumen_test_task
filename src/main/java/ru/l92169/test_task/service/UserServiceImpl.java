package ru.l92169.test_task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.l92169.test_task.entity.ResponseDTO;
import ru.l92169.test_task.entity.User;
import ru.l92169.test_task.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileServiceImpl fileService;

    @Override
    public List<User> getUsers(String name) {
        List<User> users = userRepository.findByName(name);
        log.info(String.format("+1 to count for %s", name));
        userRepository.addOne(name);
        if (users.size() == 0) {
            users = workWithExternalService(name);
        }
        return users;
    }

    @PostConstruct
    private void init() {
        List<User> listUsers = fileService.readFile();
        for (User user : listUsers) {
            List<User> users = userRepository.findByName(user.getName());
            if (users == null) {
                userRepository.save(user);
            } else { // если в файле такое же имя, но другой возраст, то сохраняется
                boolean bool = true;
                for (User user2 : users) {
                    if (user.getAge() == user2.getAge()) bool = false;
                }
                if (bool) userRepository.save(user);
            }
        }
    }

    @Override
    public List<User> findNamesWithMaxAge(boolean bool) {
        return userRepository.findUsersWithMaxAge(bool);
    }

    @Override
    public List<User> workWithExternalService(String name) {
        User user;
        String url = "https://api.agify.io/?name=" + name;
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("Requesting data from an external service");
            ResponseDTO response = restTemplate.getForObject(url, ResponseDTO.class);
            user = new User(response.getName(), response.getAge(), 1, false);
            if (user.getAge() == null) user.setAge(-404);
            log.info("data received");
        } catch (HttpClientErrorException ex) {
            log.info("no data received, create user with age -404");
            user = new User(name, -404, 1, false);
        }
        userRepository.save(user);
        return List.of(user);
    }

    @Override
    public HashMap<User, Integer> getFrequencyFromFile(boolean bool) {
        HashMap map = new HashMap<>();
        List<List<String>> users = userRepository.findAllFromFile(bool);
        for (List user : users) {
            map.put(user.get(0), user.get(1));
        }
        return map;
    }
}
