package tr.com.frontech.hw.server1.service.server;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.ProtectionDomain;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Created by Mert on 15.2.2015.
 *
 * Test purpose
 */
public class StandaloneJettyServer {

    /**
     * Main method.
     */
    public static void main(String...args) throws IOException {
        Server server = new Server();
        SocketConnector connector = new SocketConnector();

        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        connector.setPort(7676);
        server.setConnectors(new Connector[] { connector });

        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath("/");

        ProtectionDomain protectionDomain = StandaloneJettyServer.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();

        location = ClassLoader.getSystemClassLoader().getResource("./");
        File file = new File(location.getPath());

        System.out.println(file.getParent() + "\\" + "ROOT" );
        context.setWar(file.getParent() + "\\" + "ROOT");

        server.addHandler(context);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
