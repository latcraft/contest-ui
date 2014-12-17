package lv.latcraft.apps.contest

import com.google.inject.Inject
import groovy.json.JsonSlurper
import org.slf4j.Logger

class TaskRequestProcessor {

  @Inject
  TaskResultDAO results

  @Inject
  Logger logger

  private static String VALID_RESPONSE = '["Practical Vim", "The Linux Command Line", "Mac OS X Snow Leopard"]'
  private static VALID_RESPONSE_JSON = new JsonSlurper().parseText(VALID_RESPONSE)

  void onRequest(TaskRequest request) {
    String response = null
    String validationMessage = null
    def json = null
    Throwable exception = null
    long stopTime
    long startTime = System.currentTimeMillis()
    try {
      response = new URL("http://${request.solutionHostName}?q=Linux+Ubuntu+MacOs").text
    } catch (Throwable t) {
      exception = t
    } finally {
      stopTime = System.currentTimeMillis()
    }
    if (response) {
      try {
        logger.debug("Request response: {}", response)
        json = new JsonSlurper().parseText(response)
        logger.debug("Request JSON: {}", json)
        assert json == VALID_RESPONSE_JSON
      } catch (Throwable t) {
        validationMessage = "${t.getClass().name}: ${t.message}"
      }
    }
    TaskResult result = new TaskResult(
      solutionHostName: request.solutionHostName,
      userName: request.userName,
      startTime: startTime,
      duration: stopTime ? stopTime - startTime : -1,
      exception: exception ? "${exception.getClass().name}: ${exception.message}" : null,
      validation: validationMessage,
      response: response
    )
    logger.debug("Task result: {}", result)
    results.add(result)
  }

}
