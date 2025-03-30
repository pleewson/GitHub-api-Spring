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
    git clone git@github.com:pleewson/GitHub-api-Spring.git
    cd github-api-spring
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
http://localhost:8080/git/users/{username}/repos
```

Replace {username} with the GitHub username you want to fetch, e.g., octocat.


![Screenshot 2025-03-30 at 17 17 10](https://github.com/user-attachments/assets/38720eae-2e96-44fe-9c84-c5d7d1584db5)


