package src.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import src.Models.Receipt;
import src.Models.DTO.ReimbursementRequestDTO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import src.Services.AdminService;
import src.Utils.Calculator;
import src.Utils.Utils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


public class ReimbursementController {


    private final AdminService service;

    private final HttpServer httpServer;

    private final String baseUrl;

    public ReimbursementController( HttpServer httpServer, String baseUr,AdminService service) {
        this.service = service;
        this.httpServer = httpServer;
        this.baseUrl = baseUr;
    }

    public void createContext() {

        httpServer.createContext(baseUrl + "calculate", new ReimbursementController.Calculate(service));

    }


    private static class Calculate implements HttpHandler {

        private final AdminService service;

        public Calculate(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            addCors(exchange);

            var request = exchange.getRequestBody();

            var o = Utils.getRequestToClass(request, ReimbursementRequestDTO.class);

            Calculator calculator = new Calculator.Builder().days(o.getPeriod()).personalCarMillage(o.getPersonalCarMillage()).receipts(o.getReceipts().stream().map(r ->
                    Receipt.createInstance(r.getReceiptName()).copy(r.getAmount())).collect(Collectors.toList())).
                    service(service).build();

            var result = calculator.calculate();

            o.setResult(result);

            sendOk(exchange,o);
        }
    }

    private static <T> void sendOk(HttpExchange exchange, T method) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(method);
        exchange.sendResponseHeaders(200, json.length());
        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }

    private static void addCors(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,User");
            exchange.sendResponseHeaders(200, -1);
            return;
        }
    }

    private static void sentUnauthorized(HttpExchange exchange) throws IOException {
        String unauthorized = "Unauthorized";
        exchange.sendResponseHeaders(401, unauthorized.length());
        OutputStream os = exchange.getResponseBody();
        os.write(unauthorized.getBytes());
        os.close();
    }
}
