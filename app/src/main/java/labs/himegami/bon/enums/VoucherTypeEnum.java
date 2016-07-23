package labs.himegami.bon.enums;

import labs.himegami.bon.R;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 12, 2016
 */
public enum VoucherTypeEnum {

    BUNDLE(0, R.color.bon_app_voucher_bundle, "Bundle"),
    COMPANION(1, R.color.bon_app_voucher_companion, "Companion"),
    DISCOUNT(2, R.color.bon_app_voucher_discount, "Discount"),
    UNLIMITED(3, R.color.bon_app_voucher_unlimited, "Unlimited");

    private int key;
    private int color;
    private String dscp;

    VoucherTypeEnum(int key, int color, String dscp) {
        this.key = key;
        this.color = color;
        this.dscp = dscp;
    }

    public static int findColorUsingKey(int key) {
        if (findVoucherTypeUsingKey(key) != null) {
            return findVoucherTypeUsingKey(key).getColor();
        }
        return 0;
    }

    public static VoucherTypeEnum findVoucherTypeUsingKey(int key) {
        for (VoucherTypeEnum item : VoucherTypeEnum.values()) {
            if (item.getKey() == key) {
                return item;
            }
        }
        return null;
    }

    public int getKey() {
        return key;
    }

    public int getColor() {
        return color;
    }

    public String getDscp() {
        return dscp;
    }


}
