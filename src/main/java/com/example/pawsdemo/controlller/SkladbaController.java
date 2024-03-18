package com.example.pawsdemo.controlller;

import com.example.pawsdemo.models.Skladba;
import com.example.pawsdemo.models.Uzivatel;
import com.example.pawsdemo.services.SkladbaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SkladbaController {

    /* private SkladbaService service;
    @GetMapping("/new")
    public String add(Model model){
        List<Skladba> songList = service.listAll();
        model.addAttribute("songList", songList);
        model.addAttribute("skladba",new Skladba());
        return "new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveSkladba(@ModelAttribute("skladba") Skladba skladba)  {
        service.save(skladba);
        return "redirect:/";
    } */
}
