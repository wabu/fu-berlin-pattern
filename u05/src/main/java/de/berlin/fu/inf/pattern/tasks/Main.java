package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.util.Threads;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;

public class Main {
	public static void main(String[] args) throws IOException, FileNotFoundException, InterruptedException {
        Logger logger = Logger.getRootLogger();
        logger.info("Task 10)");
		Main10.main(args);
        logger.info("Task 11)");
		Main11.main(args);
        Threads.shutdown();
	}
}
