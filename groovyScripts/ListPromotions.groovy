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
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.entity.condition.EntityFieldValue
import org.apache.ofbiz.entity.condition.EntityConditionList
import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.util.EntityUtil
import java.sql.Timestamp

filcontractorId = parameters.filcontractorId
filproductId = parameters.filproductId
filclientId = parameters.filclientId
filactiv = parameters.filactiv
filpromotionFrom = parameters.filpromotionFrom
filshowPromotions = parameters.filshowPromotions

List searchCond = []
if (filcontractorId) {
	searchCond.add(EntityCondition.makeCondition("contractorId", EntityOperator.EQUALS, filcontractorId))
}
if (filproductId) {
	searchCond.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, filproductId))
}
if (filclientId) {
	searchCond.add(EntityCondition.makeCondition("clientId", EntityOperator.EQUALS, filclientId))
}
if (filactiv.equals("Y")) {
	searchCond.add(EntityCondition.makeCondition("isValid",EntityOperator.EQUALS, "Y") )
}
if (filpromotionFrom) {
	searchCond.add(EntityCondition.makeCondition("sellinFrom", EntityOperator.GREATER_THAN_EQUAL_TO, Timestamp.valueOf(filpromotionFrom)))
}

promotionList = from("BorPromotionView").where(searchCond).cache(false).queryList()

promotionList = EntityUtil.orderBy(promotionList,  ["sellinFrom"])

List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()
if(filshowPromotions.equals("Y")){
	for (GenericValue entry: promotionList){
		Map<String,Object> e = new HashMap<String,Object>()
		e.put("promotionId",entry.get("promotionId"))
		e.put("productName",entry.get("productName"))
		e.put("clientName",entry.get("clientName"))
		e.put("sellinFrom",entry.get("sellinFrom"))
		e.put("sellinTo",entry.get("sellinTo"))
		e.put("selloutFrom",entry.get("selloutFrom"))
		e.put("selloutTo",entry.get("selloutTo"))
		e.put("discount",entry.get("discount"))
		e.put("isValid",entry.get("isValid"))
		e.put("dateIns",entry.get("dateIns"))
	
		hashMaps.add(e)
	}
}


context.listPromotions = hashMaps
