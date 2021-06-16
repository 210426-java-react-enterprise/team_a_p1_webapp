# team_a_p1_webapp
Team Alpha's WebApp repo

# General
User Management Web-Application that leverages a custom Object Relational Mapper(ORM). Custom ORM can be found [here](https://github.com/210426-java-react-enterprise/team_a_p1_orm)
  * Maven Project
    - use `mvn install` to install maven
    - use `mvn clean package` to create .war file
  * Uses Tomcat9
    - run server beforehand
  * Uses Servlets
  * Use Postman to test endpoints
  * Uses PostgeSQL database
    - "application.properties" file needs to be added with database information (url, schema, username, password)

# Two Servlets (Authorization & User Management)
  * Port: 8080
  * Context Path: "*/team_a_p1_webapp/"
  * Authorization endpoint: "*/auth"
  * User Management endpoint: "*/users"

# Authorization endpoint
  * Post Request
    - validates credentials from a JSON
    - returns the authorized user
  * Delete Request 
    - invalidates session and logs out

# User Management endpoint
  * Get Request
    - gets all users when no parameter is provided (admin privileges needed; modify doGet method in UserServlet)
    - gets user with the provide user ID
    - returns list of users or single user
  * Post Request (cannot have session: use delete request in authorization to invalidate)
    - validates inputs and creates new user
    - returns the new user
  * Put Request
    - validates inputs and updates user with new information
  * Delete Request
    - search for user and deletes if from database
