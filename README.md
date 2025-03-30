# github-api-Spring

An application to fetch and display repository information for a GitHub user.

-Repository Name (excluding forks)

-Owner Login

-For each branch it’s name and last commit sha


## Requirements

- Java 21
- Spring 3.5.0
- Maven

1. **Clone the repository:**

    ```bash
    git clone git@github.com:pleewson/GitHub-api.git
    cd github-api
    ```

2. **Install dependencies:**

    ```bash
    mvn clean install
    ```

3. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

## Usage

This application allows you to fetch GitHub repositories' information for a user. To retrieve the data, send a GET request to the following endpoint:

```bash
http://localhost:8080/git/{username}/repositories
```

Replace {username} with the GitHub username you want to fetch, e.g., octocat.

![Screenshot 2025-03-08 at 15 45 01](https://github.com/user-attachments/assets/203e7723-e754-4912-b4db-e1ac4a0622bf)
![Screenshot 2025-03-08 at 15 45 26](https://github.com/user-attachments/assets/df0006e8-2526-4aaf-a5dd-8cf62fbb91d2)


