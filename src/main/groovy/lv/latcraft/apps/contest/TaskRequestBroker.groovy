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
          TaskRequest request = queue.head()
          if (request) {
            request.processing = true
            logger.info('Processing request: {}', request)
            try {
              processor.onRequest(request)
              logger.info('Request done: {}', request)
            } catch (Throwable t) {
              logger.error("Problem with request processing!", t)
            } finally {
              queue.get()
            }
          }
        }
        sleep(1000)
      }
    }
  }

}
