package arc.resource.calculator.listeners;

public interface SendErrorReportListener {
    void onSendErrorReport( String tag, Exception e, boolean showAlertDialog );
}