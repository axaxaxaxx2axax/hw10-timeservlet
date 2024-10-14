package org.example.hw;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String timezoneParam = req.getParameter("timezone");
        String formatted = getTimeForTimeZone(timezoneParam);
        resp.getWriter().write(formatted);
        resp.getWriter().close();
    }

    private String getTimeForTimeZone(String timeZoneParam) {
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneId.of("UTC"));
        String timeZone = "UTC";

        if (timeZoneParam != null && !timeZoneParam.isBlank()) {
            try {
                int offsetHours = Integer.parseInt(timeZoneParam.substring(3).trim());
                currentTime = OffsetDateTime.now(ZoneOffset.ofHours(offsetHours));
                timeZone = timeZoneParam.replace(" ", "+");
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                return "Invalid timezone format";
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter) + " " + timeZone;
    }
}
