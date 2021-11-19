package empapp;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/files/*")
@Slf4j
public class DownloadServlet extends HttpServlet {

    @Inject
    private EmployeesService employeesService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
        Path file = employeesService.getFile(filename);
        String contentType = Files.probeContentType(file);
        log.info("Content type: " + contentType);
        resp.setContentType(contentType);
        resp.setHeader("Content-disposition", "attachment; filename=" + filename);
        Files.copy(file, resp.getOutputStream());
    }

}
