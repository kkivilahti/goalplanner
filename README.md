# Goalplanner
- [Project overview](#project-overview)
- [Technologies used](#technologies-used)
- [Main features](#main-features)
- [UI samples](#ui-samples)
- [Database diagrams](#database-diagrams)
- [Learning points](#learning-points)
- [Running the app](#running-the-app)
- [Note](#note)

## Project overview
This is my final project for Haaga-Helia’s Backend Programming course. The assignment offered plenty of freedom in choosing the topic, so I decided to build an app for planning and tracking goals. I enjoyed working on this project because it was quite different from the usual course exercises and gave me a chance to be creative with both the design and features.

## Technologies used
- Spring Boot Web (MVC)
- Thymeleaf templates (HTML5)
- JPA with H2 in-memory database
- Spring Security
- Spring Validation
- Spring REST
- JUnit
- Bootstrap

## Main features
- User registration, login, and role-based access control
- CRUD operations for goals and milestones
- Visual progress tracking
- Separate views for active and past goals
- Admin panel for user management

## UI samples

### Viewing active and past goals

Goals are divided into two separate pages according to their status and deadline. The *Active Goals* page displays pending goals that have a deadline set in the future, and contains most of the CRUD options. The *Past Goals* page lists completed goals and those with past deadlines, and is primarily intended for viewing.

![active goals page](/docs/active-goals.png)

![goal example](/docs/goal-example.png)

Each goal is displayed in a clickable box that expands to reveal all relevant details. The box shows the goal’s title, description, and key dates, along with its associated milestones. It also includes buttons for editing, deleting, and marking milestones or the entire goal as complete.

The user can mark milestones as complete, and a progress bar visually tracks progress, updating automatically as milestones are completed. Once all milestones are done, the user can mark the entire goal as complete, moving it to the *Past Goals* page.

![past goals page](/docs/complete-goal.png)

### Adding a new goal

The process of adding a new goal is divided into two steps. First, the user enters the general information about the goal. When the user clicks *Next*, the goal details are saved, and the user is redirected to the next page to add milestones.

![add goal page](/docs/addgoal.png)

![add milestones page](/docs/addmilestones.png)

At least one milestone must be added before proceeding. The user can add as many milestones as they want, and can also remove them if needed. Milestones are saved one at a time when the user clicks *Add milestone*. Once all desired milestones have been added, the user clicks *Finish* and is redirected back to the goals overview page.

If the user clicks *Cancel*, all information related to the new goal is discarded and the user is redirected back to the goals overview page.

### User management panel

The user management panel is accessible only to the admin. It allows viewing information of all registered users and deleting accounts when necessary.

![user management page](/docs/manage-users.png)

## Database diagrams
The database consists of three tables representing users, goals, and milestones, with relationships connecting goals to users and milestones to goals.

![database diagrams](/docs/diagrams.png)

## Learning points
This project provided valuable hands-on experience in building a full-stack Spring Boot application. I learned new techniques such as implementing user registration, using more advanced JPA techniques (like custom findBy queries), and managing server-side form validation with Spring Validation.

I improved my UI design skills with Thymeleaf and Bootstrap, aiming to create a user-friendly and responsive experience — though there’s still room for improvement in some areas.

Additionally, this project enhanced my problem-solving skills, as some parts proved to be a bit challenging, like managing goals and milestones together within the forms, tracking goal progress, and creating an intuitive UI.

## Running the app
**Requirements**: Java 17 (or higher), Maven, and optionally an IDE like VS Code

### Running the app locally
1. Clone the repository and navigate to it
    ```
    git clone https://github.com/kkivilahti/goalplanner.git
    cd goalplanner
    ```
2. Open the project in your IDE (e.g., VS Code) and start the app using the Spring Boot Dashboard.
   
    Optionally, you can start the app from the terminal:
    ```
    mvn spring-boot:run
    ```

3. Once the app is running, open your browser and go to:
http://localhost:8080

4. Login using the test accounts:
   - admin / admin123
   - user / user123
     
   or create your own account

## Note
This app was developed primarily over a three-week period as part of a school course. It was a rewarding challenge that helped me deepen my understanding of full-stack development with Spring Boot. While the app includes many functional features, it’s still a work in progress, and I welcome any feedback or suggestions for improvement.