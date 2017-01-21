package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class TransactionListAction extends Action {

    public TransactionListAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "transaction-list.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        return "transaction-list.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
