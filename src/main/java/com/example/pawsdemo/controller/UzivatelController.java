package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.AdresaDtoIn;
import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.dotIn.UmelecDtoIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.exceptions.UserAlreadyExistsException;
import com.example.pawsdemo.models.AdresaEntity;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.UmelecEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.BUService;
import com.example.pawsdemo.services.UmelecService;
import com.example.pawsdemo.services.UzivatelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
public class UzivatelController {

    @Autowired
    private MessageSource messages;
    private UzivatelService uzivatelService;
    private UserDetailsService userDetService;
    private UzivatelRepository uzivatelRepository;

    private static final Logger logger = LoggerFactory.getLogger(UzivatelController.class);

    @Autowired
    public UzivatelController(UzivatelService uzivatelService, @Qualifier("uzivatelService") UserDetailsService userDetService, UzivatelRepository uzivatelRepository) {
        this.uzivatelService = uzivatelService;
        this.userDetService = userDetService;
        this.uzivatelRepository = uzivatelRepository;
    }

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
        BeznyUzivatelDotIn bu = new BeznyUzivatelDotIn();
        model.addAttribute("uzivatel", uzivatel);
        model.addAttribute("adresa", adresa);
        model.addAttribute("beznyuzivatel", bu);
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("uzivatel") @Valid UzivatelDtoIn userDto,
                                            @ModelAttribute("adresa") @Valid AdresaDtoIn adresaDto,
                                            @ModelAttribute("beznyuzivatel") BeznyUzivatelDotIn buDto,
                                            @ModelAttribute("umelec") UmelecDtoIn umelecDto,
                                            @RequestParam Map<String, String> formDate,
                                            HttpServletRequest request, Errors errors) {
        try{
            String typUctu = formDate.get("typUctu");

            final AdresaEntity newAddress = uzivatelService.registerUsersAddress(adresaDto);
            if("isBU".equals(typUctu)) {
                final BeznyuzivatelEntity registeredBU = uzivatelService.registerNewUserAccountAsBU(buDto);
            }
            if("isUmelec".equals(typUctu)){
                final UmelecEntity registeredUmelec = uzivatelService.registerNewUserAccountAsUmelec(umelecDto);
            }
            final UzivatelEntity registered = uzivatelService.registerNewUserAccount(userDto, typUctu);
        } catch (UserAlreadyExistsException uae) {
            ModelAndView mav = new ModelAndView("registration", "uzivatel", userDto);
            String errMessage = messages.getMessage("message.regError", null, request.getLocale());
            mav.addObject("message", errMessage);
            return mav;
        }
        return new ModelAndView("userProfile", "uzivatel", userDto);
    }
}
