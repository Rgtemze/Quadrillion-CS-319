package pos;

import com.iyzipay.model.Status;
import data.PurchaseInfo;
import data.User;
import database.DatabaseConnection;
import ui.Page;

public class Entity {
    private PurchaseInfo purchaseInfo;

    public Entity(PurchaseInfo purchaseInfo){
        this.purchaseInfo = purchaseInfo;
    }

    private void updateDatabase(){
        DatabaseConnection db = DatabaseConnection.getInstance();
        db.updateHint(purchaseInfo.getUser());
    }

    public String doPayment(){
        String result = PosService.getInstance().buy(purchaseInfo);
        if(result == null) {
            purchaseInfo.getUser().addHint();
            updateDatabase();
        }
        return result;
    }
}
