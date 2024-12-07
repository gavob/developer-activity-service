package dev.gavin.devactivitydashboard.helper;

import dev.gavin.devactivitydashboard.model.analysis.DayCounter;
import dev.gavin.devactivitydashboard.model.analysis.NameValue;
import dev.gavin.devactivitydashboard.model.analysis.RepositoryAnalysis;
import dev.gavin.devactivitydashboard.model.analysis.TimeLength;
import dev.gavin.devactivitydashboard.model.github.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalysisHelper {
    /**
     * Constructs a RepositoryAnalysis instance using the given repository data. The data contained in
     * the analysis is processed into three primary formats which are required by the charts used by
     * the client:
     *      DayCounter (Parameters: x, y) for displaying occurrences by date on Line & Scatter charts
     *      NameValue (Parameters: name, value) for displaying occurrences by label on Circle Packing chart
     *      TimeLength (Parameters: id, time) for displaying times sequentially in a Bar chart
     * @param repository            Repository basic information
     * @param commits               List of Commits
     * @param comments              List of Comments
     * @param pullRequests          List of Pull Requests
     * @param releases              List of Releases
     * @param issues                List of Issues
     * @param closedPullRequests    List of Pull Requests in the closed state
     * @param closedIssues          List of Issues in the closed state
     * @return repositoryAnalysis
     */
    public static RepositoryAnalysis buildRepositoryAnalysis(Repository repository,
                                                       List<Commit> commits,
                                                       List<Comment> comments,
                                                       List<PullRequest> pullRequests,
                                                       List<Release> releases,
                                                       List<Issue> issues,
                                                       List<PullRequest> closedPullRequests,
                                                       List<Issue> closedIssues) {
        final RepositoryAnalysis repositoryAnalysis = new RepositoryAnalysis(repository);

        addDayCounterData(repositoryAnalysis, commits, comments, pullRequests, issues, releases);
        addNameValueData(repositoryAnalysis, closedPullRequests, issues);
        addTimeLengthData(repositoryAnalysis, closedPullRequests, closedIssues);

        return repositoryAnalysis;
    }

    private static void addDayCounterData(RepositoryAnalysis repositoryAnalysis, List<Commit> commits, List<Comment> comments, List<PullRequest> pullRequests, List<Issue> issues, List<Release> releases) {
        final Map<LocalDate, Integer> commitFrequency = commits.stream()
                .collect(Collectors.toMap(commit -> commit.getCommit().getCommitter().getDate().toLocalDate(), (counter) -> 1, Integer::sum));

        final Map<LocalDate, Integer> commentFrequency = comments.stream()
                .collect(Collectors.toMap(comment -> comment.getCreatedAt().toLocalDate(), (counter) -> 1, Integer::sum));

        final Map<LocalDate, Integer> pullRequestFrequency = pullRequests.stream()
                .collect(Collectors.toMap(pullRequest -> pullRequest.getCreatedAt().toLocalDate(), (counter) -> 1, Integer::sum));

        final Map<LocalDate, Integer> issueFrequency = issues.stream()
                .collect(Collectors.toMap(issue -> issue.getCreatedAt().toLocalDate(), (counter) -> 1, Integer::sum));

        final Map<LocalDate, Integer> releaseFrequency = releases.stream()
                .collect(Collectors.toMap(release -> release.getPublishedAt().toLocalDate(), (counter) -> 1, Integer::sum));

        repositoryAnalysis.getCommits().addAll(mapToDayCounterList(commitFrequency));
        repositoryAnalysis.getComments().addAll(mapToDayCounterList(commentFrequency));
        repositoryAnalysis.getPullRequests().addAll(mapToDayCounterList(pullRequestFrequency));
        repositoryAnalysis.getIssues().addAll(mapToDayCounterList(issueFrequency));
        repositoryAnalysis.getReleases().addAll(mapToDayCounterList(releaseFrequency));
    }

    private static void addNameValueData(RepositoryAnalysis repositoryAnalysis, List<PullRequest> closedPullRequests, List<Issue> issues) {
        final Map<String, Integer> activeContributors = closedPullRequests.stream()
                .map(pullRequest -> pullRequest.getUser().getLogin())
                .collect(Collectors.toMap(login -> login, (counter) -> 1, Integer::sum));

        final Map<String, Integer> issueTypes = issues.stream()
                .flatMap(issue -> issue.getLabels().stream())
                .map(Label::getName)
                .collect(Collectors.toMap(label -> label, (counter) -> 1, Integer::sum));

        repositoryAnalysis.getActiveContributors().addAll(mapToNameValueList(activeContributors));
        repositoryAnalysis.getIssueTypes().addAll(mapToNameValueList(issueTypes));
    }

    private static void addTimeLengthData(RepositoryAnalysis repositoryAnalysis, List<PullRequest> closedPullRequests, List<Issue> closedIssues) {
        repositoryAnalysis.getPullRequestMergeTimes().addAll(closedPullRequests.stream()
                .filter(pull -> pull.getMergedAt() != null)
                .sorted(Comparator.comparing(PullRequest::getNumber))
                .map(pull -> new TimeLength(pull.getNumber(), ChronoUnit.HOURS.between(pull.getCreatedAt(), pull.getMergedAt())))
                .toList());

        repositoryAnalysis.getIssueCloseTimes().addAll(closedIssues.stream()
                .sorted(Comparator.comparing(Issue::getNumber))
                .map(issue -> new TimeLength(issue.getNumber(), ChronoUnit.HOURS.between(issue.getCreatedAt(), issue.getClosedAt())))
                .toList());
    }

    private static List<DayCounter> mapToDayCounterList(Map<LocalDate, Integer> dateValues) {
        return dateValues.keySet().stream()
                .map(date -> new DayCounter(date, dateValues.get(date)))
                .sorted(Comparator.comparing(DayCounter::getDate))
                .toList();
    }

    private static List<NameValue> mapToNameValueList(Map<String, Integer> nameValues) {
        return nameValues.keySet().stream()
                .limit(15)
                .map(name -> new NameValue(name, nameValues.get(name)))
                .toList();
    }
}
