# spring-reddit

This application is build using spring Boot 3, Spring security 6, Spring data JPA with MySQL, Lombok, MapStruct and Java Mail Sender. It uses fake SMTP server called MailTrap to check whether our email functionality is working or not

## Steps to Use the Application

1. **Register User**
    - Register the user using the `/api/auth/signup` API.

2. **Verify User**
    - Open the Mailtrap server and verify the user using the link received in the inbox.

3. **Get JWT Token**
    - Obtain the JWT token and refresh token using the `/api/auth/login` API.

4. **Create Subreddit**
    - Create a subreddit using the `/api/subreddit` API, passing the JWT token in the Authorization section.

5. **View Subreddits**
    - View all subreddits using the `/api/subreddit` API.

6. **Create Post**
    - Create a post using the `/api/posts` API.

7. **Add Comment**
    - Add a comment on the post using the `/api/comments` API.

8. **View User Comments**
    - View all the comments by the user using the `/api/comments/by-user/{username}` API.

9. **Upvote/Downvote Post**
    - Upvote or downvote the post using the `/api/votes` API and validate the upvote or downvote count in the `/api/posts` response.

10. **Refresh Token**
    - Once the token expires, obtain a new token by calling the `/api/auth/refresh/token` API.

11. **Logout**
    - For logout, call the `/api/auth/logout` API.

## Note
- While running the application using IntelliJ, make sure to pass the email value using VM arguments.  
  Example: `-Demail=testmail@gmail.com`
- Find the postman collection at the project root directory.
  
