package exercise.daytime;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    @PostConstruct
    public void createdBean() {
        System.out.println("bean " + name + " was created!");
    }

    @PreDestroy
    public void clean() {
        System.out.println("bean " + name + " was destroyed!");
    }
}
