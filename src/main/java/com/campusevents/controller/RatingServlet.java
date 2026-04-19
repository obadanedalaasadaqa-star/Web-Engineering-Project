package com.campusevents.controller;

import com.campusevents.dao.RatingDAO;
import com.campusevents.model.Rating;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class RatingServlet extends HttpServlet {

    private final RatingDAO ratingDAO = new RatingDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId  = (String) req.getSession().getAttribute("userId");
        String eventId = req.getParameter("eventId");
        String review  = req.getParameter("review");
        int    stars   = Integer.parseInt(req.getParameter("stars"));

        try {
            Rating rating = new Rating();
            rating.setUserId(userId);
            rating.setEventId(eventId);
            rating.setStars(stars);
            rating.setReview(review);
            ratingDAO.create(rating);
        } catch (Exception e) {
            // Already rated — silently ignore duplicate
        }
        resp.sendRedirect(req.getContextPath() + "/events/detail/" + eventId);
    }
}
