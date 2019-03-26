package pos;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import data.PurchaseInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PosService {

    private static PosService instance = new PosService();

    private PosService(){

    }

    public static PosService getInstance() {
        return instance;
    }
    public String buy(PurchaseInfo pInfo){
        Options options = new Options();
        options.setApiKey("sandbox-T1CUxk3s1eJowvyw2kzVp0nF93pm9BUq");
        options.setSecretKey("sandbox-RU1m7uOZHODivNNOnsVg6VcfGvxM4dHK");
        options.setBaseUrl("https://sandbox-api.iyzipay.com");

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId("123456789");
        request.setPrice(new BigDecimal("1"));
        request.setPaidPrice(new BigDecimal("1.2"));
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId("B67832");
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());

        Address shippingAddress = new Address();
        shippingAddress.setContactName("Jane Doe");
        shippingAddress.setCity("Istanbul");
        shippingAddress.setCountry("Turkey");
        shippingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        shippingAddress.setZipCode("34742");
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName("Jane Doe");
        billingAddress.setCity("Istanbul");
        billingAddress.setCountry("Turkey");
        billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        billingAddress.setZipCode("34742");
        request.setBillingAddress(billingAddress);

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(pInfo.getName() + " " + pInfo.getSurname());
        paymentCard.setCardNumber(pInfo.getCardNo());
        paymentCard.setExpireMonth(pInfo.getExpiryDate().split("\\.")[0]);
        paymentCard.setExpireYear(pInfo.getExpiryDate().split("\\.")[1]);
        paymentCard.setCvc("123");
        paymentCard.setRegisterCard(0);
        request.setPaymentCard(paymentCard);

        Buyer buyer = new Buyer();
        buyer.setId("BY789");
        buyer.setName("Malper");
        buyer.setSurname("Karadagli");
        buyer.setGsmNumber("+905350000000");
        buyer.setEmail("email@email.com");
        buyer.setIdentityNumber("74300864791");
        buyer.setLastLoginDate("2015-10-05 12:43:35");
        buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        buyer.setIp("85.34.78.112");
        buyer.setCity("Istanbul");
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        request.setBuyer(buyer);

        List<BasketItem> basketItems = new ArrayList<BasketItem>();
        BasketItem firstBasketItem = new BasketItem();
        firstBasketItem.setId("BI101");
        firstBasketItem.setName("Binocular");
        firstBasketItem.setCategory1("Collectibles");
        firstBasketItem.setCategory2("Accessories");
        firstBasketItem.setItemType(BasketItemType.PHYSICAL.name());
        firstBasketItem.setPrice(new BigDecimal("1"));
        basketItems.add(firstBasketItem);
        request.setBasketItems(basketItems);


        Payment payment = Payment.create(request, options);
        System.out.println(payment.getErrorMessage());
        System.out.println(payment.getStatus());

        return payment.getErrorMessage();
    }
}
