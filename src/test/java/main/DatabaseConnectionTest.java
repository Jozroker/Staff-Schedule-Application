package main;

import org.junit.jupiter.api.Test;
import server.config.EntityManagerClass;

import javax.persistence.EntityManager;

public class DatabaseConnectionTest {

    @Test
    public static void main(String[] args) {
        EntityManager em = EntityManagerClass.getInstance();
    }
}
