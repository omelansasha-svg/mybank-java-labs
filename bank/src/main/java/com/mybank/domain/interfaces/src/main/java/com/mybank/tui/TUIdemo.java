package com.mybank.tui;

import com.mybank.data.DataSource;
import com.mybank.domain.Bank;
import com.mybank.report.CustomerReport;
import jexer.TAction;
import jexer.TApplication;
import jexer.TField;
import jexer.TText;
import jexer.TWindow;
import jexer.event.TMenuEvent;
import jexer.menu.TMenu;

public final class TUIdemo extends TApplication {
    private static final int CUSTOMER_INFO = 2000;
    private static final int ABOUT = 2001;
    private final Bank bank;

    public TUIdemo(Bank bank) throws Exception {
        super(BackendType.SWING);
        this.bank = bank;

        TMenu file = addMenu("&File");
        file.addItem(CUSTOMER_INFO, "&Customer Info");
        file.addSeparator();
        file.addDefaultItem(TMenu.MID_EXIT);
        addWindowMenu();
        addMenu("&Help").addItem(ABOUT, "&About");
        showCustomerDetails();
    }

    @Override
    protected boolean onMenu(TMenuEvent event) {
        return switch (event.getId()) {
            case CUSTOMER_INFO -> {
                showCustomerDetails();
                yield true;
            }
            case ABOUT -> {
                messageBox("About", "Banking | Lab 1 | Jexer").show();
                yield true;
            }
            default -> super.onMenu(event);
        };
    }

    private void showCustomerDetails() {
        TWindow window = addWindow("Customer Information", 2, 2, 58, 12, TWindow.NOZOOMBOX);
        window.newStatusBar("Enter a customer number starting at 0, then select Show.");
        window.addLabel("Customer number", 2, 2);
        TField number = window.addField(20, 2, 5, false);
        TText details = window.addText("", 2, 5, 51, 3);
        window.addButton("&Show", 29, 2, new TAction() {
            @Override
            public void DO() {
                try {
                    details.setText(CustomerReport.firstAccount(bank.customer(number.getText())));
                } catch (IllegalArgumentException e) {
                    details.setText("");
                    messageBox("Invalid customer", e.getMessage()).show();
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new TUIdemo(DataSource.open()).run();
    }
}
