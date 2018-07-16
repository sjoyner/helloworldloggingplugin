package com.example.rundeck.plugin.example;

import com.dtolabs.rundeck.core.logging.LogEvent;
import com.dtolabs.rundeck.plugins.logging.StreamingLogWriterPlugin;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Plugin(service="StreamingLogWriter",name="hello-world-logger")
@PluginDescription(title="Hello World Logging Plugin", description="Hello world streaming logging plugin")
public class HellowWorldLoggingPlugin implements StreamingLogWriterPlugin {

    private static final byte[] ENDLN = "\n".getBytes();

    @PluginProperty(name = "textToAdd",title = "String to add to logs",description = "Add this string to the logs")
    String textToAdd;
    @PluginProperty(name = "destinationFile",title = "Location for the log file",description = "Log File location")
    String destinationFile;

    private OutputStream logStream;

    public HellowWorldLoggingPlugin(){

    }

    @Override
    public void initialize(final Map<String, ?> context) {
    }

    @Override
    public void openStream() throws IOException {
        logStream = new FileOutputStream(destinationFile);
    }

    @Override
    public void addEvent(final LogEvent event) {
        try {
            logStream.write(event.getMessage().getBytes());
            logStream.write(ENDLN);
            logStream.write(textToAdd.getBytes());
            logStream.write(ENDLN);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            logStream.flush();
            logStream.close();
        } catch(IOException iex) {
            iex.printStackTrace();
        }
    }
}