package service;

import dao.UserDAO;
import db.Session;

public class ProfileService {

    UserDAO userDAO = new UserDAO();

    public void viewProfile() {

        userDAO.viewProfile(Session.currentUserId);
    }
}