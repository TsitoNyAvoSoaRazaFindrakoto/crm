# User Management

This document provides detailed information on the User Management feature of the CRM Web Application, which allows administrators to create, edit, and manage user accounts and their permissions.

## Overview

The User Management feature allows authorized administrators (typically users with the Manager role) to create and maintain user accounts, assign roles and permissions, manage access to different parts of the system, and configure user settings.

## User Roles and Permissions

### Default User Roles

The CRM system includes the following default roles:

1. **Manager**: Full administrative access to all features and data
2. **Employee**: Access to assigned customers, tickets, leads, and contracts
3. **Sales**: Focus on sales activities, leads, and contracts
4. **Support**: Focus on customer support and ticket management
5. **Customer**: Limited access to their own tickets, contracts, and profile

### Role Capabilities

Each role has predefined capabilities:

| Capability | Manager | Employee | Sales | Support | Customer |
|------------|:-------:|:--------:|:-----:|:-------:|:--------:|
| View All Users | ✓ | ✗ | ✗ | ✗ | ✗ |
| Create Users | ✓ | ✗ | ✗ | ✗ | ✗ |
| Edit Users | ✓ | ✗ | ✗ | ✗ | ✗ |
| Delete Users | ✓ | ✗ | ✗ | ✗ | ✗ |
| Manage Roles | ✓ | ✗ | ✗ | ✗ | ✗ |
| View All Customers | ✓ | ✗ | ✓ | ✓ | ✗ |
| View All Tickets | ✓ | ✗ | ✗ | ✓ | ✗ |
| View All Leads | ✓ | ✗ | ✓ | ✗ | ✗ |
| View All Contracts | ✓ | ✗ | ✓ | ✗ | ✗ |
| System Settings | ✓ | ✗ | ✗ | ✗ | ✗ |

### Permission Structure

Permissions are organized in a hierarchical structure:

1. **Object-level permissions**: Determine which CRM objects a user can access
2. **Action-level permissions**: Determine what actions a user can perform on those objects
3. **Record-level permissions**: Determine which specific records a user can access
4. **Field-level permissions**: Determine which fields a user can view or edit

## Managing Users

### Accessing User Management

To access the User Management interface:

1. Log in with a Manager account
2. Navigate to "Administration" > "User Management"

### Viewing Users

The User Management interface displays a list of all users with basic information:

1. Username
2. Full name
3. Email address
4. Role
5. Status (Active, Inactive, Locked)
6. Last login date

Use the search and filter options to find specific users.

### Creating a New User

To create a new user:

1. Navigate to "Administration" > "User Management"
2. Click "Create User"
3. Fill in the user details:
   - Username
   - Email address
   - First and last name
   - Password (or select "Send setup email")
   - Role
   - Department (optional)
   - Manager (for reporting hierarchy)
   - Time zone and locale
4. Set the user status (typically "Active")
5. Configure additional options:
   - Force password change on first login
   - Two-factor authentication requirement
   - Account expiration date (if applicable)
6. Click "Create User"

### Editing User Information

To edit an existing user:

1. Navigate to "Administration" > "User Management"
2. Find the user in the list and click their name
3. Click "Edit"
4. Modify the user information as needed
5. Click "Save Changes"

### Resetting User Passwords

To reset a user's password:

1. Navigate to "Administration" > "User Management"
2. Find the user in the list
3. Click the "Actions" dropdown
4. Select "Reset Password"
5. Choose one of the following options:
   - Set a specific password
   - Generate a random password
   - Send password reset email
6. Click "Reset Password"

### Deactivating and Reactivating Users

#### Deactivating a User

When a user no longer needs access:

1. Navigate to "Administration" > "User Management"
2. Find the user in the list
3. Click the "Actions" dropdown
4. Select "Deactivate User"
5. Confirm the deactivation

Deactivated users cannot log in but their account information and history are preserved.

#### Reactivating a User

To restore access for a deactivated user:

1. Navigate to "Administration" > "User Management"
2. Set the filter to show inactive users
3. Find the user in the list
4. Click the "Actions" dropdown
5. Select "Activate User"
6. Confirm the activation

### Deleting Users

Deleting a user permanently removes their account:

1. Navigate to "Administration" > "User Management"
2. Find the user in the list
3. Click the "Actions" dropdown
4. Select "Delete User"
5. Review the impact (assignments, ownership transfer options)
6. Confirm the deletion

**Note**: User deletion is typically not recommended; deactivation is preferred to maintain data integrity and history.

## Managing Roles

### Viewing Roles

To view the existing roles:

1. Navigate to "Administration" > "Roles & Permissions"
2. View the list of roles with their descriptions and user counts

### Creating Custom Roles

To create a new role:

1. Navigate to "Administration" > "Roles & Permissions"
2. Click "Create Role"
3. Enter role details:
   - Role name
   - Description
   - Base role (optional, to inherit permissions)
4. Configure permissions:
   - Select object permissions
   - Configure action permissions for each object
   - Set record access levels
   - Configure field-level permissions
5. Click "Create Role"

### Editing Roles

To modify an existing role:

1. Navigate to "Administration" > "Roles & Permissions"
2. Click on the role name
3. Click "Edit"
4. Modify the role settings as needed
5. Click "Save Changes"

### Assigning Roles to Users

To change a user's role:

1. Navigate to "Administration" > "User Management"
2. Find the user in the list and click their name
3. Click "Edit"
4. Change the "Role" selection
5. Click "Save Changes"

## User Profile Settings

### Configuring Default Profile Settings

Configure default settings for new users:

1. Navigate to "Administration" > "System Settings" > "User Defaults"
2. Configure default settings:
   - Password complexity requirements
   - Session timeout duration
   - Default dashboard layout
   - Default notification preferences
   - Default timezone and locale
3. Click "Save"

### User Self-Service Options

Control which profile settings users can modify themselves:

1. Navigate to "Administration" > "System Settings" > "User Self-Service"
2. Enable or disable self-service options:
   - Password changes
   - Personal information updates
   - Notification preferences
   - Display settings
   - Two-factor authentication setup
3. Click "Save"

## User Groups

User groups allow you to organize users beyond their roles.

### Creating User Groups

To create a new group:

1. Navigate to "Administration" > "User Groups"
2. Click "Create Group"
3. Enter group details:
   - Group name
   - Description
   - Group type (Department, Team, Project, etc.)
4. Add members to the group
5. Click "Create Group"

### Managing Group Membership

To modify group membership:

1. Navigate to "Administration" > "User Groups"
2. Click on the group name
3. Click "Edit Members"
4. Add or remove users
5. Click "Save Changes"

### Using Groups for Permissions

Groups can be used to assign permissions to multiple users:

1. Navigate to "Administration" > "Roles & Permissions"
2. Create or edit a role
3. In the record access section, select "Group" as the access type
4. Select the specific groups that should have access
5. Click "Save"

## Audit and Security

### User Activity Logging

The system maintains logs of user activities:

1. Navigate to "Administration" > "System Logs" > "User Activity"
2. View login history, page access, and data modifications
3. Filter logs by user, date range, or activity type
4. Export logs for compliance or analysis

### Security Settings

Configure security settings for all users:

1. Navigate to "Administration" > "Security Settings"
2. Configure options:
   - Password policy (complexity, expiration, history)
   - Session management (timeout, concurrent sessions)
   - IP restrictions
   - Two-factor authentication requirements
   - Failed login attempt handling
3. Click "Save Security Settings"

## Best Practices

### User Management Best Practices

1. **Role-Based Access**: Assign permissions based on roles rather than individual users
2. **Least Privilege**: Give users only the permissions they need to perform their job
3. **Regular Audits**: Periodically review user accounts and permissions
4. **Consistent Naming**: Use consistent naming conventions for usernames
5. **Documentation**: Maintain documentation of custom roles and their purposes
6. **Training**: Ensure users understand their permissions and responsibilities
7. **Exit Process**: Establish a process for deactivating users when they leave
8. **Separation of Duties**: Ensure critical functions require multiple users
9. **Testing**: Test custom roles to ensure they have appropriate access
10. **Backup Admin**: Maintain at least two administrator accounts

### Admin Account Security

Special considerations for administrator accounts:

1. Use strong, unique passwords for admin accounts
2. Require multi-factor authentication for all admin accounts
3. Limit the number of users with administrator access
4. Use separate accounts for administrative and regular activities
5. Regularly audit administrator activities

## Troubleshooting

### Common Issues

1. **User Cannot Log In**: Check account status, password, and lockout status
2. **Missing Permissions**: Verify role assignments and permission configuration
3. **Cannot Deactivate User**: Check for dependencies (ownership of records)
4. **Role Changes Not Effective**: Ensure the user has logged out and back in
5. **Password Reset Emails Not Received**: Check email configuration and spam filters

For additional help, contact your system administrator or refer to the [Troubleshooting](../troubleshooting.md) guide.
