package lv.latcraft.apps.contest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.AutoClone
import groovy.transform.Canonical
import gstorm.WithoutId

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY

@Canonical
@JsonInclude(NON_EMPTY)
@AutoClone()
@JsonIgnoreProperties(ignoreUnknown = true)
@WithoutId
class TaskResult {
  String userName
  String solutionHostName
  Long startTime
  Long duration
  String exception
  String response
  String validation
}
