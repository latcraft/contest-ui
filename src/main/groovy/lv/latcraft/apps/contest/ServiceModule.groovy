package lv.latcraft.apps.contest

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import groovy.sql.Sql
import gstorm.Gstorm
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    Sql sql = Sql.newInstance("jdbc:hsqldb:file:./db/tasks", "sa", "", "org.hsqldb.jdbcDriver")
    Gstorm gstorm = new Gstorm(sql)
    gstorm.stormify(TaskResult)
    bind(Sql).toInstance(sql)
    bind(Gstorm).toInstance(gstorm)
    bind(Logger).toInstance(LoggerFactory.getLogger("lv.latcraft.contest"))
    bind(TaskResultDAO).in(Scopes.SINGLETON)
    bind(TaskRequestQueue).in(Scopes.SINGLETON)
    bind(TaskRequestProcessor).in(Scopes.SINGLETON)
    TaskRequestBroker broker = new TaskRequestBroker()
    requestInjection(broker)
    bind(TaskRequestBroker).toInstance(broker)
    broker.start()
  }

}
