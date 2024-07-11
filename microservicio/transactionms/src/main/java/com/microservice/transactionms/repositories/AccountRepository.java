package com.microservice.transactionms.repositories;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.microservice.transactionms.dao.CreateAccountSp;
import com.microservice.transactionms.dao.DeleteAccountSp;
import com.microservice.transactionms.dao.QueryAccountSP;
import com.microservice.transactionms.dao.UpdateAccountSp;
import com.microservice.transactionms.dto.ResponseDb;
import com.microservice.transactionms.dto.UpdateAccountRequest;
import com.microservice.transactionms.utils.abtracts.OrmSpAbstract;

@Repository
public class AccountRepository extends OrmSpAbstract {
    

    public ResponseDb createAccount(Integer codePerson,String type) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		CreateAccountSp createAccountSp = new CreateAccountSp(codePerson,type);
		return this.executeProcedure(createAccountSp, false,1);
	}

  public ResponseDb queryAccount(Integer code) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    QueryAccountSP queryAccount = new QueryAccountSP(code);
		return this.executeProcedure(queryAccount, true,2);
	}


  public ResponseDb updateAccount(Integer code, String account,UpdateAccountRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		UpdateAccountSp updateAccountSp = new UpdateAccountSp(code,
                                                        account,
                                                            request.getAvailableBalance(),
                                                            request.getStatus());
		return this.executeProcedure(updateAccountSp, false,1);
	}

  public ResponseDb deleteAccount(Integer code, String account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		DeleteAccountSp deleteAccountSp = new DeleteAccountSp(code,account);
		return this.executeProcedure(deleteAccountSp, false,1);
	}


}
