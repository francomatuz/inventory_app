
package com.company.Inventaryapp.controllers;


import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");

        String errorMsg = "";

        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "The requested resource does not exist.";
                break;
            }
            case 403: {
                errorMsg = "You do not have permission to access the resource.";
                break;
            }
            case 401: {
                errorMsg = "Authorization is required.";
                break;
            }
            case 404: {
                errorMsg = "The requested resource was not found.";
                break;
            }
            case 500: {
                errorMsg = "An internal server error occurred.";
                break;
            }
        }

        errorPage.addObject("code", httpErrorCode);
        errorPage.addObject("message", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

  
    public String getErrorPath() {
        return "/error.html";
    }
}