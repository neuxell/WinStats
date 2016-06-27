package ppenzov;

import java.io.File;

public class WinStats{
	  public static String getStats() {
		    String output = "";
		    
		    /* Total number of processors or cores available to the JVM */
		    output += "Available processors (cores): " + 
		        Runtime.getRuntime().availableProcessors();

		    /* Total amount of free memory available to the JVM */
		    output += "\nFree memory (bytes): " + 
		        Runtime.getRuntime().freeMemory();

		    /* This will return Long.MAX_VALUE if there is no preset limit */
		    long maxMemory = Runtime.getRuntime().maxMemory();
		    /* Maximum amount of memory the JVM will attempt to use */
		    output += "\nMaximum memory (bytes): " + 
		        (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory);

		    /* Total memory currently available to the JVM */
		    output += "\nTotal memory available to JVM (bytes): " + 
		        Runtime.getRuntime().totalMemory();

		    /* Get a list of all filesystem roots on this system */
		    File[] roots = File.listRoots();

		    /* For each filesystem root, put some info into output string */
		    for (File root : roots) {
		      output += "\n\nFile system root: " + root.getAbsolutePath();
		      output += "\n\tTotal space (bytes): " + root.getTotalSpace();
		      output += "\n\tFree space (bytes): " + root.getFreeSpace();
		      output += "\n\tUsable space (bytes): " + root.getUsableSpace();
		    }
		    return output;
		  }
}
