package com.mobiweb.msm.services;

import com.mobiweb.msm.models.*;
import com.mobiweb.msm.repositories.LocationRepo;
import com.mobiweb.msm.repositories.ProductRepo;
import com.mobiweb.msm.repositories.UserRepo;
import com.mobiweb.msm.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepo userRepo;
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

        LoginDetails loginDetails = new LoginDetails();
        User oneByUsername = userRepo.findOneByUsername(username);
        List<Product> products = productRepo.findAll();
        Location oneByRole = locationRepo.findOneByRole(oneByUsername.getRole());
        loginDetails.setUser(oneByUsername);
        loginDetails.setProducts(products);
        loginDetails.setLocation(oneByRole);
        return loginDetails;
    }


    @Override
    public String getAuthToken(String username, String password) {

        User oneByUsername = userRepo.findOneByUsername(username);
        if (oneByUsername != null) {
            if (oneByUsername.getPassword().equals(password)) {

                Auth auth = new Auth();
                auth.setUsername(username);
                auth.setAuth(UUID.randomUUID().toString());
                Auth auth1 = authService.create(auth);
                Constants.tokenList.add(auth1.getAuth());
                return auth1.getAuth();
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public void logOut(String username) {

        User oneByUsername = userRepo.findOneByUsername(username);
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
