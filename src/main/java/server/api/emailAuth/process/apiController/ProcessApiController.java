package server.api.emailAuth.process.apiController;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import server.api.emailAuth.common.util.ResponseUtil;
import server.api.emailAuth.common.util.response.ResponseForm;
import server.api.emailAuth.process.domain.dto.EditAuthProcessDTO;
import server.api.emailAuth.process.domain.dto.RequestAuthDTO;
import server.api.emailAuth.process.domain.dto.SendAuthDTO;
import server.api.emailAuth.process.service.CacheService;
import server.api.emailAuth.process.service.EmailService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/api/auth/email")
@RequiredArgsConstructor
public class ProcessApiController {

    private final ResponseUtil responseUtil;
    private final EmailService emailService;
    private final CacheService cacheService;

    @PostMapping("/request")
    public String requestAuthentication(@ModelAttribute @Valid RequestAuthDTO requestAuthDTO,
                                        BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()){
            return "asdf";
        }

        UUID uuid = UUID.randomUUID();
        EditAuthProcessDTO editAuthProcessDTO = new EditAuthProcessDTO();
        editAuthProcessDTO.setUuid(uuid.toString());
        editAuthProcessDTO.setRequestAuthDTO(requestAuthDTO);
        cacheService.addRequestProcess(editAuthProcessDTO);

        return "redirect:/api/auth/email/input/" + uuid.toString();
    }

    @GetMapping("/input/{uuid}")
    public String authCode(@PathVariable String uuid,
                           Model model){
        model.addAttribute("uuid", uuid);
        return "auth/email/input";
    }

    @PostMapping("/send")
    public String sendCode(@ModelAttribute SendAuthDTO sendAuthDTO){

        return "";
    }
}
