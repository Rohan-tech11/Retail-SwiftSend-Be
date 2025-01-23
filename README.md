# SwiftSend Application

SwiftSend is a Java-based middleware platform developed using the Spring Boot framework. It serves as a bridge between courier services and users in Canada, allowing users to create accounts, book orders, and enabling clients to provide and manage services. The application uses MySQL as its database (via Hibernate), React for the front end, and includes email authentication features.

This project represents my deep dive into modern cloud-native development, showcasing the implementation of a robust middleware platform that bridges courier services with end users. Built with Java Spring Boot and React, it demonstrates the practical application of enterprise-grade architectural patterns and DevOps practices.

## Features

* **User Authentication:**
   * Users can create accounts and verify their emails for secure authentication.
   * JWT (JSON Web Token) is used for enhanced security.
   * Google OAuth2 integration for easy sign-in.
* **Service Booking:**
   * Users can view available services provided by different clients.
   * Book orders seamlessly through the application.
* **Client Management:**
   * Clients can sign up, verify their emails, and manage their services.
   * Admins receive notifications about new clients and can approve or reject client accounts.
* **Service Management:**
   * Approved clients can upload and manage services.
   * Uploaded services are visible to users for booking.
* **Email Authentication:**
   * Email verification is implemented for both user and client registration.
   * Java Mail Sender is utilized to send verification emails.
* **API Documentation:**
   * Swagger UI integration for easy API exploration and testing.

## Technologies Used

* **Backend:**
   * Java Spring Boot
   * Spring Security
   * Hibernate
   * JWT (JSON Web Token)
   * Java Mail Sender
   * MySQL (Database)
   * Swagger (API Documentation)
* **Frontend:**
   * React
   * JavaScript
* **Authentication:**
   * Google OAuth2
* **Containerization:**
   * Docker

## Getting Started

### Prerequisites

* Java Development Kit (JDK)
* Node.js and npm
* MySQL Server
* Maven
* Docker (optional)

### Configuration

1. Clone the repository: git clone https://github.com/Rohan-tech11/Retail-SwiftSend-Be.git
cd Retail-SwiftSend-Be

2. Configure application properties:

Update `src/main/resources/application.properties` with your database, email, and Google OAuth2 credentials. Use environment variables for sensitive information.

3. Build and install dependencies: mvn clean install
4. Run the application:mvn spring-boot:run
### Running with Docker

1. Build the Docker image:docker build -t swiftsend .
2. Run the container:docker run -p 8080:8080 swiftsend
## API Documentation

Once the application is running, you can access the Swagger UI at:http://localhost:8080/swagger-ui.html
This provides an interactive interface to explore and test the API endpoints.

## Future Developments

As part of our commitment to continuous improvement, we plan to keep updating SwiftSend with new features and enhancements. We welcome contributions from the community and aim to evolve this project to meet the ever-changing needs of the courier services industry.

## Contributing

We welcome contributions to SwiftSend! If you'd like to contribute, please follow these guidelines:

### How to Contribute

1. Fork the repository to your GitHub account.
2. Create a new branch for your feature or bug fix: `git checkout -b feature/your-feature-name`
3. Make your changes and commit them with descriptive commit messages.
4. Push your changes to your fork
5. Open a pull request on the original repository.
