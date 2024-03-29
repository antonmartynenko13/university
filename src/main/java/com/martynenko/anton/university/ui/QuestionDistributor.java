package com.martynenko.anton.university.ui;

import com.martynenko.anton.university.i18n.LocalizationHelper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

import static com.martynenko.anton.university.i18n.MessageCode.*;

/**
 * A class for communicating with the user in the console and distribution of questions and answers.
 *
 * @author Martynenko Anton
 * @since 1.1
 */
@RequiredArgsConstructor
@Service
public class QuestionDistributor {

  /**
   * Bean serving string resources .
   */

  private final LocalizationHelper localizationHelper;

  /**
   * Service to convert data to String responses   .
   */

  private final AnswerService answerService;


  /**
   * Opens and closes console user interaction .
   *
   */
  public void run() {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println(answerService.getGreetingAnswer());

      while (scanner.hasNext()) {
        String question = scanner.nextLine().trim();


        String answer = answerService.getGoodbyeAnswerIfExit(question);

        if (answer != null) {
          System.out.println(answer);
          break;
        }

        try {
          System.out.println(getAnswer(question));
        } catch (Exception exception) {
          System.out.println(answerService.getExceptionAnswer(exception));
        }

        System.out.println(answerService.getAnythingElseAnswer());
      }
    }
  }

  /**
   * Inner distributing questions method .
   * @param question user's question
   * @return answer
   */

  @NotNull
  private String getAnswer(@NotNull final String question) {

    Optional<String> extraction = localizationHelper.extract(question, QUESTION_DEPARTMENT_HEAD_OF);

    //question number 1
    if (extraction.isPresent()) {

      return answerService.getDepartmentsHeadNameAnswer(extraction.get());
    }

    extraction = localizationHelper.extract(question, QUESTION_DEPARTMENT_STATISTICS);

    //question number 2
    if (extraction.isPresent()) {

      return answerService.getDepartmentEmployeeStatisticsAnswer(extraction.get());

    }

    extraction = localizationHelper.extract(question, QUESTION_DEPARTMENT_SALARY);

    //question number 3
    if (extraction.isPresent()) {

      return answerService.getAvgSalaryStatisticsAnswer(extraction.get());
    }

    extraction = localizationHelper.extract(question, QUESTION_DEPARTMENT_COUNT);

    //question number 4
    if (extraction.isPresent()) {

      return answerService.getEmployeeCountAnswer(extraction.get());
    }

    extraction = localizationHelper.extract(question, QUESTION_EMPLOYEE_SEARCH);

    //question number 5
    if (extraction.isPresent()) { //can be replaced with orElseGet, but let's keep style

      return answerService.getGlobalEmployeeSearchAnswer(extraction.get());
    }

    //incorrect input
    return answerService.getIncorrectInputAnswer();
  }
}
