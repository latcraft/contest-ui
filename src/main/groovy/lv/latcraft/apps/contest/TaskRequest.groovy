package lv.latcraft.apps.contest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.AutoClone
import groovy.transform.Canonical

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY

@Canonical
@JsonInclude(NON_EMPTY)
@AutoClone()
@JsonIgnoreProperties(ignoreUnknown = true)
class TaskRequest {
  String userName
  String solutionHostName
  boolean processing = false
}
