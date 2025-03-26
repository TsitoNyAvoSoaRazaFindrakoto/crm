package site.easy.to.build.crm.extension.io.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "customer_email", "customer_name" })
public class CustomerCsv implements Serializable {

  @Email
  @NotBlank
  @JsonProperty("customer_email")
  String customerEmail;

  @NotBlank
  @JsonProperty("customer_name")
  String customerName;

}
