package org.bea;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.h2.server.web.JakartaWebServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        String webAppDirLocation = "src/main/";
        Tomcat tomcat = new Tomcat();

        //Set Port #
        tomcat.setPort(8082);

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webAppDirLocation).getAbsolutePath());
         ctx.setAllowCasualMultipartParsing(true);

        Tomcat.addServlet(ctx, "h2Console", new JakartaWebServlet())
                .addMapping("/h2Console/*");
        ctx.addServletMappingDecoded("/h2Console/*", "h2Console");

        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }
}
