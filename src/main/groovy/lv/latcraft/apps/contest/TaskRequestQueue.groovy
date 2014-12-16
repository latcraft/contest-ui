package lv.latcraft.apps.contest

import java.util.concurrent.ConcurrentLinkedDeque

class TaskRequestQueue {

  ConcurrentLinkedDeque<TaskRequest> queue = new ConcurrentLinkedDeque<>();

  void push(TaskRequest request) {
    queue.add(request)
  }

  List<TaskRequest> getAll() {
    queue.toList()
  }

  TaskRequest head() {
    queue.peek()
  }

  TaskRequest get() {
    queue.poll()
  }

}
