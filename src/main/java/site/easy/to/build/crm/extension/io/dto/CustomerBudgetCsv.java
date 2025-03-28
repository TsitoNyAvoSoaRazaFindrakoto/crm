package site.easy.to.build.crm.extension.io.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"customer_email", "Budget"})
public class CustomerBudgetCsv implements Serializable {

  @Email
  @NotBlank
  @JsonProperty("customer_email")
  String customerEmail;

  @Positive
  @JsonProperty("Budget")
  Double budget;
}
