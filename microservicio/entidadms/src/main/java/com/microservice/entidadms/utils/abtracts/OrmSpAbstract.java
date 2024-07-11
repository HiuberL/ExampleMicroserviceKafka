package com.microservice.entidadms.utils.abtracts;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.microservice.entidadms.configurations.DataSourceManager;
import com.microservice.entidadms.dto.ResponseDb;
import com.microservice.entidadms.utils.annotations.StoreProcedure;
import com.microservice.entidadms.utils.annotations.StoreProcedureParam;

@Repository
public abstract class OrmSpAbstract{

	@Autowired
	DataSourceManager dataSource;

	public ResponseDb executeProcedure(Object procedureValue, boolean expectRow, int type)
			throws SQLException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		StoreProcedure storeProcedureAnnotation = procedureValue.getClass()
				.<StoreProcedure>getAnnotation(StoreProcedure.class);
		Field[] fields = procedureValue.getClass().getDeclaredFields();
		Connection connection = dataSource.getConnection();
		ResultSet resulSet = null;

		List<Integer> output = new ArrayList<>();
		ResponseDb response = new ResponseDb();
		try (CallableStatement cstmt = connection.prepareCall(formater(storeProcedureAnnotation,fields.length,type),
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
			int pos = 1;
			for (Field field : fields) {
				StoreProcedureParam paramAnnotation = field
						.<StoreProcedureParam>getAnnotation(StoreProcedureParam.class);
				Method getter = procedureValue.getClass().getMethod(
					"get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1));

				Object value = getter.invoke(procedureValue);
				if (value == null){
					value = paramAnnotation.defaultValue();
				}

				if (paramAnnotation.isOut()) {
					cstmt.setObject(pos, value, paramAnnotation.type());
					cstmt.registerOutParameter(pos, paramAnnotation.type());
					output.add(pos);
				} else {
					cstmt.setObject(pos, value, paramAnnotation.type());
				}
				pos = pos + 1;
			}
			if (expectRow) {
				resulSet = cstmt.executeQuery();
				response.setDataset(getMapResulset(resulSet));
			} else {
				cstmt.execute();
			}
			Iterator<Integer> it = output.iterator();
			while (it.hasNext()) {
					int value = it.next();
					StoreProcedureParam paramAnnotation = fields[value-1]
							.<StoreProcedureParam>getAnnotation(StoreProcedureParam.class);
					switch (paramAnnotation.type()){
						case Types.INTEGER:
							response.getOuts().add(cstmt.getInt(value));							
							break;
						case Types.VARCHAR:
							response.getOuts().add(cstmt.getString(value));							
							break;

						default:
							break;
					}
			}
		} catch (SQLException e) {
			connection.close();
			throw new SQLException(e.getMessage());
		} finally {
			connection.close();
		}
		return response;
	}

	private List<Map<String, Object>> getMapResulset(ResultSet resulSet) throws SQLException {
		List<Map<String, Object>> data = new ArrayList<>();
		ResultSetMetaData md = resulSet.getMetaData();
		Map<String, Object> row = null;
		int columns = md.getColumnCount();
		while (resulSet.next()) {
			row = new HashMap<>();
			for (int i = 1; i <= columns; i++) {
				row.put(md.getColumnLabel(i), resulSet.getObject(i));
			}
			data.add(row);
		}
		return data;
	}


	private String formater(StoreProcedure sp, int variables, int type) {
		if (type == 1){
			return formaterSp(sp, variables);
		}
		return formaterFun(sp, variables);
	}

	private String formaterSp(StoreProcedure sp, int variables) {
        StringBuilder caller = new StringBuilder();
        caller.append("call ");
        caller.append(sp.catalog()+"." + sp.schema() + "."+  sp.name() + "(");
        for (int i = 1; i <= variables; i++) {
            caller.append("?");
            if (i < variables)
                caller.append(",");
        }
        caller.append(")");
        return caller.toString();
    }

	private String formaterFun(StoreProcedure sp, int variables) {
        StringBuilder caller = new StringBuilder();
        caller.append("SELECT * FROM ");
        caller.append(sp.catalog()+"." + sp.schema() + "."+  sp.name() + "(");
        for (int i = 1; i <= variables; i++) {
            caller.append("?");
            if (i < variables)
                caller.append(",");
        }
        caller.append(")");
        return caller.toString();
    }

}
