package com.kjm.resource.sampleresourceserver.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjm.resource.sampleresourceserver.common.BaseResponse;
import com.kjm.resource.sampleresourceserver.common.enums.ResultCodeEnum;
import com.kjm.resource.sampleresourceserver.common.enums.StatusEnum;
import com.kjm.resource.sampleresourceserver.member.model.UserDetailVo;
import com.kjm.resource.sampleresourceserver.member.model.UserVo;
import com.kjm.resource.sampleresourceserver.member.model.dto.MemberRequestDto;
import com.kjm.resource.sampleresourceserver.member.service.UserService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameters;
// import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final UserService userSerivce;

    // 회원 등록
    // @Operation(summary = "메모등록", description = "메모등록")
    // @Parameters({
    // @Parameter(name = "memoTtl", description = "메모제목"),
    // @Parameter(name = "memoCtt", description = "메모내용"),
    // })
    @PostMapping(value = "/v1.0", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<?>> addMember(@RequestBody MemberRequestDto requestDto) {
        log.info("param is ... {}", requestDto.toString());
        // 회원 등록
        UserVo userVo = userSerivce.adduserInfo(requestDto);
        UserDetailVo detailVo = userSerivce.addUserDetail(requestDto);

        if (userVo != null && detailVo != null)
            return new ResponseEntity<>(BaseResponse.actionCreateSuccess(), HttpStatus.CREATED);
        else {
            BaseResponse<Boolean> responseResult = BaseResponse.<Boolean>builder()
                    .status(StatusEnum.FAIL)
                    .data(false)
                    .resultCode(ResultCodeEnum.SERVICE_UNAVAILABLE)
                    .resultMsg("회원 등록에 실패했습니다. 잠시 후에 다시 시도해주세요.")
                    .build();
            return new ResponseEntity<>(responseResult, HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

}
