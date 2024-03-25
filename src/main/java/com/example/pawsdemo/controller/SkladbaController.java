package com.example.pawsdemo.controller;

import org.springframework.stereotype.Controller;

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
