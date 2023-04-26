package ru.l92169.test_task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO {
    private String name;
    private Integer count;
    private Integer age;

    ResponseDTO() {
    }

    ResponseDTO(String name, Integer count, Integer age) {
        this.name = name;
        this.count = count;
        this.age = age;
    }


    @Override
    public String toString() {
        return "ResponseDTO{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", age=" + age +
                '}';
    }
}
