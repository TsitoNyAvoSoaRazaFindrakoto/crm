# Email Templates and Campaigns

This document provides comprehensive information on creating and managing email templates and campaigns in the CRM Web Application.

## Overview

The Email Templates and Campaigns feature allows users to create standardized email content for consistent communication with customers and prospects. These templates can be used for individual emails or as part of larger email campaigns. The system integrates with Gmail for sending emails and tracks engagement metrics.

## Email Templates

### Template Types

The CRM supports several types of email templates:

1. **Customer Communication Templates**: For general customer correspondence
2. **Lead Nurturing Templates**: For communicating with leads at different stages
3. **Contract Templates**: For contract-related communications
4. **Support Templates**: For ticket-related communications
5. **Internal Templates**: For communication between team members
6. **Notification Templates**: For automated system notifications

### Template Components

Each email template consists of:

- **Subject Line**: The email subject
- **Body Content**: The main email content, with support for HTML formatting
- **Placeholders**: Dynamic content that gets replaced with actual values
- **Attachments**: Default files to include with the email
- **Style Settings**: Font, colors, and layout options

### Creating Email Templates

#### Basic Template Creation

1. Navigate to "Email" > "Templates" > "Create Template"
2. Enter a name for the template
3. Select a template type
4. Enter a subject line
5. Compose the email body using the rich text editor
6. Add placeholders for dynamic content
7. Click "Save Template"

#### Using the Visual Template Builder

For more complex templates:

1. Navigate to "Email" > "Templates" > "Create Template"
2. Click "Use Visual Builder"
3. Use the drag-and-drop interface to add elements:
   - Text blocks
   - Images
   - Buttons
   - Dividers
   - Social media links
4. Customize each element's appearance
5. Add placeholders for dynamic content
6. Preview the template in different devices
7. Click "Save Template"

### Using Placeholders

Placeholders dynamically insert personalized information into emails:

- `{{customer.firstName}}`: Customer's first name
- `{{customer.lastName}}`: Customer's last name
- `{{customer.company}}`: Customer's company name
- `{{ticket.subject}}`: Ticket subject
- `{{contract.endDate}}`: Contract end date
- `{{user.signature}}`: Your email signature

To use placeholders:

1. Place your cursor where you want the dynamic content
2. Click the "Add Placeholder" button
3. Select the placeholder from the dropdown list

### Managing Templates

#### Viewing Templates

1. Navigate to "Email" > "Templates"
2. Browse the list of available templates
3. Use filters to find specific templates by type, name, or creator

#### Editing Templates

1. Navigate to "Email" > "Templates"
2. Find the template you want to edit
3. Click "Edit"
4. Make your changes
5. Click "Save Template"

#### Duplicating Templates

1. Navigate to "Email" > "Templates"
2. Find the template you want to duplicate
3. Click "Duplicate"
4. Modify the copy as needed
5. Save with a new name

#### Deleting Templates

1. Navigate to "Email" > "Templates"
2. Find the template you want to delete
3. Click "Delete"
4. Confirm the deletion

## Email Campaigns

### Campaign Types

The CRM supports various email campaign types:

1. **Drip Campaigns**: Series of emails sent at predetermined intervals
2. **Onboarding Campaigns**: Welcoming and orienting new customers
3. **Lead Nurturing Campaigns**: Moving leads through the sales funnel
4. **Re-engagement Campaigns**: Reconnecting with inactive customers
5. **Newsletter Campaigns**: Regular updates and information
6. **Promotional Campaigns**: Marketing specific products or services

### Creating an Email Campaign

1. Navigate to "Email" > "Campaigns" > "Create Campaign"
2. Enter campaign details:
   - Campaign name
   - Description
   - Start and end dates
   - Target audience
3. Select or create email templates for each message in the campaign
4. Set the sending schedule:
   - Specific dates and times
   - Intervals between emails
   - Trigger-based sending
5. Configure tracking options
6. Click "Save Campaign"

### Building Campaign Sequences

For multi-stage campaigns:

1. Create the campaign as described above
2. Click "Add Email" to add each message in the sequence
3. For each email, specify:
   - Email template
   - Sending timing (days after previous email or specific date)
   - Conditions for sending (e.g., only if previous email was opened)
4. Arrange emails in the desired sequence
5. Set up alternative paths based on recipient actions

### Managing Campaign Recipients

#### Adding Recipients

1. Open the campaign
2. Click "Manage Recipients"
3. Add recipients by:
   - Uploading a CSV file
   - Selecting from existing customers or leads
   - Using saved filters or segments
   - Manually entering email addresses
4. Click "Add Recipients"

#### Excluding Recipients

1. Open the campaign
2. Click "Manage Recipients"
3. Click "Exclusions"
4. Add email addresses or select groups to exclude
5. Click "Save Exclusions"

### Scheduling and Sending Campaigns

#### Manual Sending

1. Open the campaign
2. Review all settings and content
3. Click "Send Now" or "Schedule"
4. If scheduling, set the date and time
5. Confirm the sending

#### Automated Sending

Configure campaigns to send automatically based on:
- Predefined schedule
- Customer actions or events
- CRM data changes (e.g., contract expiration approaching)

### Campaign Analytics

Track campaign performance with these metrics:

1. **Delivery Rate**: Percentage of emails successfully delivered
2. **Open Rate**: Percentage of recipients who opened the email
3. **Click Rate**: Percentage of recipients who clicked links
4. **Reply Rate**: Percentage of recipients who replied
5. **Unsubscribe Rate**: Percentage of recipients who unsubscribed
6. **Bounce Rate**: Percentage of emails that couldn't be delivered
7. **Conversion Rate**: Percentage of recipients who completed desired actions

To view analytics:

1. Navigate to "Email" > "Campaigns"
2. Select the campaign
3. Click "Analytics"
4. View overall performance or drill down to individual email metrics

## Gmail Integration

### Connecting Your Gmail Account

1. Navigate to your profile settings
2. Click "Google Services"
3. Click "Connect" next to Gmail
4. Follow the prompts to authorize the CRM to access your Gmail account
5. Once connected, you can send emails through Gmail from within the CRM

### Sending Individual Emails with Templates

1. Navigate to the customer, lead, ticket, or contract
2. Click "Send Email"
3. Click "Use Template"
4. Select a template from the list
5. The template content will load into the editor
6. Customize as needed
7. Click "Send"

### Email Tracking

When sending emails through the CRM:

1. Open and click tracking is automatically enabled
2. View tracking information in the communication history
3. Receive notifications when recipients open emails or click links

## Automated Email Notifications

### System Notifications

Configure automated emails for system events:

1. Navigate to "Settings" > "Email Settings" > "Notifications"
2. Select which events trigger emails:
   - Ticket creation or updates
   - Lead assignment
   - Contract expiration
   - Task assignments and deadlines
3. Select or create templates for each notification type
4. Save your notification settings

### Personalized Notification Settings

Each user can configure their personal notification preferences:

1. Navigate to your profile settings
2. Click "Notification Preferences"
3. Select which notifications you want to receive by email
4. Save your preferences

## Best Practices

### Email Template Best Practices

1. **Clear Purpose**: Each template should have a specific purpose
2. **Consistent Branding**: Maintain consistent visual identity
3. **Mobile Optimization**: Ensure templates display well on mobile devices
4. **Personalization**: Use placeholders to personalize content
5. **Concise Content**: Keep messages clear and to the point
6. **Strong Call-to-Action**: Include clear next steps or actions
7. **Alternative Text**: Add alt text for images
8. **Test Before Use**: Test templates with different email clients

### Email Campaign Best Practices

1. **Targeted Audiences**: Segment recipients for relevant messaging
2. **Permission-Based**: Only send to recipients who have opted in
3. **Optimal Timing**: Send at times when recipients are likely to engage
4. **Progressive Content**: Build campaigns that progress logically
5. **A/B Testing**: Test different subject lines or content versions
6. **Responsive Design**: Ensure campaigns work on all devices
7. **Analytics Review**: Regularly review performance metrics
8. **Unsubscribe Option**: Always include an easy way to unsubscribe

## Troubleshooting

### Common Issues

1. **Emails Not Sending**: Check Gmail connection and sending limits
2. **Placeholder Not Working**: Verify placeholder syntax and available data
3. **Poor Rendering in Some Clients**: Test and adjust template HTML
4. **Low Open Rates**: Review subject lines and sending times
5. **High Bounce Rate**: Clean your recipient list
6. **Emails Going to Spam**: Review content for spam triggers

For additional help, contact your system administrator or refer to the [Troubleshooting](../troubleshooting.md) guide.
