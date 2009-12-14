package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.util.Threads;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Main7.main(args);
		Main9.main(args);
        Threads.shutdown();
	}
}
