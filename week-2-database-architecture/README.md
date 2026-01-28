\# High-Level Database Schema Design



\## Domain

Student Course Registration System



\## Problem Description



The Student Course Registration System is designed to manage students, courses, instructors, and course enrollments within an academic institution. The system captures essential information about students and instructors, the courses offered, and the relationships between them.



\## Identified Entities



\- Student

\- Instructor

\- Course

\- Enrollment



\## Entity Relationships



\- A student can enroll in multiple courses.

\- A course can have multiple students enrolled.

\- An instructor can teach multiple courses.

\- Each course is taught by one instructor.



\## Database Schema (Logical Design)



\### Student

\- student\_id (Primary Key)

\- full\_name

\- email

\- date\_of\_birth



\### Instructor

\- instructor\_id (Primary Key)

\- full\_name

\- email



\### Course

\- course\_id (Primary Key)

\- course\_name

\- course\_code

\- credits

\- instructor\_id (Foreign Key)



\### Enrollment

\- enrollment\_id (Primary Key)

\- student\_id (Foreign Key)

\- course\_id (Foreign Key)

\- semester

\- enrollment\_date



\## Entity Relationship Diagram (ERD)



The diagram below illustrates the entities and their relationships.



!\[ERD](erd/student-course-erd.png)





