package com.example.rundeck.plugin.example

import com.dtolabs.rundeck.core.logging.LogEvent
import com.dtolabs.rundeck.core.logging.LogEventImpl
import com.dtolabs.rundeck.core.logging.LogLevel
import spock.lang.Specification

class HellowWorldLoggingPluginSpec extends Specification {

    def "Hello world logger"() {
        given:

        HellowWorldLoggingPlugin plugin = new HellowWorldLoggingPlugin()
        def configuration = [:]
        plugin.initialize(configuration)
        plugin.textToAdd = "Hello World"
        plugin.destinationFile = "/opt/checkFile.txt"
        plugin.openStream()

        when:

        plugin.addEvent(eventMessage("msg1"))
        plugin.addEvent(eventMessage("msg2"))
        plugin.addEvent(eventMessage("msg3"))
        plugin.addEvent(eventMessage("msg4"))


        then:
        "msg1\nHello World\nmsg2\nHello World\nmsg3\nHello World\nmsg4\nHello World\n" == new File("/opt/checkFile.txt").text

        cleanup:
        new File("/opt/checkFile.txt").delete()
    }

    LogEvent eventMessage(final String msg) {
        new LogEventImpl("testEvent", new Date(), LogLevel.NORMAL,msg,[:])
    }
}
