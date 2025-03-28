package site.easy.to.build.crm.extension.service.quickActions;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.extension.exception.PostPersistException;
import site.easy.to.build.crm.extension.io.dto.CustomerBudgetCsv;
import site.easy.to.build.crm.extension.io.dto.CustomerCsv;
import site.easy.to.build.crm.extension.io.dto.TicketLeadCsv;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.EmailTokenUtils;

import java.sql.SQLDataException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataGenerationService {
	private final User adminUser;
	private final Faker dataGenerator;
	private final CustomerLoginInfoService customerLoginInfoService;
	private final CustomerService customerService;
	private final PasswordEncoder passwordEncoder;
	@PersistenceContext
	private final EntityManager entityManager;
	@Getter
	private final List<String> dataCompletionErrors = new ArrayList<>();

	Map<String, CustomerLoginInfo> customerLoginInfos = new HashMap<>();
	List<Ticket> tickets = new ArrayList<>();
	List<Lead> leads = new ArrayList<>();
	List<Budget> budgets = new ArrayList<>();

	String[] leadStatus = { "meeting-to-schedule", "scheduled", "archived", "success", "assign-to-sales" };
	String[] ticketStatus = { "open", "assigned", "on-hold", "in-progress", "resolved", "closed", "reopened", "pending" +
			"-customer-response", "escalated", "archived" };
	String[] ticketPriority = { "low", "medium", "high", "closed", "urgent", "critical" };

	public DataGenerationService(UserService userService, CustomerLoginInfoService customerLoginInfoService,
			PasswordEncoder passwordEncoder, EntityManager entityManager,
			CustomerService customerService) {
		this.customerLoginInfoService = customerLoginInfoService;
		this.customerService = customerService;
		this.entityManager = entityManager;
		dataGenerator = new Faker();
		adminUser = userService.findFirst();
		this.passwordEncoder = passwordEncoder;
	}

	public void generateCustomerLoginInfosFrom(List<CustomerCsv> customerCsvList) {
		customerLoginInfos = new HashMap<>();
		int i = 0;
		for (CustomerCsv customerCsv : customerCsvList) {
			if (customerLoginInfos.containsKey(customerCsv.getCustomerEmail())
					|| customerService.findByEmail(customerCsv.getCustomerEmail()) != null) {
				dataCompletionErrors.add("Customer with email " + customerCsv.getCustomerEmail() + " at row " + (i + 2) +
						" already exists");
			} else {
				CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
				customerLoginInfo.setEmail(customerCsv.getCustomerEmail());
				customerLoginInfo.setPassword(passwordEncoder.encode(customerCsv.getCustomerEmail()));
				customerLoginInfo.setPasswordSet(true);

				Customer customer = new Customer();
				customer.setUser(adminUser);
				customer.setEmail(customerCsv.getCustomerEmail());
				customer.setName(customerCsv.getCustomerName());
				customer.setPosition(dataGenerator.job().title());
				customer.setCreatedAt(LocalDateTime.now());

				Address address = dataGenerator.address();
				customer.setAddress(address.fullAddress());
				customer.setCountry(address.country());

				customer.setCity(address.city());
				customer.setState(address.state());

				customerLoginInfo.setCustomer(customer);

				customerLoginInfos.put(customerCsv.getCustomerEmail(), customerLoginInfo);
			}
			i++;
		}
	}

	public void generateBudgets(List<CustomerBudgetCsv> customerBudgetCsvs) {
		int i = 0;
		for (CustomerBudgetCsv customerBudgetCsv : customerBudgetCsvs) {
			Budget budget = new Budget();
			if (customerLoginInfos.containsKey(customerBudgetCsv.getCustomerEmail())) {
				budget.setCustomer(customerLoginInfos.get(customerBudgetCsv.getCustomerEmail()).getCustomer());
			} else {
				dataCompletionErrors.add(
						"Customer with email " + customerBudgetCsv.getCustomerEmail() + "not found for budget at row " + (i + 2));
			}
			budget.setTitle("Added budget" + dataGenerator.business().creditCardType());
			budget.setAmount(customerBudgetCsv.getBudget());
			budget.setStartDate(LocalDate.now());
			budget.setEndDate(LocalDate.now().plusWeeks(dataGenerator.random().nextInt(4, 52)));
			budgets.add(budget);
			i++;
		}
	}

	public void generateTicketsAndLeads(List<TicketLeadCsv> ticketLeadCsvList) {
		int i = 0;
		for (TicketLeadCsv ticketLeadCsv : ticketLeadCsvList) {
			if (ticketLeadCsv.getType().equalsIgnoreCase("lead")) {
				Lead lead = new Lead();

				if (customerLoginInfos.containsKey(ticketLeadCsv.getCustomerEmail())) {
					lead.setCustomer(customerLoginInfos.get(ticketLeadCsv.getCustomerEmail()).getCustomer());
				} else {
					this.dataCompletionErrors
							.add("Customer with email " + ticketLeadCsv.getCustomerEmail() + "not found for lead at row " + (i + 2));
				}
				lead.setEmployee(adminUser);
				lead.setManager(adminUser);
				lead.setCreatedAt(LocalDateTime.now().plusDays(dataGenerator.random().nextInt(1, 5)));
				lead.setStatus(ticketLeadCsv.getStatus());
				lead.setName(ticketLeadCsv.getSubjectOrName());

				Expense expense = new Expense();
				expense.setAmount(ticketLeadCsv.getExpense());
				expense.setDateExpense(lead.getCreatedAt().toLocalDate().plusDays(dataGenerator.random().nextInt(1, 3)));
				expense.setDescription(lead.getName() + " expense ");

				lead.setExpense(expense);

				leads.add(lead);
			} else {
				Ticket ticket = new Ticket();

				if (customerLoginInfos.containsKey(ticketLeadCsv.getCustomerEmail())) {
					ticket.setCustomer(customerLoginInfos.get(ticketLeadCsv.getCustomerEmail()).getCustomer());
				} else {
					this.dataCompletionErrors.add(
							"Customer with email " + ticketLeadCsv.getCustomerEmail() + "not found for ticket at row " + (i + 2));
				}
				ticket.setEmployee(adminUser);
				ticket.setManager(adminUser);
				ticket.setCreatedAt(LocalDateTime.now().plusDays(dataGenerator.random().nextInt(1, 5)));
				ticket.setStatus(ticketLeadCsv.getStatus());
				ticket.setSubject(ticketLeadCsv.getSubjectOrName());
				ticket.setDescription(dataGenerator.elderScrolls().quote() + " ticket!");
				ticket.setPriority(ticketPriority[ThreadLocalRandom.current().nextInt(0, ticketPriority.length)]);

				Expense expense = new Expense();
				expense.setAmount(ticketLeadCsv.getExpense());
				expense.setDateExpense(ticket.getCreatedAt().toLocalDate().plusDays(dataGenerator.random().nextInt(1, 3)));
				expense.setDescription(ticket.getSubject() + " expense ");

				ticket.setExpense(expense);

				tickets.add(ticket);
			}
			i++;
		}
	}

	@Transactional(rollbackFor = SQLDataException.class)
	public void saveCustomers() throws SQLDataException {
		for (CustomerLoginInfo customerLoginInfo : customerLoginInfos.values()) {
			Customer customer = customerLoginInfo.getCustomer();
			CustomerLoginInfo customerLoginInfo1 = customerLoginInfoService.save(customerLoginInfo);
			customer.setCustomerLoginInfo(customerLoginInfo1);
			Customer createdCustomer = customerService.save(customer);
			customerLoginInfo1.setCustomer(createdCustomer);
			customerLoginInfos.put(customerLoginInfo1.getEmail(), customerLoginInfo1);
		}
	}

	@Transactional(rollbackFor = SQLDataException.class)
	public void saveBudgets() throws SQLDataException {
		for (Budget budget : budgets) {// Persist the customer first
			entityManager.persist(budget);
		}
	}

	@Transactional(rollbackFor = SQLDataException.class)
	public void saveTicketsAndLeads() throws SQLDataException {
		for (Lead lead : leads) {
			entityManager.persist(lead.getExpense()); // Persist the expense first
			entityManager.persist(lead);
		}

		for (Ticket ticket : tickets) {
			entityManager.persist(ticket.getExpense()); // Persist the expense first
			entityManager.persist(ticket);
		}
	}

	@Transactional(rollbackFor = { SQLDataException.class, PostPersistException.class })
	public void importCsvAndSave(List<CustomerCsv> customerCsvList, List<TicketLeadCsv> ticketLeadCsvList,
			List<CustomerBudgetCsv> customerBudgetCsvList) throws SQLDataException, PostPersistException {
		generateCustomerLoginInfosFrom(customerCsvList);
		saveCustomers();

		generateBudgets(customerBudgetCsvList);
		saveBudgets();

		generateTicketsAndLeads(ticketLeadCsvList);
		saveTicketsAndLeads();

		if (!dataCompletionErrors.isEmpty()) {
			// ?Info : clearGeneratedData;
			throw new PostPersistException();
		}

	}

	public void clearGeneratedData() {
		budgets.clear();
		leads.clear();
		tickets.clear();
		customerLoginInfos.clear();
	}

	/**
	 * Génère des clients aléatoires avec Faker
	 *
	 * @param count Nombre de clients à générer
	 * @return Liste des CustomerLoginInfo générés
	 */
	public List<CustomerLoginInfo> generateRandomCustomers(int count) {
		List<CustomerLoginInfo> customersInfos = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			String username = dataGenerator.name().username();
			String email = username.toLowerCase().replace(" ", "") + "@gmail.com";

			Customer customer = new Customer();
			customer.setName(username);
			customer.setPosition(dataGenerator.company().profession());
			customer.setEmail(email);
			customer.setUser(adminUser);
			customer.setCountry(dataGenerator.address().country());
			customer.setCity(dataGenerator.address().city());

			CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
			customerLoginInfo.setEmail(email);
			customerLoginInfo.setPasswordSet(true);
			String hashPassword = passwordEncoder.encode("2004");
			customerLoginInfo.setPassword(hashPassword);
			String token = EmailTokenUtils.generateToken();
			customerLoginInfo.setToken(token);
			customerLoginInfo.setCustomer(customer);

			customersInfos.add(customerLoginInfo);
		}
		return customersInfos;
	}

	/**
	 * Génère des budgets aléatoires pour les clients existants
	 *
	 * @param customers Liste des clients
	 * @param count     Nombre de budgets à générer par client
	 * @return Liste des budgets générés
	 */
	public List<Budget> generateRandomBudgets(List<Customer> customers, int count) {
		List<Budget> budgets = new ArrayList<>();

		for (Customer customer : customers) {
			for (int i = 0; i < count; i++) {
				Budget budget = new Budget();

				budget.setTitle(dataGenerator.commerce().department() + " Budget");
				// Montant aléatoire entre 1000 et 50000
				double amount = 1000 + (Math.random() * 49000);
				budget.setAmount((double) Math.round(amount * 100) / 100);

				LocalDate now = LocalDate.now();
				LocalDate startDate = now.minusDays(ThreadLocalRandom.current().nextInt(0, 60));
				LocalDate endDate = startDate.plusMonths(ThreadLocalRandom.current().nextInt(1, 13));

				budget.setStartDate(startDate);
				budget.setEndDate(endDate);
				budget.setCustomer(customer);

				budgets.add(budget);
			}
		}
		return budgets;
	}

	/**
	 * Génère des tickets aléatoires pour les clients existants
	 *
	 * @param customers Liste des clients
	 * @param count     Nombre de tickets à générer par client
	 * @return Liste des tickets générés
	 */
	public List<Ticket> generateRandomTickets(List<Customer> customers, int count) {
		List<Ticket> tickets = new ArrayList<>();

		for (Customer customer : customers) {
			for (int i = 0; i < count; i++) {
				Ticket ticket = new Ticket();

				Expense expense = new Expense();
				// Montant aléatoire entre 50 et 2000
				double expenseAmount = 50 + (Math.random() * 1950);
				expense.setAmount((double) Math.round(expenseAmount * 100) / 100);
				expense.setDateExpense(LocalDate.now().minusDays(ThreadLocalRandom.current().nextInt(0, 30)));
				expense.setDescription(dataGenerator.lorem().sentence());

				ticket.setSubject(dataGenerator.lorem().sentence(3, 3));
				ticket.setDescription(dataGenerator.lorem().paragraph());
				ticket.setStatus(ticketStatus[ThreadLocalRandom.current().nextInt(0, ticketStatus.length)]);
				ticket.setPriority(ticketPriority[ThreadLocalRandom.current().nextInt(0, ticketPriority.length)]);
				ticket.setCustomer(customer);
				ticket.setManager(adminUser);
				ticket.setEmployee(adminUser);
				ticket.setCreatedAt(LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(0, 30)));
				ticket.setExpense(expense);

				tickets.add(ticket);
			}
		}
		return tickets;
	}

	/**
	 * Génère des leads aléatoires pour les clients existants
	 *
	 * @param customers Liste des clients
	 * @param count     Nombre de leads à générer par client
	 * @return Liste des leads générés
	 */
	public List<Lead> generateRandomLeads(List<Customer> customers, int count) {
		List<Lead> leads = new ArrayList<>();
		String[] statuses = { "meeting-to-schedule", "scheduled", "archived", "success", "assign-to-sales" };

		for (Customer customer : customers) {
			for (int i = 0; i < count; i++) {
				Lead lead = new Lead();

				Expense expense = new Expense();
				// Montant aléatoire entre 50 et 1000
				double expenseAmount = 50 + (Math.random() * 950);
				expense.setAmount((double) Math.round(expenseAmount * 100) / 100);
				expense.setDateExpense(LocalDate.now().minusDays(ThreadLocalRandom.current().nextInt(0, 30)));
				expense.setDescription(dataGenerator.lorem().sentence());

				lead.setCustomer(customer);
				lead.setManager(adminUser);
				lead.setName(dataGenerator.company().name() + " Opportunity");
				lead.setEmployee(adminUser);
				lead.setStatus(statuses[ThreadLocalRandom.current().nextInt(0, statuses.length)]);
				lead.setCreatedAt(LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(0, 60)));
				lead.setExpense(expense);

				leads.add(lead);
			}
		}
		return leads;
	}

	/**
	 * Génère et sauvegarde des données aléatoires pour le CRM
	 *
	 * @param customerCount     Nombre de clients à générer
	 * @param budgetPerCustomer Nombre de budgets par client
	 * @param ticketPerCustomer Nombre de tickets par client
	 * @param leadPerCustomer   Nombre de leads par client
	 * @return Map contenant les statistiques des données générées
	 * @throws SQLDataException si une erreur survient pendant la sauvegarde
	 */
	@Transactional(rollbackFor = SQLDataException.class)
	public Map<String, Integer> generateAndSaveRandomData(int customerCount, int budgetPerCustomer,
			int ticketPerCustomer, int leadPerCustomer) throws SQLDataException {
		try {
			// 1. Générer et sauvegarder les clients
			List<CustomerLoginInfo> customerLoginInfos = generateRandomCustomers(customerCount);
			List<Customer> customers = new ArrayList<>();

			for (CustomerLoginInfo customerLoginInfo : customerLoginInfos) {
				entityManager.persist(customerLoginInfo.getCustomer());
				entityManager.persist(customerLoginInfo);
				customers.add(customerLoginInfo.getCustomer());
			}

			// 2. Générer et sauvegarder les budgets
			List<Budget> budgets = generateRandomBudgets(customers, budgetPerCustomer);
			for (Budget budget : budgets) {
				entityManager.persist(budget);
			}

			// 3. Générer et sauvegarder les tickets
			List<Ticket> tickets = generateRandomTickets(customers, ticketPerCustomer);
			for (Ticket ticket : tickets) {
				entityManager.persist(ticket.getExpense());
				entityManager.persist(ticket);
			}

			// 4. Générer et sauvegarder les leads
			List<Lead> leads = generateRandomLeads(customers, leadPerCustomer);
			for (Lead lead : leads) {
				entityManager.persist(lead.getExpense());
				entityManager.persist(lead);
			}

			// 5. Retourner des statistiques sur les données générées
			Map<String, Integer> stats = new HashMap<>();
			stats.put("customers", customers.size());
			stats.put("budgets", budgets.size());
			stats.put("tickets", tickets.size());
			stats.put("leads", leads.size());

			return stats;

		} catch (Exception e) {
			throw new SQLDataException("Erreur lors de la génération de données aléatoires: " + e.getMessage());
		}
	}

	public void clearDataCompletionErrors() {
		dataCompletionErrors.clear();
	}
}
