package ru.l92169.test_task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileServiceImpl fileService;

    @Override
    public List<User> getUsers(String name) {
        List<User> users = userRepository.findByName(name);
        log.info(String.format("+1 to count for %s", name));
        userRepository.addOne(name);
        return users;
    }

    @PostConstruct
    private void init() {
        List<User> listUsers = fileService.readFile();
        for (User user : listUsers) {
            List<User> users = userRepository.findByName(user.getName());
            if (users == null) {
                userRepository.save(user);
            }else { // если в файле такое же имя, но другой возраст, то сохраняется
                boolean bool = true;
                for (User user2 : users){
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
        User user = new User();
        String url = "https://api.agify.io/?name=" + name;
        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("Requesting data from an external service");
            ResponseDTO response = restTemplate.getForObject(url, ResponseDTO.class);
            user.setName(response.getName());
            user.setAge(response.getAge());
            user.setCount(1);
            if (user.getAge() == null) {
                user.setAge(-404);
            }
            userRepository.save(user);
            log.info("data received");
        } catch (HttpClientErrorException ex) {
            log.info("no data received, create user with age -404");
            user.setName(name);
            user.setAge(-404);
            user.setCount(1);
        }
        user.setFromFile(false);
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
