package com.rslakra.springbootsamples.emailservice.utils;

import com.rslakra.springbootsamples.emailservice.Constants;
import com.rslakra.springbootsamples.emailservice.domain.order.DeliveryDetails;
import com.rslakra.springbootsamples.emailservice.domain.order.OrderDetails;
import com.rslakra.springbootsamples.emailservice.domain.order.OrderedProductInfo;
import com.rslakra.springbootsamples.emailservice.domain.order.ShippingDetails;
import com.rslakra.springbootsamples.emailservice.domain.payment.PaymentDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ValidationException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;

/**
 * @author Rohtash Lakra
 * @created 8/12/21 3:18 PM
 */
public class AppUtils {
    
    public static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final String ALGO = "AES";
    private static final byte[] KEY_VALUE =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    
    /**
     * create the salt with combination of random id and some salt string constants
     *
     * @return
     */
    public static String getSalt() {
        //String encryptsalt = BCrypt.hashpw(password, Constants.SALT_RAW_STRING);
        String salt = Constants.getSalt(Constants.getUniqueId());
        salt = new BCryptPasswordEncoder().encode(salt);
        return salt;
    }
    
    /**
     * Encrypt the password using salt
     *
     * @param salt
     * @param password
     * @return
     */
    public static String getEncryptedPassword(String salt, String password) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        password = password + salt;
        return bcrypt.encode(password);
    }
    
    /**
     * Checks the login password is same or not
     *
     * @param salt
     * @param raw_password
     * @param db_password
     * @return
     */
    public static boolean isValidPassword(String salt, String raw_password, String db_password) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        raw_password = raw_password + salt;
        return bcrypt.matches(raw_password, db_password);
    }
    
    /**
     * Get and trim a request parameter value, returning empty string if null.
     * This is a convenience method to avoid repeating the null check and trim pattern.
     *
     * @param request the HttpServletRequest
     * @param paramName the parameter name
     * @return the trimmed parameter value, or empty string if null
     */
    public static String getParameter(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return value != null ? value.trim() : "";
    }
    
    /**
     * encode the String to its corresponding HTML
     * Uses SecurityUtils which replaces ESAPI functionality
     *
     * @param string
     * @return
     */
    public static String encodeForHTML(String string) {
        return SecurityUtils.encodeForHTML(string);
    }
    
    /**
     * Check for valid Alphanumeric String (e.g Rohtash1)
     * Uses SecurityUtils which replaces ESAPI functionality
     *
     * @param context (kept for compatibility, not used)
     * @param param the input to validate
     * @param regExp (kept for compatibility, uses standard validation)
     * @param maxLength maximum allowed length
     * @param allowNull whether null values are allowed
     * @return true if valid
     * @throws ValidationException
     */
    public static boolean isValidInput(String context, String param, String regExp, int maxLength, boolean allowNull)
            throws ValidationException {
        if (param == null) {
            return allowNull;
        }
        // Use SecurityUtils for validation
        return SecurityUtils.isSafeInput(param, maxLength);
    }
    
    /**
     * Check for valid Integer value
     * Uses SecurityUtils which replaces ESAPI functionality
     *
     * @param context (kept for compatibility, not used)
     * @param param the integer string to validate
     * @param minValue minimum allowed value
     * @param maxValue maximum allowed value
     * @param allowNull whether null values are allowed
     * @return true if valid
     * @throws ValidationException
     */
    public static boolean isValidInteger(String context, String param, int minValue, int maxValue, boolean allowNull)
            throws ValidationException {
        if (param == null) {
            return allowNull;
        }
        return SecurityUtils.isValidInteger(param, minValue, maxValue);
    }
    
    public static boolean isValidPrice(final String value) {
        return value.matches("^[1-9]\\d*(\\.\\d{1,2})?$");
    }
    
    
    /**
     * Validate the product's quantity in the order
     * Uses SecurityUtils which replaces ESAPI functionality
     */
    public static boolean isValidQuantity(String quantity) {
        if (quantity == null || quantity.trim().isEmpty()) {
            return false;
        }
        return SecurityUtils.isValidInteger(quantity.trim(), 1, 50);
    }
    
    /**
     * Validate the product's quantity in the order
     */
    public static boolean isValidQuantity(int quantity) {
        return isValidQuantity(quantity + "");
    }
    
    /**
     * returns the orderDetails object stored in session.
     *
     * @param session
     * @return
     */
    public static OrderDetails getOrderDetailsFromSession(HttpSession session) {
        return (OrderDetails) session.getAttribute("orderDetails");
    }
    
    /**
     * Verify the valid quantity of products in order.
     *
     * @param orderDetails
     * @return
     */
    public static boolean isOrderHasProduct(OrderDetails orderDetails) {
        if (orderDetails.getOrderedProducts().size() < 1) {
            return false;
        }
        return true;
    }
    
    /**
     * Generate a 9 digit unique number used as order id
     *
     * @return
     */
    public static long getGeneratedOrderId() {
        long timeSeed = System.nanoTime(); // to get the current date time value
        double randSeed = Math.random() * 1000; // random number generation
        long midSeed = (long) (timeSeed * randSeed); // mixing up the time and rand number.
        String s = midSeed + "";
        String subStr = s.substring(0, 9);
        long finalOrderId = Long.parseLong(subStr);    // integer value
        return finalOrderId;
    }
    
    /**
     * Encrypt the data like credit card using AES
     *
     * @param Data
     */
    public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.getEncoder().encodeToString(encVal);
        return encryptedValue;
    }
    
    /**
     * Decrypt the data like credit card
     *
     * @param encryptedData
     */
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    
    /**
     * Change the credit card number as standard view like *****1234, show only last 4 digit.
     *
     * @param cardNumber
     */
    public static String getCardNumberToView(String cardNumber) {
        int length = cardNumber.length();
        int nonViewableChar = length - 4;
        String viewableNumbers = cardNumber.substring(nonViewableChar);
        StringBuilder card = new StringBuilder();
        for (int i = 1; i <= nonViewableChar; i++) {
            card.append("*");
        }
        card.append(viewableNumbers);
        return card.toString();
    }
    
    /**
     * Generate private key for encryption and decryption.
     *
     * @return
     * @throws Exception
     */
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(KEY_VALUE, ALGO);
        return key;
    }
    
    /**
     * Encoded all ordered product information in HTML
     *
     * @param listOfProductInfo
     * @return
     */
    public static ArrayList<OrderedProductInfo> getHTMLEncodedOrderedProductsInfo(
            ArrayList<OrderedProductInfo> listOfProductInfo) {
        ArrayList<OrderedProductInfo> encodedList = new ArrayList<OrderedProductInfo>();
        //OrderedProductInfo orderedProduct = null;
        for (OrderedProductInfo orderedProductInfo : listOfProductInfo) {
            orderedProductInfo.setColor(encodeForHTML(orderedProductInfo.getColor()));
            orderedProductInfo.setProductName(encodeForHTML(orderedProductInfo.getProductName()));
            encodedList.add(orderedProductInfo);
        }
        return encodedList;
    }
    
    /**
     * Encoded Payment details information in HTML
     *
     * @param paymentDetails
     * @return
     */
    public static PaymentDetails getHTMLEncodedPaymentDetails(PaymentDetails paymentDetails) {
        paymentDetails.setCardNumber(encodeForHTML(paymentDetails.getCardNumber()));
        //paymentDetails.setCardOwner(ESAPI.encoder().encodeForHTML(paymentDetails.getCardOwner()));
        //paymentDetails.setCardType(ESAPI.encoder().encodeForHTML(paymentDetails.getCardType()));
        //paymentDetails.setTotalAmount(ESAPI.encoder().encodeForHTML(paymentDetails.getTotalAmount()));
        return paymentDetails;
    }
    
    /**
     * Encoded Delivery details information in HTML
     *
     * @param deliveryDetails
     * @return
     */
    public static DeliveryDetails getHTMLEncodedDeliveryDetails(DeliveryDetails deliveryDetails) {
        deliveryDetails.setCity(encodeForHTML(deliveryDetails.getCity()));
        deliveryDetails.setCountry(encodeForHTML(deliveryDetails.getCountry()));
        deliveryDetails.setHouseNumber(encodeForHTML(deliveryDetails.getHouseNumber()));
        deliveryDetails.setState(encodeForHTML(deliveryDetails.getState()));
        deliveryDetails.setStreet(encodeForHTML(deliveryDetails.getStreet()));
        return deliveryDetails;
    }
    
    /**
     * Encoded Shipping details information in HTML
     *
     * @param shippingDetails
     * @return
     */
    public static ShippingDetails getHTMLEncodedShippingDetails(ShippingDetails shippingDetails) {
        shippingDetails.setType(encodeForHTML(shippingDetails.getType()));
        return shippingDetails;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String password1 = getEncryptedPassword("abcd", "admin");
        System.out.println("password is same :" + bcrypt.matches("admin2", password1));
        System.out.println("password :" + getEncryptedPassword("abcd", "admin"));
        System.out.println("password :" + bcrypt.hashCode());
    }
}
