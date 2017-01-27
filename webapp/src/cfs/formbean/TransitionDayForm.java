package cfs.formbean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class TransitionDayForm {
    private String closingDateISO;
    private String closingDate;
    private String lastClosingDateISO;
    private String lastClosingDate;
    private String[] closingPrices;
    private String[] fundIds;
    private Map<Integer, Double> priceForFund;

    public TransitionDayForm(HttpServletRequest request) {
        closingDateISO = request.getParameter("closingDateISO");
        lastClosingDateISO = request.getParameter("lastClosingDateISO");
        fundIds = request.getParameterValues("fundIds");
        closingPrices = request.getParameterValues("closingPrices");
    }

    public List<String> getValidationErrors() {
        if (closingDateISO == null || closingDateISO.isEmpty()) {
            return Collections.singletonList("Closing date is required!");
        }
        if (lastClosingDateISO == null || lastClosingDateISO.isEmpty()) {
            return Collections.singletonList("Last closing date is required!");
        }
        if (fundIds == null || fundIds.length == 0) {
            return Collections.singletonList("Fund IDs are required!");
        }
        if (closingPrices == null || closingPrices.length != fundIds.length) {
            return Collections.singletonList("Closing price is required for every fund!");
        }

        try {
            LocalDate date = LocalDate.parse(closingDateISO, DateTimeFormatter.ISO_DATE);
            closingDate = date.format(DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            return Collections.singletonList("Invalid closing date!");
        }

        if (lastClosingDateISO.equals("N/A")) {
            lastClosingDate = null;
        } else {
            try {
                LocalDate date = LocalDate.parse(lastClosingDateISO, DateTimeFormatter.ISO_DATE);
                lastClosingDate = date.format(DateTimeFormatter.ISO_DATE);
            } catch (Exception e) {
                return Collections.singletonList("Invalid last closing date!");
            }

            if (closingDate.compareTo(lastClosingDate) <= 0) {
                return Collections.singletonList("Closing date must be later than last closing date!");
            }
        }

        Map<Integer, Double> priceForFund = new HashMap<Integer, Double>();

        for (int i = 0; i < fundIds.length; i++) {
            int fundIdVal;
            double closingPriceVal;
            try {
                fundIdVal = Integer.parseInt(fundIds[i]);
            } catch (Exception e) {
                return Collections.singletonList("Invalid fundId!");
            }
            try {
                closingPriceVal = Double.parseDouble(closingPrices[i]);
            } catch (Exception e) {
                return Collections.singletonList("Invalid shares!");
            }

            if (closingPriceVal < 1.000) {
                return Collections.singletonList("Closing price must be at least $1.00!");
            }
            if (closingPriceVal > 10000.000) {
                return Collections.singletonList("Closing price must not be more than $10,000.00!");
            }

            priceForFund.put(fundIdVal, closingPriceVal);
        }

        this.priceForFund = Collections.unmodifiableMap(priceForFund);

        return Collections.emptyList();
    }

    public Map<Integer, Double> getPriceForFundMap() {
        return this.priceForFund;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public String getLastClosingDate() {
        return lastClosingDate;
    }

}
