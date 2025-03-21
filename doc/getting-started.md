# Getting Started with CRM Web Application

This guide will help you set up and run the CRM Web Application on your system.

## Prerequisites

Before installing the CRM application, ensure you have the following:

- Java 17 or later installed
- MySQL 5.7 or later database server
- Maven 3.6 or later for building the application
- Google API credentials for integration with Google services (Drive, Gmail, Calendar)
- Modern web browser (Chrome, Firefox, Edge, Safari)

## Installation

Follow these steps to install and run the CRM application:

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-organization/crm.git
   cd crm
   ```

2. **Configure database connection**

   Edit `src/main/resources/application.properties` and update the following properties:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/crm?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Configure Google API credentials**

   Create a `google-credentials.json` file in the `src/main/resources` directory with your Google API credentials.

4. **Build the application**

   ```bash
   mvn clean package
   ```

5. **Run the application**

   ```bash
   java -jar target/crm-0.0.1-SNAPSHOT.jar
   ```

6. **Access the application**

   Open your web browser and navigate to:

   ```
   http://localhost:8080
   ```

## Configuration

### Google API Configuration

To enable integration with Google services:

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project
3. Enable the following APIs:
   - Google Drive API
   - Gmail API
   - Google Calendar API
4. Create OAuth 2.0 credentials
5. Add authorized redirect URIs (e.g., `http://localhost:8080/oauth2/callback/google`)
6. Download the credentials JSON file and place it in the `src/main/resources` directory

### Email Configuration

To configure email notifications:

1. Edit `src/main/resources/application.properties`
2. Update the following properties:

   ```properties
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

## Initial Setup

After starting the application for the first time:

1. Navigate to `http://localhost:8080`
2. Log in with the default admin credentials:
   - Username: `admin`
   - Password: `admin123`
3. You will be prompted to change the password
4. Create additional users and assign roles as needed
