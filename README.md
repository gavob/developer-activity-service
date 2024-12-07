## Development Activity Dashboard

This application serves as the backend service for a Development
Activity Dashboard for public GitHub repositories. It handles
user requests from the frontend application, making relevant 
requests for data from the GitHub API and processing the data
into a format that the frontend can use to construct charts
and graphs for insight into recent developer activity within 
the repository. 

## Prerequisites

Java (Version 21 LTS)

Maven

## Install

In the GitHubApiConfiguration.java file replace `GITHUB_API_TOKEN`
in the authorisation header with a GitHub Personal Access Token 

You can use an IDE to compile and start the application or
by command line:

- Run `mvn clean install` in `/devactivitydashboard` directory to compile 
the project

- Run `mvn spring-boot:run` to start the application on 
default port http://localhost:8080/

## Usage

This service is used by the frontend application
https://github.com/gavob/development-activity-frontend
follow the README for setup and run this application

### Endpoints:
 
GET `/dev_activity/search_repos/{search_term}` Returns short 
summaries of GitHub repositories with names including the 
search term

GET `/dev_activity/repo_details/{owner}/{name}` Returns an
object with analysis data for displaying repository activity
in various charts


