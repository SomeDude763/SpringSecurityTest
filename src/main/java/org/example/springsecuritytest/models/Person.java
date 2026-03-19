package org.example.springsecuritytest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    @NotEmpty
    @Size(min = 2, max = 50, message = "Имя не дб пустым епт и размер от 2 до 50")
    private String userName;
    @Min(value = 1900,message = "да не может быть такого")
    @Column(name = "year_of_birth")
    private String yearOfBirth;
    @Column(name = "password")
    private String password;

    public Person() {
    }

    public Person(String userName, String yearOfBirth) {
        this.userName = userName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", yearOfBirth='" + yearOfBirth + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
