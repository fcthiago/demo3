package com.example.demo;

import com.example.demo.database.QueryResponse;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Pageable;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        StudentRepository repository = (StudentRepository) context.getBean("studentRepository");

        repository.save(new Student(1, "Zara", 11));
        repository.save(new Student(2, "Nuha", 2));
        repository.save(new Student(3, "Ayan", 15));

        System.out.println();
        System.out.println("======== Query Response ========");
        QueryResponse<Student> queryResponse = repository.find(Pageable.unpaged());
        System.out.println(", Total : " + queryResponse.getTotal());
        System.out.println("================================");

        for (Student student : queryResponse.getResult()) {
            System.out.print("ID : " + student.getId());
            System.out.print(", Name : " + student.getName());
            System.out.println(", Age : " + student.getAge());
        }

        System.out.println();

        context.close();
    }

}
