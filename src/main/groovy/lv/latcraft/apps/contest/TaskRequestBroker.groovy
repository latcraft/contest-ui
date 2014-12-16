package lv.latcraft.apps.contest

import com.google.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TaskRequestBroker {

  @Inject
  TaskRequestQueue queue

  @Inject
  TaskRequestProcessor processor

  Logger logger = LoggerFactory.getLogger(TaskRequestBroker)

  void start() {
    logger.info('Broker started!')
    Thread.start {
      while (true) {
        if (queue && processor) {
          TaskRequest request = queue.get()
          if (request) {
            logger.info('Processing request: {}', request)
            try {
              processor.onRequest(request)
            } catch (Throwable t) {
              logger.error("Problem with request processing!", t)
            }
          }
        }
        sleep(1000)
      }
    }
  }

}
