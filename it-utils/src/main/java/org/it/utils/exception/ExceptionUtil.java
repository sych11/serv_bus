package org.it.utils.exception;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class ExceptionUtil {

	public static String getPrintStackTraceAsString(Throwable thr) {
		CharArrayWriter writer = new CharArrayWriter();
		PrintWriter writer2 = new PrintWriter(writer, true);
		thr.printStackTrace(writer2);
		writer2.flush();
		writer.flush();
		writer.close();
		return writer.toString();
	}
}
