package src.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import src.Models.DTO.AdminFiledDTO;
import src.Models.DTO.LimitDTO;
import src.Models.DTO.ReceiptDTO;
import src.Models.Limit.LimitType;
import src.Models.Receipt;
import src.Services.AdminService;
import src.Utils.Utils;
import java.io.IOException;
import java.io.OutputStream;


public class AdminController {


    private final HttpServer httpServer;
    private final String url;

    private final AdminService adminService;

    public AdminController(HttpServer httpServer, String url, AdminService adminService) {
        this.httpServer = httpServer;
        this.url = url;
        this.adminService = adminService;
    }

    public void createContext() {

        httpServer.createContext(url + "addRecipe", new AdminController.AddRecipe(adminService));
        httpServer.createContext(url + "updateRecipe", new AdminController.UpdateRecipe(adminService));
        httpServer.createContext(url + "deleteRecipe", new AdminController.DeleteRecipe(adminService));
        httpServer.createContext(url + "dailyAllowance", new AdminController.DailyAllowance(adminService));
        httpServer.createContext(url + "dailyMillage", new AdminController.DailyMillage(adminService));
        httpServer.createContext(url + "getReceipts", new AdminController.AllRecopies(adminService));
        httpServer.createContext(url + "getAllData", new AdminController.AllData(adminService));
        httpServer.createContext(url + "addLimit", new AdminController.AddLimit(adminService));
        httpServer.createContext(url + "deleteLimit", new AdminController.DeleteLimit(adminService));

    }


    private static class AddRecipe implements HttpHandler {

        private final AdminService service;

        public AddRecipe(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            addCors(exchange);

            if (!exchange.getRequestHeaders().containsKey("User")) {
                sentUnauthorized(exchange);
            }

            if (exchange.getRequestHeaders().get("User").contains("Admin")) {
                var body = Utils.getRequestToClass(exchange.getRequestBody(), ReceiptDTO.class);

                service.addReceipt(Receipt.createInstance(body.getReceiptName()));
                if (body.getLimit() != null) {
                    service.setRecipeTypeLimit(body.getReceiptName(), body.getLimit());
                }

                sendOk(exchange, service.getAllRecipes());


            } else {
                sentUnauthorized(exchange);
            }
        }
    }

    private static class DeleteRecipe implements HttpHandler {
        private final AdminService service;

        public DeleteRecipe(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            addCors(exchange);

            if (!exchange.getRequestHeaders().containsKey("User")) {
                sentUnauthorized(exchange);
            }

            if (exchange.getRequestHeaders().get("User").contains("Admin")) {
                var body = Utils.getRequestToClass(exchange.getRequestBody(), ReceiptDTO.class);

                try {
                    service.deleteReceipts(body.getReceiptName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                sendOk(exchange, service.getAllRecipes());
            } else {
                sentUnauthorized(exchange);
            }
        }
    }


    private static class DailyAllowance implements HttpHandler {

        private final AdminService service;

        public DailyAllowance(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            addCors(exchange);

            if (!exchange.getRequestHeaders().containsKey("User")) {
                sentUnauthorized(exchange);
            }
            if (exchange.getRequestHeaders().get("User").contains("Admin")) {

                var body = Utils.getRequestToClass(exchange.getRequestBody(), AdminFiledDTO.class);
                if (body.allowenceRade != null) {

                    service.setDailyAllowance(body.allowenceRade);

                    sendOk(exchange, new AdminFiledDTO(null, service.getDailyAllowence(), service.getDailyMillage(), service.getAllRecipes(), service.getLimitTypes(), service.getOtherLimits()));

                } else {
                    sentUnauthorized(exchange);
                }
            } else {
                sentUnauthorized(exchange);
            }
        }

    }

    private static class DailyMillage implements HttpHandler {

        private final AdminService service;

        public DailyMillage(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            addCors(exchange);

            if (!exchange.getRequestHeaders().containsKey("User")) {
                sentUnauthorized(exchange);
            }
            if (exchange.getRequestHeaders().get("User").contains("Admin")) {


                var body = Utils.getRequestToClass(exchange.getRequestBody(), AdminFiledDTO.class);
                if (body.dailyMillage != null) {
                    service.setCarMileageRate(body.dailyMillage);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    var el = new AdminFiledDTO(null, service.getDailyAllowence(), service.getDailyMillage(), service.getAllRecipes(), service.getLimitTypes(), service.getOtherLimits());
                    var res = gson.toJson(el);

                    exchange.sendResponseHeaders(200, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                } else {
                    sentUnauthorized(exchange);
                }
            } else {
                sentUnauthorized(exchange);
            }
        }
    }

    private static class AllRecopies implements HttpHandler {


        private final AdminService service;

        public AllRecopies(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            if (!exchange.getRequestMethod().equals("GET")) throw new UnsupportedOperationException();
            addCors(exchange);
            sendOk(exchange, service.getAllRecipes());
        }
    }


    private static class AllData implements HttpHandler {
        private final AdminService service;

        public AllData(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equals("GET")) throw new UnsupportedOperationException();
            addCors(exchange);
            sendOk(exchange, new AdminFiledDTO(null, service.getDailyAllowence(), service.getDailyMillage(), service.getAllRecipes(), service.getLimitTypes(), service.getOtherLimits()));

        }
    }


    private static class UpdateRecipe implements HttpHandler {
        private final AdminService service;

        public UpdateRecipe(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            addCors(exchange);

            if (!exchange.getRequestHeaders().containsKey("User")) {
                sentUnauthorized(exchange);
            }
            if (exchange.getRequestHeaders().get("User").contains("Admin")) {


                var body = Utils.getRequestToClass(exchange.getRequestBody(), ReceiptDTO.class);
                service.setRecipeTypeLimit(body.getReceiptName(), body.getLimit());
                sendOk(exchange, service.getAllRecipes());


            } else {
                sentUnauthorized(exchange);
            }
        }
    }

    private static class AddLimit implements HttpHandler {

        private final AdminService service;

        public AddLimit(AdminService adminService) {
            this.service = adminService;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCors(exchange);

            if (!exchange.getRequestHeaders().containsKey("User")) {
                sentUnauthorized(exchange);
            }
            if (exchange.getRequestHeaders().get("User").contains("Admin")) {
                var body = Utils.getRequestToClass(exchange.getRequestBody(), LimitDTO.class);
                try {
                    service.setOtherLimit(LimitType.valueOf(body.getLimitType()), body.getLimit());
                } catch (IllegalAccessException e) {
                    sentUnauthorized(exchange);
                }
                sendOk(exchange, service.getOtherLimits());

            } else {
                sentUnauthorized(exchange);
            }
        }

    }

    private static class DeleteLimit implements HttpHandler {

        private final AdminService service;

        public DeleteLimit(AdminService service) {
            this.service = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            addCors(exchange);
            if (!exchange.getRequestHeaders().containsKey("User")) {
                sentUnauthorized(exchange);
            }
            if (exchange.getRequestHeaders().get("User").contains("Admin")) {
                var body = Utils.getRequestToClass(exchange.getRequestBody(), LimitDTO.class);
                service.deleteOtherLimit(LimitType.valueOf(body.getLimitType()));
                sendOk(exchange, service.getOtherLimits());

            } else {
                sentUnauthorized(exchange);
            }
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
