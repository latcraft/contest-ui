package lv.latcraft.apps.contest

import com.google.inject.Inject
import groovy.json.JsonSlurper
import org.slf4j.Logger

class TaskRequestProcessor {

  @Inject
  TaskResultDAO results

  @Inject
  Logger logger

  public static String VALID_RESPONSE = '["Practical Vim", "Linux", "Mac OS X Snow Leopard"]'
  public static VALID_RESPONSE_JSON = new JsonSlurper().parseText(VALID_RESPONSE)

  void onRequest(TaskRequest request) {
    String response = null
    String validationMessage = null
    def json = null
    Throwable exception = null
    long stopTime
    long startTime = System.currentTimeMillis()
    try {
      response = new URL("http://${request.solutionHostName}/search?query=Linux+Ubuntu+MacOs").text
    } catch (Throwable t) {
      exception = t
    } finally {
      stopTime = System.currentTimeMillis()
    }
    if (response) {
      try {
        logger.info("Request response: {}", response)
        json = new JsonSlurper().parseText(response)
        logger.info("Request JSON: {}", json)
        assert json == VALID_RESPONSE_JSON
      } catch (Throwable t) {
        validationMessage = "${t.getClass().name}: ${t.message}"
      }
    }
    TaskResult result = new TaskResult(
      solutionHostName: truncate(request.solutionHostName),
      userName: truncate(request.userName),
      startTime: startTime,
      duration: stopTime ? stopTime - startTime : -1,
      exception: exception ? truncate("${exception.getClass().name}: ${exception.message}") : null,
      validation: truncate(validationMessage),
      response: truncate(response)
    )
    logger.debug("Task result: {}", result)
    results.add(result)
  }

  String truncate(String input, int length = 255) {
    input ? input.trim().substring(0, Math.min(input.length(), length)) : null
  }

}
