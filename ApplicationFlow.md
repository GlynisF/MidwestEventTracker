# Application Flow

### User Sign Up
1. A user has landed on the Midwest Event Tracker landing page.
2. User clicks the option to 'sign up' (option to sign up is only available on the landing page).
3. User fills out the sign up form and clicks 'submit'.
4. Request is redirected to the user sign up servlet.
5. Servlet creates a user object & user object in the database with details retrieved from the form.
6. Response to user confirming sign up success and asking user to log in with credentials 
(displayed as a session message on the jsp).

### User Sign In (after user sign up)
1. User clicks 'log in' when prompted.
2. User enters their username or email address and password and clicks 'submit'.
3. If user is authenticated, the server will handle allowing access to edit pages. JDBCRealm is used for authentication
(users, users_roles, and roles table).
4. If authentication fails, an error message is presented to the user (displayed as a session message on the jsp).