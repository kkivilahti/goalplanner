# Goalplanner
### Final project for Backend Programming course

- [Project overview](#project-overview)
- [Main features](#main-features)
- [Learning points](#learning-points)
- [UI samples](#ui-samples)
- [Database diagrams](#database-diagrams)
- [Testing the app](#testing-the-app)
- [Note](#note)

## Project overview
Here is the assignment: 
> In this exercise, you can build the first prototype of your dream application and continue developing it in the coming years.
> 
> Implement a Spring Boot application using the following technologies:
> - Spring Boot Web (MVC)
> - Thymeleaf templates (HTML5)
> - JPA (H2 or PostgreSQL)
> - Spring Security
> - Spring Validation (HTML forms, @Valid)
> - Spring REST (JSON)
>   
> and at least two of the following:
> - More advanced JPA handling (e.g., multiple database tables, @ManyToMany relationships, useful findBy... query methods, etc.)
> - Testing (JUnit, etc.)
> - I18n (e.g., multilingual user interface)
> - Some other Spring Boot functionality not mentioned above
> - Front-end solution using Spring REST services (React or similar)
>
> Design a database for your application. It must include at least two related tables.

I implemented all the required technologies and, from the optional list, chose to include **JUnit testing** and **more advanced JPA handling**. The application uses an **H2 in-memory database**.

The assignment gave us a lot of freedom in choosing the topic. I decided to build a Goalplanner application for planning and tracking goals and their milestones. I found this topic interesting because it was very different from the course exercises.

## Main features
- User registration and login functionality
- User can view, add, edit, and delete goals and milestones
- Progress is visually tracked as user marks goals and milestones as complete
- Goals are organized into separate views — Active Goals and Past Goals — based on their status and deadline
- Admin user has access to user management
  
## Learning points
The project was educational, rewarding, and occasionally challenging. It deepened my skills in Spring Boot, Thymeleaf, and Bootstrap, and gave me a clearer understanding of the MVC architecture. Some of the most interesting parts were implementing the user registration feature — which was completely new to me — and filtering goals using more advanced JPA query methods. I also enjoyed working on the UI design, although it still has room for improvement. One of the more challenging parts was implementing the add and edit features, as goals and milestones are linked, so I had to find a solution to handle them together within the forms.

## UI samples

![active goals page](https://github.com/kkivilahti/goalplanner/blob/main/docs/active-goals.png)

![goal example](https://github.com/kkivilahti/goalplanner/blob/main/docs/goal-example.png)

Goals are organized onto two separate pages — Active Goals and Past Goals — based on their status and deadline. Each goal is displayed in a clickable box that expands to reveal all relevant details. The box shows the goal’s title, description, and key dates, along with its associated milestones. It also includes buttons for editing, deleting, and marking milestones or the entire goal as complete.

Users can mark milestones as complete, and a progress bar visually tracks progress, updating automatically as each milestone is completed. Once all milestones are done, the user can mark the entire goal as complete — at which point it is moved to the *Past Goals* page:

![past goals page](https://github.com/kkivilahti/goalplanner/blob/main/docs/complete-goal.png)

Here's also a sneak peek at the admin's *User management* page:

![user management page](https://github.com/kkivilahti/goalplanner/blob/main/docs/manage-users.png)

## Database diagrams
![database diagrams](https://github.com/kkivilahti/goalplanner/blob/main/docs/diagrams.png)

## Testing the app
If you want to test the app locally, follow these steps:
1. Clone the repository
``` 
git clone https://github.com/kkivilahti/goalplanner.git
```
2. You can open the project in your IDE -
for example, VS Code - and start the application using the Spring Boot Dashboard.
    *(Make sure you have Java and Maven installed)*
   
    Optionally, you can start the app via the terminal:
```
mvn spring-boot:run
```

3. After the app has started, it should be available at:
http://localhost:8080

4. You can login with the test accounts
   - admin / admin123
   - user / user123
     
   or create your own account

## Note
This is an initial version of the application, built over a three-week period for a school course. While it includes many functional features, it could definitely benefit from additional UI improvements and feature enhancements.
