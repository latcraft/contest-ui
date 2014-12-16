package lv.latcraft.apps.contest

import com.google.inject.Inject
import groovy.json.JsonSlurper
import org.slf4j.Logger

class TaskRequestProcessor {

  @Inject
  TaskResultDAO results

  @Inject
  Logger logger

  void onRequest(TaskRequest request) {
    String response = null
    String validationMessage = null
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
    try {
      def json = new JsonSlurper().parseText(response)
      // TODO: Validate response data
    } catch (Throwable t) {
      validationMessage = "${t.getClass().name}: ${t.message}"
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
    logger.debug("Task result: %1", result)
    results.add(result)
  }

}
