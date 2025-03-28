package site.easy.to.build.crm.extension.io.dto;

import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.*;

import java.util.List;

@Getter
@Setter
public class CustomerJson extends CustomerLoginInfo {
  List<Ticket> tickets;
  List<Lead> leads;
  List<Budget> budgets;

}
