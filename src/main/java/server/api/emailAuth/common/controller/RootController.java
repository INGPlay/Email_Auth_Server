package server.api.emailAuth.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import server.api.emailAuth.common.domain.ErrorPageDTO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class RootController {

    @GetMapping
    public String rootPage(){
        return "index";
    }

    @GetMapping("/error")
    public String errorPage(@ModelAttribute ErrorPageDTO errorPageDTO){
        return "error";
    }
}
