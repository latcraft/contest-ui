import com.fasterxml.jackson.databind.JsonNode
import lv.latcraft.apps.contest.TaskRequest
import lv.latcraft.apps.contest.TaskRequestQueue
import lv.latcraft.apps.contest.ServiceModule
import lv.latcraft.apps.contest.TaskResultDAO
import org.slf4j.Logger
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode
import static ratpack.registry.Registries.just
import static lv.latcraft.apps.contest.TaskRequestProcessor.*

ratpack {

  bindings {
    add new JacksonModule()
    add new ServiceModule()
  }

  handlers { TaskRequestQueue queue, TaskResultDAO results, Logger logger ->
    get {
      render file("public/index.html")
    }
    get('solution') {
      render json(VALID_RESPONSE_JSON)
    }
    prefix('api/v1') {
      handler {
        logger.info "${request.method} ${request.path}"
        next()
      }
      get('queue/all') {
        render json(requests: queue.all, response: 'OK')
      }
      get('result/top') {
        render json(results: results.top, response: 'OK')
      }
      get('result/last') {
        render json(results: results.last, response: 'OK')
      }
      handler {
        next(just(JsonNode, parse(jsonNode())))
      }
      post('queue/submit') { JsonNode jsonRequest ->
        queue.push(new TaskRequest(
          userName: jsonRequest.get('userName')?.asText(),
          solutionHostName: jsonRequest.get('solutionHostName')?.asText()
        ))
        render json(response: 'OK')
      }
    }
    assets "public"
  }

}
