package com.mobiweb.msm.controllers;

import com.mobiweb.msm.exceptions.UnAuthorizedException;
import com.mobiweb.msm.models.*;
import com.mobiweb.msm.models.enums.ProductType;
import com.mobiweb.msm.models.enums.Role;
import com.mobiweb.msm.services.*;
import com.mobiweb.msm.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/mk/webservice")
public class MSMController {
    @Autowired
    IncentiveMessageService incentiveMessageService;
    @Autowired
    ReportService reportService;
    @Autowired
    LoginService loginService;
    @Autowired
    MessageService messageService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ProductService productService;
    @Autowired
    SalesService salesService;
    @Autowired
    TechnicalService technicalService;
    @Autowired
    UserService userService;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    ImageService imageService;
    @Autowired
    DealerInfoService dealerInfoService;

    public MSMController() {
        Constants.tokenList = new ArrayList<>();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleMethodArgumentNotValidException(UnAuthorizedException e) {
        return e.getMessage();
    }

    public void checkIfValidToken() {
        boolean b = loginService.verifyToken();
        if (!b) {
            throw new UnAuthorizedException("Unauthorized login again");
        }
    }

// incentive messages

    @RequestMapping(value = "/incentivemessage", method = RequestMethod.POST)
    public void setIncentiveMessage(@RequestBody @Valid IncentiveMessage incentiveMessage) {
        checkIfValidToken();
        incentiveMessageService.create(incentiveMessage);
    }

    @RequestMapping(value = "/incentivemessage", method = RequestMethod.PUT)
    public void updateIncentiveMessage(@RequestBody @Valid IncentiveMessage incentiveMessage) {
        checkIfValidToken();
        incentiveMessageService.update(incentiveMessage);
    }

    @RequestMapping(value = "/incentivemessage/{id}", method = RequestMethod.GET)
    public IncentiveMessage getIncentiveMessage(@PathVariable("id") final long id) {
        checkIfValidToken();
        return incentiveMessageService.retrieve(id);
    }

    @RequestMapping(value = "/incentivemessage/{id}", method = RequestMethod.DELETE)
    public IncentiveMessage deleteIncentiveMessage(@PathVariable("id") final long id) {
        checkIfValidToken();
        return incentiveMessageService.delete(id);
    }

    @RequestMapping(value = "/incentivemessage/getall", method = RequestMethod.GET)
    public List<IncentiveMessage> deleteIncentiveMessage() {
        checkIfValidToken();
        return incentiveMessageService.getAll();
    }

    @RequestMapping(value = "/incentivemessage/leaders/{id}", method = RequestMethod.GET)
    public List<Sales> getLeaderByIncentive(@PathVariable("id") final long id) {
        checkIfValidToken();
        return incentiveMessageService.getIncentiveQualifiedUsers(id);
    }


    //Leader board

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    public List<Leader> getLeaders(@RequestParam("from") String startDate, @RequestParam("to") String endDate) {
        checkIfValidToken();
        return reportService.getLeaderBoard(startDate, endDate);
    }

    // Login service
    @RequestMapping(value = "/login/details", method = RequestMethod.GET)
    public LoginDetails logindetails(@RequestParam("username") final String username) {
        checkIfValidToken();
        return loginService.getLoginDetails(username);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String authToken(@RequestParam("username") String username, @RequestParam("password") String password) {
        return loginService.getAuthToken(username, password);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(@RequestParam("username") String username) {
        loginService.logOut(username);
    }

    // Message Service

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public void setMessage(@RequestBody @Valid Message message) {
        checkIfValidToken();
        messageService.create(message);
    }

    @RequestMapping(value = "/message", method = RequestMethod.PUT)
    public void updateMessage(@RequestBody @Valid Message Message) {
        checkIfValidToken();
        messageService.update(Message);
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    public Message getMessage(@PathVariable("id") final long id) {
        checkIfValidToken();
        return messageService.retrieve(id);
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE)
    public Message deleteMessage(@PathVariable("id") final long id) {
        checkIfValidToken();
        return messageService.delete(id);
    }

    @RequestMapping(value = "/message/role/{role}", method = RequestMethod.GET)
    public List<Message> deleteMessage(@PathVariable("role") Role role) {
        checkIfValidToken();
        return messageService.getMessageByRole(role);
    }

    //Payment service

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public Payment setPayment(@RequestBody @Valid Payment payment) {
        checkIfValidToken();
        return paymentService.create(payment);
    }

    @RequestMapping(value = "/payment", method = RequestMethod.PUT)
    public void updatePayment(@RequestBody @Valid Payment payment) {
        checkIfValidToken();
        paymentService.update(payment);
    }

    @RequestMapping(value = "/payment/{id}", method = RequestMethod.GET)
    public Payment getPayment(@PathVariable("id") final long id) {
        checkIfValidToken();
        return paymentService.retrieve(id);
    }

    @RequestMapping(value = "/payment/{id}", method = RequestMethod.DELETE)
    public Payment deletePayment(@PathVariable("id") final long id) {
        checkIfValidToken();
        return paymentService.delete(id);
    }

    //Product service

    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    public void updateProduct(@RequestBody @Valid Product product) {
        checkIfValidToken();
        productService.update(product);
    }

    @RequestMapping(value = "/product/id/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable("id") final long id) {
        checkIfValidToken();
        return productService.retrieve(id);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public Product deleteProduct(@PathVariable("id") final long id) {
        checkIfValidToken();
        return productService.delete(id);
    }

    @RequestMapping(value = "/product/getall", method = RequestMethod.GET)
    public List<Product> getProduct() {
        checkIfValidToken();
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public void setProduct(@RequestBody @Valid Product product) {
        checkIfValidToken();
        productService.create(product);
    }

    // Sales

    @RequestMapping(value = "/sales", method = RequestMethod.POST)
    public void setSales(@RequestBody @Valid Sales sales) {
        checkIfValidToken();
        salesService.create(sales);
    }

    @RequestMapping(value = "/sales", method = RequestMethod.PUT)
    public void updateSales(@RequestBody @Valid Sales sales) {
        checkIfValidToken();
        salesService.update(sales);
    }

    @RequestMapping(value = "/sales/{id}", method = RequestMethod.GET)
    public Sales getSales(@PathVariable("id") final long id) {
        checkIfValidToken();
        return salesService.retrieve(id);
    }

    @RequestMapping(value = "/sales/{id}", method = RequestMethod.DELETE)
    public Sales deleteSales(@PathVariable("id") final long id) {
        checkIfValidToken();
        return salesService.delete(id);
    }

    @RequestMapping(value = "/sales/report", method = RequestMethod.GET)
    public List<Sales> getSales(@ModelAttribute("validate") Void validate, @RequestParam("from") String startDate, @RequestParam("to") String endDate) {
        checkIfValidToken();
        checkIfValidToken();
        return salesService.salesReport(startDate, endDate);
    }

    @RequestMapping(value = "/sales/report/brand", method = RequestMethod.GET)
    public List<Sales> getSalesByBrand(@RequestParam("from") String startDate, @RequestParam("to") String endDate, @RequestParam("category") ProductType productType) {
        checkIfValidToken();
        return salesService.salesReportByBrand(startDate, endDate, productType);
    }

    @RequestMapping(value = "/sales/user", method = RequestMethod.GET)
    public List<Sales> getSalesByUser(@RequestParam("from") String startDate, @RequestParam("to") String endDate, @RequestParam("username") String username) {
        checkIfValidToken();
        return salesService.salesByUser(startDate, endDate, username);
    }


    // Technical

    @RequestMapping(value = "/technical", method = RequestMethod.POST)
    public void setTechnical(@RequestBody @Valid Technical technical) {
        checkIfValidToken();
        technicalService.create(technical);
    }

    @RequestMapping(value = "/technical", method = RequestMethod.PUT)
    public void updateTechnical(@RequestBody @Valid Technical technical) {
        checkIfValidToken();
        technicalService.update(technical);
    }

    @RequestMapping(value = "/technical/{id}", method = RequestMethod.GET)
    public Technical getTechnical(@PathVariable("id") final long id) {
        checkIfValidToken();
        return technicalService.retrieve(id);
    }

    @RequestMapping(value = "/technical/getall", method = RequestMethod.GET)
    public List<Technical> getTechnicalForUser() {
        checkIfValidToken();
        return technicalService.ListForTechnicians();
    }

    @RequestMapping(value = "/technical/{id}", method = RequestMethod.DELETE)
    public Technical deleteTechnical(@PathVariable("id") final long id) {
        checkIfValidToken();
        return technicalService.delete(id);
    }

    @RequestMapping(value = "/technical/report", method = RequestMethod.GET)
    public List<Technical> getTechnical(@RequestParam("from") String startDate, @RequestParam("to") String endDate) {
        checkIfValidToken();
        return technicalService.serviceReport(startDate, endDate);
    }

    // Using the return type of sales because response demands so
    @RequestMapping(value = "/technical/report/brand", method = RequestMethod.GET)
    public List<Sales> getTechnicalByBrand(@RequestParam("from") String startDate, @RequestParam("to") String endDate) {
        checkIfValidToken();
        return technicalService.serviceReportByBrand(startDate, endDate);
    }

    @RequestMapping(value = "/technical/user", method = RequestMethod.GET)
    public List<Technical> getServiceByTechs(@RequestParam("from") String startDate, @RequestParam("to") String endDate, @RequestParam("username") String username) {
        checkIfValidToken();
        return technicalService.servicesByUser(startDate, endDate, username);
    }

    // User

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void setUser(@RequestBody @Valid User user) {
        checkIfValidToken();
        userService.create(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void updateUser(@RequestBody @Valid User user) {
        checkIfValidToken();
        userService.update(user);
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") final long id) {
        checkIfValidToken();
        return userService.retrieve(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public User deleteUser(@PathVariable("id") final long id) {
        checkIfValidToken();
        return userService.delete(id);
    }

    @RequestMapping(value = "/user/username/{username}", method = RequestMethod.DELETE)
    public User softDeleteUsername(@PathVariable("username") final String username) {
        checkIfValidToken();
        return userService.deleteUsername(username);
    }

    @RequestMapping(value = "/user/username/{username}/{gcmID}", method = RequestMethod.GET)
    public User updateGCMID(@PathVariable("username") final String username, @PathVariable("gcmID") String regID) {
        checkIfValidToken();
        return userService.updateGcmID(username, regID);
    }

    @RequestMapping(value = "/user/username/{username}/userinfo", method = RequestMethod.PATCH)
    public User patchUserInfo(@PathVariable("username") final String accountId, @RequestBody @Valid final HashMap<String, String> userInfo) {
        checkIfValidToken();
        return userService.patch(accountId, userInfo);
    }

    @RequestMapping(value = "/user/username/{username}", method = RequestMethod.GET)
    public User getUsername(@PathVariable("username") final String username) {
        checkIfValidToken();
        return userService.getUserFromUsername(username);
    }

    @RequestMapping(value = "/user/username/getall", method = RequestMethod.GET)
    public List<User> getAllUsername() {
        checkIfValidToken();
        return userService.getAllUser();
    }

    // Purchase

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public Purchase setVendorPurchase(@RequestBody @Valid Purchase purchase) {
        checkIfValidToken();
        return purchaseService.create(purchase);
    }

    @RequestMapping(value = "/purchase", method = RequestMethod.PUT)
    public void updateVendorPurchase(@RequestBody @Valid Purchase purchase) {
        checkIfValidToken();
        purchaseService.update(purchase);
    }

    @RequestMapping(value = "/purchase/{id}", method = RequestMethod.GET)
    public Purchase getVendorPurchase(@PathVariable("id") final long id) {
        checkIfValidToken();
        return purchaseService.retrieve(id);
    }

    @RequestMapping(value = "/purchase/{id}", method = RequestMethod.DELETE)
    public Purchase deleteVendorPurchase(@PathVariable("id") final long id) {
        checkIfValidToken();
        return purchaseService.delete(id);
    }

    @RequestMapping(value = "/purchase/report", method = RequestMethod.GET)
    public List<ExpenseManager> getVendorPurchase(@RequestParam("from") String startDate) {
        checkIfValidToken();
        return purchaseService.getExpenseDetails(startDate);
    }

    // DealerInfo

    @RequestMapping(value = "/dealerinfo", method = RequestMethod.POST)
    public DealerInfo setDealerInfo(@RequestBody @Valid DealerInfo dealerInfo) {
        checkIfValidToken();
        return dealerInfoService.create(dealerInfo);
    }

    @RequestMapping(value = "/dealerinfo", method = RequestMethod.PUT)
    public void updateDealerInfo(@RequestBody @Valid DealerInfo purchase) {
        checkIfValidToken();
        dealerInfoService.update(purchase);
    }

    @RequestMapping(value = "/dealerinfo/{id}", method = RequestMethod.GET)
    public DealerInfo getDealerInfo(@PathVariable("id") final long id) {
        checkIfValidToken();
        return dealerInfoService.retrieve(id);
    }

    @RequestMapping(value = "/dealerinfo/{id}", method = RequestMethod.DELETE)
    public DealerInfo deleteDealerInfo(@PathVariable("id") final long id) {
        checkIfValidToken();
        return dealerInfoService.delete(id);
    }


    //insert image
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public void handleFileUpload(@RequestParam("uploadfile") MultipartFile uploadFile) {checkIfValidToken();//        imageService.insertFile(uploadFile);
//
//    }


}
