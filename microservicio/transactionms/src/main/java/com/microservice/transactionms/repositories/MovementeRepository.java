package com.microservice.transactionms.repositories;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.microservice.transactionms.dao.CreateMovementSp;
import com.microservice.transactionms.dao.DeleteMovementSp;
import com.microservice.transactionms.dao.QueryMovementSP;
import com.microservice.transactionms.dto.CreateMovementRequest;
import com.microservice.transactionms.dto.ResponseDb;
import com.microservice.transactionms.utils.abtracts.OrmSpAbstract;

@Repository
public class MovementeRepository extends OrmSpAbstract {
    

    public ResponseDb createMovement(CreateMovementRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		CreateMovementSp createMovementSp = new CreateMovementSp(request.getCodeClient(),
																request.getAccount(),
																request.getValue(),
																request.getType(),
																BigInteger.valueOf(0));
		return this.executeProcedure(createMovementSp, false,1);
	}


	
    public ResponseDb deleteMovement(Long sequence) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		DeleteMovementSp deleteMovementSp = new DeleteMovementSp(BigInteger.valueOf(sequence));
		return this.executeProcedure(deleteMovementSp, false,1);
	}

    public ResponseDb queryMovement(Integer accountCode, String initDate, String endDate) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		QueryMovementSP queryMovementSP = new QueryMovementSP(accountCode,
																initDate,
																endDate);
		return this.executeProcedure(queryMovementSP, true,2);
	}
}
