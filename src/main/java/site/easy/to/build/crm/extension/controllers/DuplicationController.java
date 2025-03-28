package site.easy.to.build.crm.extension.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.extension.io.dto.CustomerJson;
import site.easy.to.build.crm.extension.service.quickActions.DuplicationService;

import java.io.File;

@RestController
@RequestMapping("/api/duplicate")
@AllArgsConstructor
public class DuplicationController {
  private final DuplicationService duplicationService;

  @GetMapping("/{id}")
  public ResponseEntity<CustomerJson> create(@PathVariable int id) throws JsonProcessingException {

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "file.json" + "\"")
      .body(duplicationService.createDuplicate(id));
  }

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody CustomerJson customerJson) {
    return ResponseEntity.ok().body(customerJson);
  }

}
