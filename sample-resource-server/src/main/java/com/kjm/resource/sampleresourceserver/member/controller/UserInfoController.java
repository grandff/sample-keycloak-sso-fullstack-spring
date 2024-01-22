package com.kjm.resource.sampleresourceserver.member.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjm.resource.sampleresourceserver.common.BaseResponse;
import com.kjm.resource.sampleresourceserver.common.enums.ResultCodeEnum;
import com.kjm.resource.sampleresourceserver.common.enums.StatusEnum;
import com.kjm.resource.sampleresourceserver.member.model.UserDetailVo;
import com.kjm.resource.sampleresourceserver.member.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userSerivce;

    /**
     * client의 thymeleaf에서 사용
     * FIXME 솔직히 이해 못함.. 대체 어떻게 호출하는지??
     * 
     * @param principal
     * @return
     */
    @GetMapping("/info")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt principal) {
        System.out.println("yes user info call ~~~~ " + principal.getClaims().toString());
        return Collections.singletonMap("user_name", principal.getClaimAsString("preferred_username"));
    }

    @GetMapping("/detail")
    public ResponseEntity<BaseResponse<?>> getUserDetail(@AuthenticationPrincipal Jwt principal) {
        // 토큰이 저기에 자동으로 담겨져 있음...
        System.out.println("yes user info call ~~~~ " + principal.getClaims().toString());

        // preferred_username -> user_id와 동일함 (pk) 아마도???
        // 해당 pk로 상세 정보를 불러와서 리턴
        String username = principal.getClaimAsString("preferred_username");
        UserDetailVo vo = userSerivce.getUserDetailInfo(username).orElse(null);

        if (vo == null) {
            return ResponseEntity.notFound().build();
        } else {
            BaseResponse<UserDetailVo> responseResult = BaseResponse.<UserDetailVo>builder()
                    .status(StatusEnum.SUCCESS)
                    .data(vo)
                    .resultCode(ResultCodeEnum.SUCCESS)
                    .resultMsg("조회됐습니다.")
                    .build();

            return ResponseEntity.ok(responseResult);
        }
    }
}