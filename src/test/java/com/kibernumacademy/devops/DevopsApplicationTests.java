package com.kibernumacademy.devops;

import com.kibernumacademy.devops.entitys.Student;
import com.kibernumacademy.devops.repositories.IStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DevopsApplicationTests {

    @Autowired
    private IStudentRepository repository;

    @Test
    void contextLoads() {
      assertNotNull(repository);
    }

    @Test
    void main_startsSpringBootApplication() {
        DevopsApplication.main(new String[] {});
        assertThat(SpringApplication.exit(SpringApplication.run(DevopsApplication.class))).isEqualTo(0);
    }

    @Test
    void testRepositorySavesStudents() {
        // given
        Student student3 = new Student("Sofia", "Ramos", "sofia@gmail.com");
        Student student4 = new Student("Catalina", "Marquez", "catalina@gmail.com");

        // when
        repository.save(student3);
        repository.save(student4);

        // then
        Optional<Student> foundMiguel = repository.findById(student3.getId());
        Optional<Student> foundCamila = repository.findById(student4.getId());

        assertTrue(foundMiguel.isPresent());
        assertTrue(foundCamila.isPresent());
    }
}