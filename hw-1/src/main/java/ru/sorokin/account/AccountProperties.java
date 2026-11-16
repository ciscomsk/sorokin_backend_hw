package ru.sorokin.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final int defaultAmount;
    private final double transferCommission;

    // TODO: для финансового учета нужно использовать BigDecimal
    public AccountProperties(
            @Value("${account.default-amount}") int defaultAmount,
            @Value("${account.transfer-commission}") double transferCommission
    ) {
        validate(defaultAmount, transferCommission);
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }

    private void validate(int defaultAmount, double transferCommission) {
        if (defaultAmount < 0) {
            throw new IllegalArgumentException("The initial balance must be non-negative");
        }

        if (transferCommission < 0 || transferCommission > 1 || !Double.isFinite(transferCommission)) {
            throw new IllegalArgumentException("The commission must be a finite number from 0 to 1, inclusive");
        }
    }
}
