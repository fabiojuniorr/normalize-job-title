package org.feefo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NormalizeTest {

  private final Normalize normalize = new Normalize();

  public static final String MESSAGE_ERROR_NO_MATCHING_JOB_TITLE_FOUND = "No matching job title found.";
  private static final String MESSAGE_ERROR_INVALID_INPUT_VALUE = "Invalid normalization string. Enter a input with at least 3 characters.";

  @Test
  @DisplayName("Should validate input with null value")
  public void shouldValidateInputWithNullValue() {
    String result = normalize.normalize(null);
    Assertions.assertThat(result)
        .isEqualTo(MESSAGE_ERROR_INVALID_INPUT_VALUE);
  }

  @Test
  @DisplayName("Should validate input with short value")
  public void shouldValidateInputWithShortValue() {
    String result = normalize.normalize("ab");
    Assertions.assertThat(result)
        .isEqualTo(MESSAGE_ERROR_INVALID_INPUT_VALUE);
  }

  @Test
  @DisplayName("Should validate input with empty value")
  public void shouldValidateInputWithEmptyValue() {
    String result = normalize.normalize("");
    Assertions.assertThat(result)
        .isEqualTo(MESSAGE_ERROR_INVALID_INPUT_VALUE);
  }

  @Test
  @DisplayName("Should validate input with spaces only")
  public void shouldValidateInputWithSpacesOnly() {
    String result = normalize.normalize("       ");
    Assertions.assertThat(result)
        .isEqualTo(MESSAGE_ERROR_INVALID_INPUT_VALUE);
  }

  @Test
  @DisplayName("Should validate input with symbols only")
  public void shouldValidateInputWithSymbolsOnly() {
    String result = normalize.normalize("$$$#@!@#");
    Assertions.assertThat(result)
        .isEqualTo(MESSAGE_ERROR_NO_MATCHING_JOB_TITLE_FOUND);
  }

  @Test
  @DisplayName("Should normalize input with special characters and return message error")
  public void shouldNormalizeInputWithSpecialCharacters() {
    String result = normalize.normalize("Archi@tect");
    Assertions.assertThat(result)
        .isEqualTo(MESSAGE_ERROR_NO_MATCHING_JOB_TITLE_FOUND);
  }

  @Test
  @DisplayName("Should normalize input with numbers and return message error")
  public void shouldNormalizeInputWithNumbers() {
    String result = normalize.normalize("Architect123");
    Assertions.assertThat(result)
        .isEqualTo(MESSAGE_ERROR_NO_MATCHING_JOB_TITLE_FOUND);
  }

  @Test
  @DisplayName("Should normalize input value and return message error when no matching job titles found")
  public void shouldNormalizeInputValueAndReturnMessageErrorWhenNoMatchingJobTitleFound() {
    String result = normalize.normalize("Doctor");
    Assertions.assertThat(result)
        .isEqualTo(MESSAGE_ERROR_NO_MATCHING_JOB_TITLE_FOUND);
  }

  @Test
  @DisplayName("Should normalize input value and return exact job title")
  public void shouldNormalizeInputValueAndReturnExactJobTitle() {
    String result = normalize.normalize("Architect");
    Assertions.assertThat(result)
        .isEqualTo("Architect");
  }

  @Test
  @DisplayName("Should normalize partial input value and return exact job title")
  public void shouldNormalizePartialInputValueAndReturnExactJobTitle() {
    String result = normalize.normalize("Soft eng");
    Assertions.assertThat(result)
        .isEqualTo("Software engineer");
  }

  @Test
  @DisplayName("Should normalize mix input value and return exact job title")
  public void shouldNormalizeMixInputValueAndReturnExactJobTitle() {
    String result = normalize.normalize("archITECT");
    Assertions.assertThat(result)
        .isEqualTo("Architect");
  }

  @Test
  @DisplayName("Should normalize multiple tokens input value and return exact job title")
  public void shouldNormalizeMultipleTokensInputValueAndReturnExactJobTitle() {
    String result = normalize.normalize("Quantity sur");
    Assertions.assertThat(result)
        .isEqualTo("Quantity surveyor");
  }

  @Test
  @DisplayName("Should normalize and calculating the best score matching token and return exact job title")
  public void shouldNormalizeInputValueAndCalculatingTheBestScoreMatchingToken() {
    String result = normalize.normalize("Software engineer Architect Software engineer");
    Assertions.assertThat(result)
        .isEqualTo("Software engineer");
  }
}