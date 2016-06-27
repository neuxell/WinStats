package ppenzov;

import java.io.File;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import com.sun.jna.win32.W32APIOptions;

public class WinStats {

 public static String getStats() {
  if(!System.getProperty("os.name").contains("Win"))
	  return "NOT WINDOWS";

  String output = "PROCESSORS, RAM, HARD SPACE";

  /* Total number of processors or cores available to the JVM */
  output += "\nAvailable processors (cores): " +
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
  for (File root: roots) {
   output += "\n\nFile system root: " + root.getAbsolutePath();
   output += "\n\tTotal space (bytes): " + root.getTotalSpace();
   output += "\n\tFree space (bytes): " + root.getFreeSpace();
   output += "\n\tUsable space (bytes): " + root.getUsableSpace();
  }
  return output;
 }

 public static String getProcesses() {
  String output = "PROCESS LIST";

  Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
  Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();

  WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
  try {
   while (kernel32.Process32Next(snapshot, processEntry)) {
    output += "\n" + processEntry.th32ProcessID + "\t" + Native.toString(processEntry.szExeFile);
   }
  } finally {
   kernel32.CloseHandle(snapshot);
  }

  return output;
 }
}