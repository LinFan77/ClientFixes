@echo off & color 0e
title L1Open Server Console
echo Start Date/Time: %DATE% %TIME%

@@java -server -Xms1024m -Xmx2048m -XX:+UseParallelGC -XX:SurvivorRatio=16 -cp L1OpenServer.jar;lib\c3p0-0.9.5.5.jar;lib\mchange-commons-java-0.2.20.jar;lib\org.eclipse.swt.win32.win32.x86_64_3.100.1.v4234e.jar;lib\javolution-5.5.1.jar;lib\mysql-connector-j-8.0.32.jar;;lib\xmlapi.jar;lib\netty-3.9.5.Final.jar;lib\jaxb-runtime-2.3.1.jar;lib\istack-commons-runtime-4.1.1.jar;lib\xercesImpl-2.12.2.jar; -Djavax.xml.parsers.SAXParserFactory=com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl server.Server
@pause