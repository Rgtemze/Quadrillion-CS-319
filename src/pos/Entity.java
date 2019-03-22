package pos;

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

    public boolean doPayment(){
        boolean result = PosService.getInstance().buy(purchaseInfo);

        if(result) {
            User.getInstance().addHint();
            updateDatabase();
        }
        return result;
    }
}
