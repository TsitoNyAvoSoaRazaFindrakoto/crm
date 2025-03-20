# CRM Web Application - Functionalities and Key Methods

## Overview

This document provides a detailed overview of the CRM Web Application, outlining its main functionalities and the key methods that enable these features. The application is built using Spring Boot MVC, Thymeleaf, Hibernate, MySQL, and Java 17. It integrates with Google services such as Google Drive, Gmail, and Google Calendar to enhance productivity and collaboration.

## Main Functionalities

### 1. User Authentication and Authorization

*   **Description:** Secure user login using credentials or Google accounts.
*   **Key Methods:**
    *   `UserAuthenticationService.authenticateUser()`: Handles user authentication.
    *   `GoogleOAuth2Service.handleGoogleAuthentication()`: Manages Google OAuth2 authentication flow.
    *   `UserAuthorizationService.authorizeUser()`: Determines user access rights based on roles.

### 2. Google Drive Integration

*   **Description:** Allows users to manage files and folders directly within the CRM application.
*   **Key Methods:**
    *   `GoogleDriveService.createFolder()`: Creates new folders in Google Drive.
    *   `GoogleDriveService.uploadFile()`: Uploads files to Google Drive.
    *   `GoogleDriveService.deleteFile()`: Deletes files from Google Drive.
    *   `GoogleDriveService.shareFile()`: Shares files with other users.
    *   `GoogleDriveService.listFiles()`: Lists files and folders in a specified Google Drive directory.

### 3. Google Calendar Integration

*   **Description:** Manages calendar events, meetings, and automated email notifications.
*   **Key Methods:**
    *   `GoogleCalendarService.createEvent()`: Creates new calendar events.
    *   `GoogleCalendarService.updateEvent()`: Updates existing calendar events.
    *   `GoogleCalendarService.deleteEvent()`: Deletes calendar events.
    *   `GoogleCalendarService.getEvents()`: Retrieves calendar events.
    *   `CalendarEventNotificationService.sendNotification()`: Sends email notifications for scheduled or modified meetings.

### 4. Google Gmail Integration

*   **Description:** Enables users to send emails, save drafts, and manage their inbox.
*   **Key Methods:**
    *   `GmailService.sendEmail()`: Sends emails via Gmail.
    *   `GmailService.saveDraft()`: Saves email drafts.
    *   `GmailService.getInbox()`: Retrieves emails from the inbox.
    *   `GmailService.getSentItems()`: Retrieves sent emails.
    *   `GmailService.deleteEmail()`: Deletes emails.

### 5. User Roles and Permissions

*   **Description:** Manages user access and permissions based on predefined roles (Manager, Employee, Customer, etc.).
*   **Key Methods:**
    *   `RoleManagementService.createUser()`: Creates new user accounts.
    *   `RoleManagementService.assignRole()`: Assigns roles to users.
    *   `PermissionService.checkPermission()`: Checks if a user has permission to access a specific feature.

### 6. Leads Management

*   **Description:** Creates, updates, and manages leads, including attachments and scheduled meetings.
*   **Key Methods:**
    *   `LeadService.createLead()`: Creates new lead entries.
    *   `LeadService.updateLead()`: Updates existing lead information.
    *   `LeadService.deleteLead()`: Deletes lead entries.
    *   `LeadService.getLead()`: Retrieves lead details.

### 7. Tickets Management

*   **Description:** Manages support tickets, attachments, and related meetings.
*   **Key Methods:******
    *   `TicketService.createTicket()`: Creates new support tickets.
    *   `TicketService.updateTicket()`: Updates existing ticket information.
    *   `TicketService.deleteTicket()`: Deletes tickets.
    *   `TicketService.getTicket()`: Retrieves ticket details.

### 8. Contracts Management

*   **Description:** Manages contracts, including details, attachments, and expiration checks.
*   **Key Methods:**
    *   `ContractService.createContract()`: Creates new contract entries.
    *   `ContractService.updateContract()`: Updates existing contract information.
    *   `ContractService.deleteContract()`: Deletes contracts.
    *   `ContractService.getContract()`: Retrieves contract details.
    *   `ContractExpirationChecker.scheduleContractExpirationCheck()`: Schedules checks for contract expirations.

### 9. Email Templates and Campaigns

*   **Description:** Creates personalized email templates and manages email campaigns.
*   **Key Methods:**
    *   `EmailTemplateService.createTemplate()`: Creates new email templates.
    *   `EmailTemplateService.updateTemplate()`: Updates existing email templates.
    *   `EmailCampaignService.createCampaign()`: Creates new email campaigns.
    *   `EmailCampaignService.sendCampaign()`: Sends email campaigns to target users.

### 10. User Settings

*   **Description:** Configures user-specific settings, including email preferences and Google service access.
*   **Key Methods:**
    *   `UserSettingsService.updateEmailSettings()`: Updates user email settings.
    *   `UserSettingsService.updateGoogleSettings()`: Manages user access to Google services.

## Additional Notes

This overview highlights the core functionalities and methods of the CRM Web Application. Each functionality is designed to streamline business processes and enhance user productivity through integration with Google services and a robust user management system.
