package com.laattre.backen.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.laattre.backen.captcha.ICaptchaService;
import com.laattre.backen.persistence.model.User;
import com.laattre.backen.registration.OnRegistrationCompleteEvent;
import com.laattre.backen.service.UserService;
import com.laattre.backen.web.dto.UserDto;
import com.laattre.backen.web.util.GenericResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class RegistrationCaptchaController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private ICaptchaService captchaService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public RegistrationCaptchaController() {
        super();
    }

    // Registration

    @RequestMapping(value = "/user/registrationCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse captchaRegisterUserAccount(@Valid final User accountDto, final HttpServletRequest request) {

        final String response = request.getParameter("g-recaptcha-response");
        captchaService.processResponse(response);

        LOGGER.debug("Registering user account with information: {}", accountDto);

        final User registered = userService.registerNewUserAccount(accountDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
        return new GenericResponse("success");
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
