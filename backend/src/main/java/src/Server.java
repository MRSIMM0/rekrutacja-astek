package src;

import src.Controllers.AdminController;
import src.Controllers.ReimbursementController;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.DataSource.PseudoDB;
import src.Services.AdminServiceImpl;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {

        int serverPort = 8080;
        String baseUrl = "/api/";
        logger.info(String.format("Servers starting on port %d", serverPort));
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(serverPort), 0);

        new AdminController(httpServer, baseUrl, new AdminServiceImpl(PseudoDB.getInstance())).createContext();

        new ReimbursementController(httpServer, baseUrl, new AdminServiceImpl(PseudoDB.getInstance())).createContext();

        httpServer.setExecutor(null);
        httpServer.start();


    }

}
