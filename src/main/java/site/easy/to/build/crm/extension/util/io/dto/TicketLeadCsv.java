package site.easy.to.build.crm.extension.util.io.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "customer_email", "subject_or_name", "type", "status", "expense" })
public class TicketLeadCsv implements Serializable {

  @Email
  @NotBlank
  @JsonProperty("customer_email")
  String customerEmail;

  @NotBlank
  @JsonProperty("subject_or_name")
  String subjectOrName;

  @NotBlank
  @Pattern(regexp = "^(ticket|lead)$", message = "Type is either ticket or lead")
  @JsonProperty("type")
  String type;

  @NotBlank
  @Pattern(regexp = "^(open|assigned|on-hold|in-progress|resolved|closed|reopened|pending-customer-response|escalated|archived|meeting-to-schedule|scheduled|success|assign-to-sales)$", message = "Invalid status")
  @JsonProperty("status")
  String status;

  @Positive
  @JsonProperty("expense")
  Double expense;

  @AssertTrue(message = "invalid for type")
  public boolean isStatusValid() {
    if (type.equalsIgnoreCase("ticket")) {
      return type.matches("^(open|assigned|on-hold|in-progress|resolved|closed|reopened|pending-customer-response" + "|escalated|archived)$");
    } else {
      return type.matches("^(meeting-to-schedule|scheduled|archived|success|assign-to-sales)$");
    }
  }

}