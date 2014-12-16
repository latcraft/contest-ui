package lv.latcraft.apps.contest

import groovy.sql.GroovyRowResult

class TaskResultDAO {

  void add(TaskResult result) {
    result.save()
  }

  List<TaskResult> getAll() {
    TaskResult.all.collect { mapRecord(it) }
  }

  List<TaskResult> getLast() {
    TaskResult.where('1=1 order by startTime desc').take(20).collect { mapRecord(it) }
  }

  List<TaskResult> getTop() {
    TaskResult.where('validation is null and exception is null order by duration asc, startTime asc').take(20).collect { mapRecord(it) }
  }

  TaskResult mapRecord(GroovyRowResult record) {
    new TaskResult(
      userName: record.userName,
      duration: record.duration,
      startTime: record.startTime,
      exception: record.exception,
      validation: record.validation,
      solutionHostName: record.solutionHostName,
      response: record.response
    )
  }

}
