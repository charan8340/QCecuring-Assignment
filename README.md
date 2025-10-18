## Task Management Dashboard Documentation

### Overview

This documentation provides an overview of the Task Management Dashboard application, detailing its features, architecture, and code structure. The application allows users to manage tasks, view audit logs, and perform CRUD operations.

**Application Features** [0:21](https://loom.com/share/628e3ec0dea341c4b6383a5480bfb2b3?t=21)

![generated-image-at-00:00:21](https://loom.com/i/5decdb610a73420a80ce773d2ec9df33?workflows_screenshot=true)

- Users can add new tasks with a title and description.
- Tasks can be edited to update details such as to change title or description.
- An audit log tracks changes made to tasks, showing previous values.
- Users can search tasks by title or description.
- Pagination is implemented to display tasks and audit logs efficiently.
- Users can delete tasks and view deleted task information.

**Code Architecture** [5:03](https://loom.com/share/628e3ec0dea341c4b6383a5480bfb2b3?t=303)

![generated-image-at-00:05:03](https://loom.com/i/bdd9f87612344809bbe711135d34fcdb?workflows_screenshot=true)

- The application is built using Spring Boot with a modular architecture.
- It consists of two main modules: Task and Audit Log.
- The MVC (Model-View-Controller) pattern is used for the audit log module.
- The controller handles API requests, while the service layer contains business logic and interacts with the MongoDB repository.

**Backend Implementation** [6:08](https://loom.com/share/628e3ec0dea341c4b6383a5480bfb2b3?t=368)

![generated-image-at-00:06:08](https://loom.com/i/1df804ca5eab4bb2838aa31178cec52f?workflows_screenshot=true)

- The backend is powered by Spring Boot, utilizing a MongoDB database.
- The application defines endpoints for task management and audit log retrieval.
- The service layer processes requests and communicates with the database for CRUD operations.
- Pagination is implemented to limit the number of records returned in responses.

**Database Configuration** [6:49](https://loom.com/share/628e3ec0dea341c4b6383a5480bfb2b3?t=409)

![generated-image-at-00:06:49](https://loom.com/i/d8522553e0404769b456f145d820b58d?workflows_screenshot=true)

- The MongoDB database is configured with the following details: 
  - Port number: 27017.
  - Database name: 'task_manager'.
- The application logs all operations performed on tasks, including create, update, and delete actions.

### Link to Loom

<https://loom.com/share/628e3ec0dea341c4b6383a5480bfb2b3>
