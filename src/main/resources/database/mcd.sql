create table if not exists customer_login_info (
  id int auto_increment primary key,
  password varchar(255) null,
  username varchar(255) null,
  token varchar(500) null,
  password_set tinyint(1) default 0 null,
  constraint token unique (token)
) auto_increment = 19;

create table if not exists employee (
  id int auto_increment primary key,
  username varchar(45) not null,
  first_name varchar(45) not null,
  last_name varchar(45) not null,
  email varchar(45) not null,
  password varchar(80) not null,
  provider varchar(45) null
) charset = latin1
  auto_increment = 9;

create table if not exists parameter_crm (
  parameter_key varchar(50) not null primary key,
  parameter_value double null
);

create table if not exists roles (
  id int auto_increment primary key,
  name varchar(255) null
) auto_increment = 4;

create table if not exists users (
  id int auto_increment primary key,
  email varchar(100) not null,
  password varchar(255) null,
  hire_date datetime null,
  created_at timestamp default CURRENT_TIMESTAMP null,
  updated_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
  username varchar(50) not null,
  status varchar(100) null,
  token varchar(500) null,
  is_password_set tinyint(1) default 0 null,
  constraint email unique (email),
  constraint username unique (username)
) auto_increment = 52;

create table if not exists customer (
  customer_id int unsigned auto_increment primary key,
  name varchar(255) null,
  phone varchar(20) null,
  address varchar(255) null,
  city varchar(255) null,
  state varchar(255) null,
  country varchar(255) null,
  user_id int null,
  description text null,
  position varchar(255) null,
  twitter varchar(255) null,
  facebook varchar(255) null,
  youtube varchar(255) null,
  created_at datetime null,
  email varchar(255) null,
  profile_id int null,
  constraint customer_ibfk_1 foreign key (user_id) references users(id),
  constraint customer_ibfk_2 foreign key (profile_id) references customer_login_info(id)
) auto_increment = 43;

create table if not exists budget (
  budget_id int unsigned auto_increment primary key,
  title varchar(255) not null,
  amount decimal(10,2) not null,
  start_date date not null,
  end_date date not null,
  customer_id int unsigned not null,
  constraint budgets_ibfk_1 foreign key (customer_id) references customer(customer_id)
);

create index customer_id on budget(customer_id);

create index profile_id on customer(profile_id);

create index user_id on customer(user_id);

create table if not exists email_template (
  template_id int unsigned auto_increment primary key,
  name varchar(255) null,
  content text null,
  user_id int null,
  json_design text null,
  created_at datetime null,
  constraint name unique (name),
  constraint email_template_ibfk_1 foreign key (user_id) references users(id)
) auto_increment = 35;

create table if not exists contract_settings (
  id int unsigned auto_increment primary key,
  amount tinyint(1) null,
  subject tinyint(1) null,
  description tinyint(1) null,
  end_date tinyint(1) null,
  start_date tinyint(1) null,
  status tinyint(1) null,
  user_id int null,
  status_email_template int unsigned null,
  amount_email_template int unsigned null,
  subject_email_template int unsigned null,
  description_email_template int unsigned null,
  start_email_template int unsigned null,
  end_email_template int unsigned null,
  customer_id int null,
  constraint contract_settings_ibfk_1 foreign key (user_id) references users(id),
  constraint contract_settings_ibfk_2 foreign key (status_email_template) references email_template(template_id),
  constraint contract_settings_ibfk_3 foreign key (amount_email_template) references email_template(template_id),
  constraint contract_settings_ibfk_4 foreign key (subject_email_template) references email_template(template_id),
  constraint contract_settings_ibfk_5 foreign key (description_email_template) references email_template(template_id),
  constraint contract_settings_ibfk_6 foreign key (start_email_template) references email_template(template_id),
  constraint contract_settings_ibfk_7 foreign key (end_email_template) references email_template(template_id),
  constraint contract_settings_ibfk_8 foreign key (customer_id) references customer_login_info(id)
) auto_increment = 4;

create index amount_email_template on contract_settings(amount_email_template);

create index customer_id on contract_settings(customer_id);

create index description_email_template on contract_settings(description_email_template);

create index end_email_template on contract_settings(end_email_template);

create index start_email_template on contract_settings(start_email_template);

create index status_email_template on contract_settings(status_email_template);

create index subject_email_template on contract_settings(subject_email_template);

create index user_id on contract_settings(user_id);

create index user_id on email_template(user_id);

create table if not exists expense (
  expense_id int unsigned auto_increment primary key,
  amount decimal(10,2) not null,
  date_expense date not null,
  description varchar(255) null,
  budget_id int unsigned null,
  customer_id int unsigned not null,
  constraint expense_ibfk_1 foreign key (budget_id) references budget(budget_id),
  constraint expense_ibfk_2 foreign key (customer_id) references customer(customer_id)
);

create index budget_id on expense(budget_id);

create index customer_id on expense(customer_id);

create table if not exists lead_settings (
  id int unsigned auto_increment primary key,
  status tinyint(1) null,
  meeting tinyint(1) null,
  phone tinyint(1) null,
  name tinyint(1) null,
  user_id int null,
  status_email_template int unsigned null,
  phone_email_template int unsigned null,
  meeting_email_template int unsigned null,
  name_email_template int unsigned null,
  customer_id int null,
  constraint lead_settings_ibfk_1 foreign key (user_id) references users(id),
  constraint lead_settings_ibfk_2 foreign key (status_email_template) references email_template(template_id),
  constraint lead_settings_ibfk_3 foreign key (phone_email_template) references email_template(template_id),
  constraint lead_settings_ibfk_4 foreign key (meeting_email_template) references email_template(template_id),
  constraint lead_settings_ibfk_5 foreign key (name_email_template) references email_template(template_id),
  constraint lead_settings_ibfk_6 foreign key (customer_id) references customer_login_info(id)
) auto_increment = 3;

create index customer_id on lead_settings(customer_id);

create index meeting_email_template on lead_settings(meeting_email_template);

create index name_email_template on lead_settings(name_email_template);

create index phone_email_template on lead_settings(phone_email_template);

create index status_email_template on lead_settings(status_email_template);

create index user_id on lead_settings(user_id);

create table if not exists oauth_users (
  id int auto_increment primary key,
  user_id int null,
  access_token varchar(255) not null,
  access_token_issued_at datetime not null,
  access_token_expiration datetime not null,
  refresh_token varchar(255) not null,
  refresh_token_issued_at datetime not null,
  refresh_token_expiration datetime null,
  granted_scopes varchar(255) null,
  email varchar(255) null,
  constraint email unique (email),
  constraint oauth_users_ibfk_1 foreign key (user_id) references users(id)
) auto_increment = 28;

create table if not exists ticket_settings (
  id int unsigned auto_increment primary key,
  priority tinyint(1) null,
  subject tinyint(1) null,
  description tinyint(1) null,
  status tinyint(1) null,
  user_id int null,
  status_email_template int unsigned null,
  subject_email_template int unsigned null,
  priority_email_template int unsigned null,
  description_email_template int unsigned null,
  customer_id int null,
  constraint ticket_settings_ibfk_1 foreign key (user_id) references users(id),
  constraint ticket_settings_ibfk_2 foreign key (status_email_template) references email_template(template_id),
  constraint ticket_settings_ibfk_3 foreign key (subject_email_template) references email_template(template_id),
  constraint ticket_settings_ibfk_4 foreign key (priority_email_template) references email_template(template_id),
  constraint ticket_settings_ibfk_5 foreign key (description_email_template) references email_template(template_id),
  constraint ticket_settings_ibfk_6 foreign key (customer_id) references customer_login_info(id)
) auto_increment = 6;

create index customer_id on ticket_settings(customer_id);

create index description_email_template on ticket_settings(description_email_template);

create index phone_email_template on ticket_settings(subject_email_template);

create index priority_email_template on ticket_settings(priority_email_template);

create index status_email_template on ticket_settings(status_email_template);

create index user_id on ticket_settings(user_id);

create table if not exists trigger_lead (
  lead_id int unsigned auto_increment primary key,
  customer_id int unsigned not null,
  user_id int null,
  name varchar(255) null,
  phone varchar(20) null,
  employee_id int null,
  status varchar(50) null,
  meeting_id varchar(255) null,
  google_drive tinyint(1) null,
  google_drive_folder_id varchar(255) null,
  created_at datetime null,
  expense_id int unsigned null,
  constraint meeting_info unique (meeting_id),
  constraint trigger_lead_ibfk_1 foreign key (customer_id) references customer(customer_id),
  constraint trigger_lead_ibfk_2 foreign key (user_id) references users(id),
  constraint trigger_lead_ibfk_3 foreign key (employee_id) references users(id),
  constraint trigger_lead_ibfk_4 foreign key (expense_id) references expense(expense_id)
) auto_increment = 56;

create table if not exists lead_action (
  id int unsigned auto_increment primary key,
  lead_id int unsigned not null,
  action varchar(255) null,
  date_time datetime null,
  constraint lead_action_ibfk_1 foreign key (lead_id) references trigger_lead(lead_id)
) auto_increment = 13;

create index lead_id on lead_action(lead_id);

create table if not exists trigger_contract (
  contract_id int unsigned auto_increment primary key,
  subject varchar(255) null,
  status varchar(100) null,
  description text null,
  start_date date null,
  end_date date null,
  amount decimal null,
  google_drive tinyint(1) null,
  google_drive_folder_id varchar(255) null,
  lead_id int unsigned null,
  user_id int null,
  customer_id int unsigned null,
  created_at datetime null,
  constraint trigger_contract_ibfk_1 foreign key (lead_id) references trigger_lead(lead_id),
  constraint trigger_contract_ibfk_2 foreign key (user_id) references users(id),
  constraint trigger_contract_ibfk_3 foreign key (customer_id) references customer(customer_id)
) auto_increment = 19;

create table if not exists file (
  file_id int auto_increment primary key,
  file_name varchar(100) null,
  file_data blob null,
  file_type varchar(255) null,
  lead_id int unsigned null,
  contract_id int unsigned null,
  constraint file_ibfk_1 foreign key (lead_id) references trigger_lead(lead_id),
  constraint file_ibfk_2 foreign key (contract_id) references trigger_contract(contract_id)
) auto_increment = 140;

create index contract_id on file(contract_id);

create index lead_id on file(lead_id);

create table if not exists google_drive_file (
  id int unsigned auto_increment primary key,
  drive_file_id varchar(255) null,
  drive_folder_id varchar(255) null,
  lead_id int unsigned null,
  contract_id int unsigned null,
  constraint google_drive_file_ibfk_1 foreign key (lead_id) references trigger_lead(lead_id),
  constraint google_drive_file_ibfk_2 foreign key (contract_id) references trigger_contract(contract_id)
) auto_increment = 52;

create index contract_id on google_drive_file(contract_id);

create index lead_id on google_drive_file(lead_id);

create index customer_id on trigger_contract(customer_id);

create index lead_id on trigger_contract(lead_id);

create index user_id on trigger_contract(user_id);

create index customer_id on trigger_lead(customer_id);

create index employee_id on trigger_lead(employee_id);

create index expense_id on trigger_lead(expense_id);

create index user_id on trigger_lead(user_id);

create table if not exists trigger_ticket (
  ticket_id int unsigned auto_increment primary key,
  subject varchar(255) null,
  description text null,
  status varchar(50) null,
  priority varchar(50) null,
  customer_id int unsigned not null,
  manager_id int null,
  employee_id int null,
  created_at datetime null,
  expense_id int unsigned null,
  constraint fk_ticket_customer foreign key (customer_id) references customer(customer_id),
  constraint fk_ticket_employee foreign key (employee_id) references users(id),
  constraint fk_ticket_ibfk_3 foreign key (expense_id) references expense(expense_id),
  constraint fk_ticket_manager foreign key (manager_id) references users(id)
) auto_increment = 47;

create index expense_id on trigger_ticket(expense_id);

create table if not exists user_profile (
  id int unsigned auto_increment primary key,
  first_name varchar(50) null,
  last_name varchar(50) null,
  phone varchar(50) null,
  department varchar(255) null,
  salary decimal(10,2) null,
  status varchar(50) null,
  oauth_user_image_link varchar(255) null,
  user_image blob null,
  bio text null,
  youtube varchar(255) null,
  twitter varchar(255) null,
  facebook varchar(255) null,
  user_id int null,
  country varchar(100) null,
  position varchar(100) null,
  address varchar(255) null,
  constraint user_profile_ibfk_1 foreign key (user_id) references users(id)
) auto_increment = 33;

create index user_id on user_profile(user_id);

create table if not exists user_roles (
  user_id int not null,
  role_id int not null,
  constraint `PRIMARY` primary key (user_id,role_id),
  constraint user_roles_ibfk_1 foreign key (user_id) references users(id),
  constraint user_roles_ibfk_2 foreign key (role_id) references roles(id)
);

create index role_id on user_roles(role_id);

