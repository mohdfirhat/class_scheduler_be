package com.tfip.lessonscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Lesson Scheduler application.
 * <p>
 * This class initializes and launches the Spring Boot application, setting up
 * the necessary configurations and components required for the application to
 * run. It makes use of the {@code SpringApplication.run} method to bootstrap
 * the application.
 * <p>
 * The {@code @SpringBootApplication} annotation enables several key features: -
 * Component scanning for discovering Spring components within the application.
 * - Automatic configuration of the application context. - Enablement of Spring
 * Boot's auto-configuration mechanism.
 */
@SpringBootApplication
public class LessonschedulerApplication {

    /**
     * The main method serves as the entry point of the Lesson Scheduler
     * application. It initializes and launches the Spring Boot application by
     * invoking
     * {@code SpringApplication.run(LessonschedulerApplication.class, args)}.
     *
     * @param args command-line arguments passed to the application during
     *             startup
     */
    public static void main(String[] args) {
        SpringApplication.run(LessonschedulerApplication.class, args);
    }

}
