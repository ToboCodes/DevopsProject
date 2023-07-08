package com.kibernumacademy.devops.entitys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {

  private Student student; // Objeto de la clase que vamos a probar

  @BeforeEach // Este método se ejecuta antes de cada prueba
  public void setup() {
    student = new Student("James", "Gosling", "jgosling@example.com"); // Inicializamos el objeto
  }

  @Test // Indica que este método es una prueba
  void shouldSetName() {
    student.setName("Richard"); // Cambiamos el nombre
    assertEquals("Richard", student.getName()); // Comprobamos que el cambio se realizó correctamente
  }

  @Test
  void shouldSetLastname() {
    student.setLastname("Stallman"); // Cambiamos el apellido
    assertEquals("Stallman", student.getLastname()); // Comprobamos que el cambio se realizó correctamente
  }

  @Test
  void shouldSetEmail() {
    student.setEmail("rstallman@example.com"); // Cambiamos el email
    assertEquals("rstallman@example.com", student.getEmail()); // Comprobamos que el cambio se realizó correctamente
  }

  // Puedes continuar con pruebas similares para otras funcionalidades de la clase Student.
}
