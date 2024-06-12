package com.example.flowglobalassignment.ip.presentation;

import com.example.flowglobalassignment.global.common.CommonResDto;
import com.example.flowglobalassignment.ip.application.IpRuleService;
import com.example.flowglobalassignment.ip.application.dto.IpRuleSearchCondition;
import com.example.flowglobalassignment.ip.application.dto.request.IpRuleRequestDto;
import com.example.flowglobalassignment.ip.application.dto.response.CreateIpRoleResponseDto;
import com.example.flowglobalassignment.ip.application.dto.response.IpRuleListResponseDto;
import com.example.flowglobalassignment.ip.util.IpUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ip")
@RequiredArgsConstructor
public class IpRuleController {

    private final IpRuleService ipRuleService;
    @PostMapping("")
    public ResponseEntity<CommonResDto<?>> createIpRole(@RequestBody @Valid IpRuleRequestDto ipRuleRequestDto){
        CreateIpRoleResponseDto result = ipRuleService.createIpRole(ipRuleRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResDto<>(1,"IP규칙 생성에 성공하였습니다.",result));
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResDto<?>> deleteIpRole(@RequestParam("ipRoleId") Long ipRoleId){
        boolean result = ipRuleService.deleteIpRole(ipRoleId);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResDto<>(1,"IP규칙 삭제에 성공하였습니다.",result));
    }
    @GetMapping("")
    public ResponseEntity<CommonResDto<?>> readIpRole(IpRuleSearchCondition ipRuleSearchCondition,
                                                      Pageable pageable){
        Page<IpRuleListResponseDto> result = ipRuleService.readIpRole(ipRuleSearchCondition, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResDto<>(1,"IP규칙 리스트조회에 성공하였습니다.",result));
    }
    @GetMapping("/my-ip")
    public ResponseEntity<CommonResDto<?>> readClientIp(){
        String clientIp = IpUtils.getClientIp();
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResDto<>(1,"사용자의 IP조회에 성공하였습니다.",clientIp));
    }
    @GetMapping("/ip-roles/count")
    public ResponseEntity<CommonResDto<?>> getIpRoleCount(@RequestParam("ipAddress") String ipAddress){
        Integer result = ipRuleService.getIpRoleCount(ipAddress);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResDto<>(1,"사용자의 IP규칙개수 조회에 성공하였습니다.",result));
    }

}
