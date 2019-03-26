package pos;

import com.iyzipay.model.Status;
import data.PurchaseInfo;
import data.User;
import database.DatabaseConnection;

public class Entity {
    private PurchaseInfo purchaseInfo;

    public Entity(PurchaseInfo purchaseInfo){
        this.purchaseInfo = purchaseInfo;
    }

    private void updateDatabase(){
        DatabaseConnection db = DatabaseConnection.getInstance();
        User user = User.getInstance();
        db.executeSQL(String.format("UPDATE users " +
                "SET HINT = '%d' " +
                "WHERE NICKNAME = '%s'; ", user.getHint(), user.getNickName()));
    }

    public String doPayment(){
        String result = PosService.getInstance().buy(purchaseInfo);

        if(result == null) {
            User.getInstance().addHint();
            updateDatabase();
        }
        return result;
    }
}
