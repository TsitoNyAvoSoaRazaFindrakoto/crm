package site.easy.to.build.crm.extension.util.io.dto;

import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.groups.Default;
import lombok.*;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.extension.Exception.LocalValidationException;
import site.easy.to.build.crm.extension.util.io.CsvBase;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DTO for {@link site.easy.to.build.crm.entity.Customer}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCsv extends CsvBase<Customer> implements Serializable {
  @NotEmpty
  @NotBlank(message = "Name is required", groups = {Default.class,
    Customer.CustomerUpdateValidationGroupInclusion.class})
  String name;
  @Email(message = "Please enter a valid email format")
  @NotBlank(message = "Email is required")
  String email;
  String phone;
  String address;
  String city;
  String state;
  @NotBlank(message = "Country is required", groups = {Default.class,
    Customer.CustomerUpdateValidationGroupInclusion.class})
  String country;

	@Override
	@JsonIgnore
  public Class<?> getMotherEntity() {
    return Customer.class;
  }

  @Override
  public Customer extractMotherEntity(Validator validator, Map<String, ?> additionalParams) throws LocalValidationException {
    Customer customer = new Customer();
    customer.setName(this.getName());
    customer.setEmail(this.getEmail());
    customer.setPhone(this.getPhone());
    customer.setAddress(this.getAddress());
    customer.setCity(this.getCity());
    customer.setState(this.getState());
    customer.setCountry(this.getCountry());
    if (!validator.validate(customer).isEmpty()) throw new LocalValidationException();
    return customer;
  }

  @Override
  public Customer extractEntity(Class<?> targetClass, Validator validator, Map<String, ?> additionalParams) {
    return null;
  }
}