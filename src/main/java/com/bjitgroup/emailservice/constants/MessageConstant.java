/**
 * Created by Mohammad Khalid Hasan|| BJIT-R&D
 * Since: 3/27/2024
 * Version: 1.0
 */

package com.bjitgroup.emailservice.constants;

public class MessageConstant {
    //MailConfig Class Messages
    public static final String invalid_email_service = "Invalid mail service type: ";

    // MailContent Class Messages
    public static final String from_email_address_invalid = "Invalid 'from' email address. ";
    public static final String to_email_address_empty = "Please provide at least one recipient email address.";

    public static final String io_exception = "IO exception occur in request ";

    //SMTP Mail Service Class Messages
    public static final String sendMail_success = "mail sent successfully";
    public static final String sendMail_error = "mail sending failed";
    public static final String successfully_sending_html_template_email =
            "HTML template mail has been sent successfully.";
    public static final String error_sending_html_template_email = "Error sending HTML template email";

    public static final String process_attachment_error = "Error sending email attachments";

    //LoadMailTemplate Class Messages
    public static final String unsuccessful_template_loading = "Error loading HTML template file!";

    //MailContentValidation Class Messages
    public static final String not_null_MailContent = "MailContent must not be null";

    //MailValidation Class Messages
    public static final String unsupported_file_type = "Unsupported file type:";
    public static final String fileNotExists = "File does not exist:";
    public static final String maximum_attachment_file_size = "File size exceeds the maximum limit of 5MB.";
    public static final String html_file_type_mismatched = "Invalid File! Please provide an HTML file only.";

    // ValidAttachmentSize Class Messages
    public static final String valid_Attachment_Size_message = "Total size of attachments must not exceed 25 MB.";

}
