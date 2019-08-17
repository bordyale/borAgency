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

List searchCond = []
if (filcontractorId) {
	searchCond.add(EntityCondition.makeCondition("contractorId", EntityOperator.EQUALS, filcontractorId))
}
if (filproductId) {
	searchCond.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, filproductId))
}

conditionsList = select("conditionId","contractorName","clientName","pricelistName","productName","price","startingPrice","sc1","sc2","sc3","sc4","sc5").from("ConditionView").where(searchCond).cache(false).queryList()

conditionsList = EntityUtil.orderBy(conditionsList,  ["productId"])

List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()
for (GenericValue entry: conditionsList){
	Map<String,Object> e = new HashMap<String,Object>()
	e.put("conditionId",entry.get("conditionId"))
	e.put("contractorName",entry.get("contractorName"))
	e.put("clientName",entry.get("clientName"))
	e.put("pricelistName",entry.get("pricelistName"))
	e.put("productName",entry.get("productName"))
	e.put("price",entry.get("price"))
	BigDecimal startingPrice = entry.get("startingPrice")
	e.put("startingPrice",startingPrice)
	BigDecimal resultPrice = startingPrice ==null ? entry.get("price") : startingPrice
	if (resultPrice == null) {
		resultPrice = BigDecimal.ZERO
	}
	BigDecimal sc1 = entry.get("sc1")==null ? BigDecimal.ZERO : entry.get("sc1")
	BigDecimal sc2 = entry.get("sc2")==null ? BigDecimal.ZERO : entry.get("sc2")
	BigDecimal sc3 = entry.get("sc3")==null ? BigDecimal.ZERO : entry.get("sc3")
	BigDecimal sc4 = entry.get("sc4")==null ? BigDecimal.ZERO : entry.get("sc4")
	BigDecimal sc5 = entry.get("sc5")==null ? BigDecimal.ZERO : entry.get("sc5")
	e.put("sc1",sc1)
	e.put("sc2",sc2)
	e.put("sc3",sc3)
	e.put("sc4",sc4)
	e.put("sc5",sc5)
	resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc1.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc1.divide(new BigDecimal(100))))
	resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc2.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc2.divide(new BigDecimal(100))))
	resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc3.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc3.divide(new BigDecimal(100))))
	resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc4.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc4.divide(new BigDecimal(100))))
	resultPrice = resultPrice.multiply(new BigDecimal(1).subtract(sc5.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ZERO : sc5.divide(new BigDecimal(100))))
	e.put("resultPrice",resultPrice)
	hashMaps.add(e)
}



context.listIt = hashMaps
