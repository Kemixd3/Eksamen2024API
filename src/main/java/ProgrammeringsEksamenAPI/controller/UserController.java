package ProgrammeringsEksamenAPI.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {


    @GetMapping("/html")
    public String getHtml() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>HTML Page</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Hello, this is an HTML page!</h1>\n" +
                "    <p>This is a paragraph.</p>\n" +
                "</body>\n" +
                "</html>";
    }



}
