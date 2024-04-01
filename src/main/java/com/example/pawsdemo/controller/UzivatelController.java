package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.AdresaDtoIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.exceptions.UserAlreadyExistsException;
import com.example.pawsdemo.models.AdresaEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.UzivatelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UzivatelController {

    @Autowired
    private MessageSource messages;
    @Autowired
    private UzivatelService uzivatelService;
    @Qualifier("uzivatelService")
    @Autowired
    private UserDetailsService userDetService;

    @Autowired
    private UzivatelRepository uzivatelRepository;

    private static final Logger logger = LoggerFactory.getLogger(UzivatelController.class);


    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        UserDetails userDet = userDetService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDet);
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/registration")
    public String registration(WebRequest requestUser, Model model) {
        UzivatelDtoIn uzivatel = new UzivatelDtoIn();
        AdresaDtoIn adresa = new AdresaDtoIn();
        model.addAttribute("uzivatel", uzivatel);
        model.addAttribute("adresa", adresa);
        return "registration";
    }
    @PostMapping("/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("uzivatel") @Valid UzivatelDtoIn userDto, @ModelAttribute("adresa") @Valid AdresaDtoIn adresaDto,
                                            HttpServletRequest request, Errors errors) {
        try{
            final AdresaEntity newAddress = uzivatelService.registerUsersAddress(adresaDto);
            final UzivatelEntity registered = uzivatelService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistsException uae) {
            ModelAndView mav = new ModelAndView("registration", "uzivatel", userDto);
            String errMessage = messages.getMessage("message.regError", null, request.getLocale());
            mav.addObject("message", errMessage);
            return mav;
        }
        return new ModelAndView("userProfile", "uzivatel", userDto);
    }
}
