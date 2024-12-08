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

## Improvements

One of the first things this could do with is some logging. 
If something should go wrong during runtime we can determine
the issue much faster with informative logs throughout the 
application (e.g. Log request type and parameters in controller).

At the moment the response codes are handled automatically by
spring, which has worked well with such a simple application,
but by manually wrapping responses in response entities we get
much greater control. We can set specific error messages and 
status codes for particular circumstances/exceptions which can
allow us to create more informative responses on the frontend
receiving the responses (e.g. We might get some kind of 500 error
due to unexpected data or some kind of system bug and may use
a custom error message to give a more specific response).

The data formats contained within the repository analysis are 
distinct and have their own processing logic, separating these
into their own classes would be a good choice. If new data was
integrated or adding pagination of results, etc, then we would
see the AnalysisHelper class quickly grow excessively complex. 
This might include adding new abstract classes or interfaces to 
create a clearer type system for analysis formats (e.g A TimeData 
abstract class with IssueTime or PullRequestTime subclasses).

The various elements (commits, pull requests, etc) that we query
return the maximum GitHub page size of 100 most recent records.
This is enough for some of the graphs to effectively display 
some of the recent activity, but for data that is organised by
day and number of instances can potentially be reduced to a single
instance if there were 100 records. This might be unlikely with
releases, but quite likely with commits if it's a very active public 
repository. We could repeat calls for more pages, but it already 
takes some noticeable time to retrieve all the data as it is.
The GitHub API has statistics endpoints 
https://docs.github.com/en/rest/metrics/statistics?apiVersion=2022-11-28
which could give better long term insights with more efficient 
queries. It would be best to introduce some of these endpoints
in the metrics sections of the API docs which I unfortunately missed
earlier in development.

One feature which could be added is user accounts. This could allow
users to create an account, provide their own GitHub API token, and 
get access to public and private repositories that the user has
access to. This would require registering and storage of user 
account details in a database, and providing their token to the 
configuration header on each request. User requests would need
to be authenticated, which could be done by using a tool like Spring
Security and providing the client a generated JWT token to be stored in their
cookies to be used with each request, or some other method of 
authentication or session management. 

Further endpoints for data could be added to this service. The client
might select an overview of a group of commits which makes a request
to this service for the associated pull requests.

