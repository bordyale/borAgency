/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.ofbiz.entity.condition.EntityExpr
import org.apache.ofbiz.entity.condition.EntityFunction
import org.apache.ofbiz.entity.condition.EntityFieldValue
import org.apache.ofbiz.entity.condition.EntityConditionList
import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.util.EntityUtil
import org.apache.ofbiz.entity.condition.EntityOperator
import java.sql.Timestamp




conditionBoughtNot = from("ConditionViewisBoughtNot").queryList()

clientsBoughtSetNot = []
conditionBoughtNot.each { entry ->
	clientsBoughtSetNot.add(entry.clientId)
}



baseExprs = []
baseExprs.add(EntityCondition.makeCondition("isProductBought", EntityOperator.EQUALS, "Y"))
conditionBoughtYes = from("ConditionView").where(baseExprs).cache(false).queryList()

clientsBoughtSetYes = []
conditionBoughtYes.each { entry ->
	clientsBoughtSetYes.add(entry.clientId)
}


//baseExprs = []
baseExprs =EntityCondition.makeCondition([EntityCondition.makeCondition("clientId", EntityOperator.IN, clientsBoughtSetNot),EntityCondition.makeCondition("clientId", EntityOperator.NOT_IN, clientsBoughtSetYes)],EntityOperator.AND)
//baseExprs.add(EntityCondition.makeCondition("clientId", EntityOperator.NOT_IN, clientsBoughtSetYes))
//searchAND = EntityCondition.makeCondition(baseExprs, EntityOperator.AND)
listClientsNotBought = select("contractorId","contractorName","clientId","clientName").from("ConditionView").where(baseExprs).distinct().cache(false).queryList()







List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()


for (GenericValue entry: listClientsNotBought){
	Map<String,Object> e = new HashMap<String,Object>()

	e.put("contractorId",entry.get("contractorId"))
	e.put("contractorName",entry.get("contractorName"))
	e.put("clientId",entry.get("clientId"))
	e.put("clientName",entry.get("clientName"))

	hashMaps.add(e)
}




context.listClientsNotBought = hashMaps
