package site.easy.to.build.crm.extension.io.dto;

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

	@JsonProperty(value = "status")
	@NotBlank(message = "Status is required")
	@Pattern(regexp = "^(open|assigned|on-hold|in-progress|resolved|closed|reopened|pending-customer-response|escalated|archived|meeting-to-schedule|scheduled|success|assign-to-sales)$", message = "Invalid status for ticket or leads")
	public String status;

	@JsonProperty(value = "expense")
	@Positive(message = "Expense must be a positive number")
	public Double expense;

	@AssertTrue(message = "Status is invalid for the provided type")
	public boolean isStatusValid() {
		if ("ticket".equalsIgnoreCase(type) || "tickets".equalsIgnoreCase(type)) {
			return status.matches("^(open|assigned|on-hold|in-progress|resolved|closed|reopened|pending-customer-response|escalated|archived)$");
		} else if ("lead".equalsIgnoreCase(type) || "leads".equalsIgnoreCase(type)) {
			status.matches("^(meeting-to-schedule|scheduled|archived|success|assign-to-sales)$");
		}
		System.out.println("TicketLeadCsv.isStatusValid()..." + status + " : " + type);
		return false;
	}

}