package org.feefo;

import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author fabio.rocha
 *
 * @apiNote The {@code Normalize} class provides functionality to normalize input strings by matching tokens
 * to a predefined list of job titles. If a match is found, the corresponding job title is returned;
 * otherwise, an appropriate error message is returned.
 */
public class Normalize {

  /**
   * A predefined list of known job titles.
   */
  private static final List<String> jobTitles = List.of("Architect", "Software engineer",
      "Quantity surveyor", "Accountant");

  /**
   * Normalizes the input string by matching tokens to known job titles.
   *
   * @param input The input string to be normalized.
   * @return A matching job title or an error message.
   */
  public String normalize(String input) {
    if (Objects.isNull(input) || input.trim().length() < 3) {
      return "Invalid normalization string. Enter a input with at least 3 characters.";
    }

    Map<String, Double> jobTitlesScore = jobTitles.stream()
        .collect(toMap(jobTitle -> jobTitle, jobTitle -> calculateScore(input, jobTitle)));

    return getJobTitleWithTheBestScore(jobTitlesScore);
  }

  /**
   * Retrieves the job title with the highest score from the map of job titles and their scores.
   * If all score titles is zero, return message error matching job title not found
   *
   * @param jobTitlesScore A map containing job titles as keys and their respective scores as
   *  values.
   * @return The job title with the highest score.
   */
  private String getJobTitleWithTheBestScore(Map<String, Double> jobTitlesScore) {
    if (Collections.max(jobTitlesScore.values()) == 0)
      return "No matching job title found.";

    return Collections.max(jobTitlesScore.entrySet(), Entry.comparingByValue()).getKey();
  }

  /**
   * Calculates a score indicating how well the input string matches the given job title.
   *
   * @param input    The input string to be matched.
   * @param jobTitle The job title to be scored against the input string.
   * @return A score indicating the degree of match between the input string and the job title.
   */
  private Double calculateScore(String input, String jobTitle) {
    if (input.equalsIgnoreCase(jobTitle)) {
      return 1.0;
    }

    String[] tokens = input.split(" ");
    double tokenScore = 1.0 / tokens.length;

    return Stream.of(tokens)
        .filter(token -> jobTitle.toLowerCase().contains(token.toLowerCase()))
        .mapToDouble(token -> tokenScore).sum();
  }
}
