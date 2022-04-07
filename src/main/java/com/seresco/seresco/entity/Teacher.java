package com.seresco.seresco.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "El numero de documento no puede ser vacio")
  @Size(min = 8, max = 8, message = "debe contener 8 numeros")
  @Column(name = "number_id", unique = true, length = 8, nullable = false)
  private String numberID;

  @NotEmpty(message = "El nombre no puede ser vacio")
  @Column( nullable = false)
  private String name;

  @NotEmpty(message = "El apellido no puede ser vacio")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotEmpty(message = "El correo no puede ser vacio")
  @Email(message = "No es una dirección de correo válido")
  private String email;
  private String phone;
}
