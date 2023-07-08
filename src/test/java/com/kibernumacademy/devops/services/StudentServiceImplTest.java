package com.kibernumacademy.devops.services;

import com.kibernumacademy.devops.entitys.Student;
import com.kibernumacademy.devops.repositories.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

  private StudentServiceImpl studentService; // La clase que vamos a probar

  @Mock // Crea un mock del repositorio
  private IStudentRepository repository;

  @BeforeEach // Este método se ejecuta antes de cada prueba
  public void setup() {
    MockitoAnnotations.openMocks(this); // Inicializa los mocks en esta prueba
    studentService = new StudentServiceImpl(repository); // Creamos la instancia de la clase a probar
  }

  @Test // Indica que este método es una prueba
  void shouldSaveStudent() {
    // Crear el estudiante
    Student student = new Student("James", "Gosling", "jgosling@example.com");
    // Cuando save se llame en el repositorio, retorna el estudiante
    when(repository.save(any(Student.class))).thenReturn(student);

    studentService.saveStudent(student); // Llamada al método a probar
    verify(repository).save(student); // Verifica si save fue llamado con el estudiante correcto
  }

  @Test
  void shouldFindStudentById() {
    Student student = new Student("James", "Gosling", "jgosling@example.com");
    student.setId(1L);
    when(repository.findById(any(Long.class))).thenReturn(Optional.of(student));

    studentService.getStudentById(1L);
    verify(repository).findById(1L);
  }

  @Test
  void shouldListAllStudents() {
    Student student1 = new Student("James", "Gosling", "jgosling@example.com");
    Student student2 = new Student("Richard", "Stallman", "rstallman@example.com");
    when(repository.findAll()).thenReturn(Arrays.asList(student1, student2));

    studentService.listAllStudents();
    verify(repository).findAll();
  }

  @Test
  void shouldDeleteStudentById() {
    Student student = new Student("James", "Gosling", "james@gmail.com");
    student.setId(1L);
    when(repository.findById(any(Long.class))).thenReturn(Optional.of(student));

    studentService.deleteStudentById(1L);
    verify(repository).deleteById(1L);
  }

  @Test
  void shouldUpdateStudent() {
    Student student = new Student("James", "Gosling", "james@gmail.com");
    student.setId(1L);
    when(repository.findById(any(Long.class))).thenReturn(Optional.of(student));

    studentService.updatedStudent(student);
    verify(repository).save(student);
  }
  
}
