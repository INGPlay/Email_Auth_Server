package server.api.emailAuth.process.apiController;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import server.api.emailAuth.common.util.ResponseUtil;
import server.api.emailAuth.exceptionHandler.exception.CacheTimeoutException;
import server.api.emailAuth.exceptionHandler.exception.EmailDuplicateException;
import server.api.emailAuth.exceptionHandler.exception.FailedValidationException;
import server.api.emailAuth.process.domain.dto.*;
import server.api.emailAuth.process.service.CacheService;
import server.api.emailAuth.process.service.CheckService;
import server.api.emailAuth.process.service.EmailService;
import server.api.emailAuth.process.service.util.CodeGenerator;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/api/auth/email")
@RequiredArgsConstructor
public class ProcessApiController {

    private final ResponseUtil responseUtil;
    private final CodeGenerator codeGenerator;
    private final EmailService emailService;
    private final CacheService cacheService;
    private final CheckService checkService;

    @PostMapping("/request")
    public String requestAuthentication(@ModelAttribute @Valid RequestAuthDTO requestAuthDTO,
                                        BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()){
            throw new FailedValidationException();
        }

        UUID uuid = UUID.randomUUID();
        EditAuthProcessDTO editAuthProcessDTO = new EditAuthProcessDTO();
        editAuthProcessDTO.setUuid(uuid.toString());
        editAuthProcessDTO.setRequestAuthDTO(requestAuthDTO);
        cacheService.addRequestProcess(editAuthProcessDTO);

        return "redirect:/api/auth/email/input/" + uuid.toString();
    }

    @GetMapping("/input/{uuid}")
    public String inputEmailPages(@PathVariable String uuid,
                                  Model model){
        String count = cacheService.getCounting(uuid);

        model.addAttribute("count", count);
        model.addAttribute("uuid", uuid);
        return "auth/email/input";
    }

    @GetMapping("/send")
    public String sendCode(@ModelAttribute SendAuthDTO sendAuthDTO) throws NoSuchAlgorithmException, JsonProcessingException {

        if (!cacheService.isExistKey(sendAuthDTO.getUuid())){
            throw new CacheTimeoutException();
        }

        RequestAuthDTO request = cacheService.getRequest(sendAuthDTO.getUuid());

        if (request.getDuplicateCheckerUrl() != null && !request.getDuplicateCheckerUrl().trim().isEmpty()) {
            if (checkService.isFindEmail(request.getDuplicateCheckerUrl(), sendAuthDTO.getEmail())){
                throw new EmailDuplicateException();
            }
        }

        String authCode = codeGenerator.getRandomNumberCode(6);
        log.info("authCode : {}", authCode);
        emailService.sendEmail(sendAuthDTO.getEmail(),
                "인증코드입니다.",
                "인증번호 : " + authCode
        );

        CheckAuthDTO checkAuthDTO = new CheckAuthDTO();
        checkAuthDTO.setEmail(sendAuthDTO.getEmail());
        checkAuthDTO.setAuthCode(authCode);
        cacheService.addAuthorizeProcess(sendAuthDTO.getUuid(), checkAuthDTO);

        return "redirect:/api/auth/email/check/" + sendAuthDTO.getUuid();
    }

    @GetMapping("/check/{uuid}")
    public String checkCode(@PathVariable String uuid,
                            Model model){

        String countDown = ZonedDateTime.now()
                .plusSeconds(cacheService.getTtl(uuid))
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        model.addAttribute("uuid", uuid);
        model.addAttribute("countDown", countDown);

        return "auth/email/check";
    }

    @PostMapping("/compare")
    public String compareCode(@ModelAttribute CompareAuthDTO compareAuthDTO,
                              RedirectAttributes redirectAttributes) throws JsonProcessingException {

        CheckAuthDTO authorize = cacheService.getAuthorize(compareAuthDTO.getUuid());
        RequestAuthDTO request = cacheService.getRequest(compareAuthDTO.getUuid());
        cacheService.deleteKey(compareAuthDTO.getUuid());

        boolean result = isEqaulCode(authorize.getAuthCode(), compareAuthDTO.getInputCode());
        if (result){
            log.info("[succcess]");
            cacheService.deleteKey(compareAuthDTO.getUuid());
            redirectAttributes.addAttribute("result", true);
            redirectAttributes.addAttribute("authorizeEmail", authorize.getEmail());
            return "redirect:" + request.getSuccessRedirectUrl();
        } else {
            log.info("[fail]");
            cacheService.countRepeat(compareAuthDTO.getUuid());
            return "redirect:/api/auth/email/input/" + compareAuthDTO.getUuid();
        }
    }

    private static boolean isEqaulCode(String authCode, String inputCode) {
        return authCode.equals(inputCode);
    }
}
