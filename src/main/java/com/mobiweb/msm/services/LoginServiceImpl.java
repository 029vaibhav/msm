package com.mobiweb.msm.services;

import com.mobiweb.msm.exceptions.InvalidCredentials;
import com.mobiweb.msm.exceptions.error.ErrorMessage;
import com.mobiweb.msm.exceptions.UserDoesNotExists;
import com.mobiweb.msm.models.*;
import com.mobiweb.msm.repositories.LocationRepo;
import com.mobiweb.msm.repositories.ProductRepo;
import com.mobiweb.msm.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    private final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    UserService userService;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    LocationRepo locationRepo;
    @Autowired
    AuthService authService;
    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public LoginDetails getLoginDetails(String username) {

        try {
            LoginDetails loginDetails = new LoginDetails();
            User oneByUsername = userService.getUserFromUsername(username);
            List<Product> products = productRepo.findAll();
            Location oneByRole = locationRepo.findOneByRole(oneByUsername.getRole());
            loginDetails.setUser(oneByUsername);
            loginDetails.setProducts(products);
            loginDetails.setLocation(oneByRole);
            return loginDetails;
        } catch (Exception e) {
            log.error("unknown error" + e.getMessage());
            throw e;
        }
    }


    @Override
    public Auth getAuthToken(String username, String password) {

        User oneByUsername = userService.getUserFromUsername(username);
        if (oneByUsername != null && oneByUsername.getDeleted() == 0) {
            if (oneByUsername.getPassword().equals(password)) {

                Auth auth = new Auth();
                auth.setUsername(username);
                auth.setAuth(UUID.randomUUID().toString());
                Auth auth1 = authService.create(auth);
                Constants.tokenList.add(auth1.getAuth());
                return auth1;
            } else {
                log.error("invalid cred username" + username + " pass" + password);
                throw new InvalidCredentials(ErrorMessage.INVALID_CRED);
            }
        } else {
            log.error("invalid account username" + username + " pass" + password);
            throw new UserDoesNotExists(ErrorMessage.N0_USER);
        }

    }

    @Override
    public void logOut(String username) {

        User oneByUsername = userService.getUserFromUsername(username);
        if (oneByUsername != null) {
            List<Auth> auth = authService.retrieveByUsername(username);
            if (auth != null && auth.size() > 0) {
                auth.stream().forEach(auth1 -> {
                    Auth delete = authService.delete(auth1.getId());
                    try {
                        Constants.tokenList.remove(delete.getAuth());
                    } catch (NullPointerException e) {
                    }
                });
            }
        }
    }

    public boolean verifyToken() {
        String auth = httpServletRequest.getHeader("AUTH");
        boolean contains = Constants.tokenList.contains(auth);
        if (contains) {
            return true;
        }
        Auth auth1 = authService.retrieveByAuth(auth);
        if (auth1 != null) {
            return true;
        }
        return false;
    }
}
