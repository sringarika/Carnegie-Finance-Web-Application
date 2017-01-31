package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.databean.Transactions;
import cfs.model.Model;
import cfs.model.TransactionDAO;
import cfs.viewbean.TransactionHistoryView;

public class TransactionListAction extends Action {

    private TransactionDAO transactionDAO;

    public TransactionListAction(Model model) {
        transactionDAO = model.getTransactionDAO();
    }

    @Override
    public String getName() {
        return "transaction-list.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        try {
            TransactionHistoryView[] transactions = transactionDAO.showAll();
            request.setAttribute("transactions", transactions);
            int pendingCount = 0;
            int processedCount = 0;
            for (TransactionHistoryView transaction : transactions) {
                if (transaction.getTransactionStatus().equals(Transactions.PENDING)) {
                    pendingCount += 1;
                } else {
                    processedCount += 1;
                }
            }
            request.setAttribute("pendingCount", pendingCount);
            request.setAttribute("processedCount", processedCount);
            return "transaction-list.jsp";
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
