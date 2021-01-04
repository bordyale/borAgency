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
import org.apache.ofbiz.base.util.UtilDateTime

filcontractorId = parameters.filcontractorId
filproductId = parameters.filproductId
filclientId = parameters.filclientId
filname = parameters.name
filactiv = parameters.filactiv
sortField = parameters.sortField

List filCond = []
List activCond = []
if (filcontractorId) {
	filCond.add(EntityCondition.makeCondition("contractorId", EntityOperator.EQUALS, filcontractorId))
}
if (filproductId) {
	filCond.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, filproductId))
}
if (filclientId) {
	filCond.add(EntityCondition.makeCondition("clientId", EntityOperator.EQUALS, filclientId))
}
if (filname) {
	filCond.add(EntityCondition.makeCondition("name", EntityOperator.LIKE, filname + "%"))
}
if (filactiv.equals("Y")) {
	activCond.add(EntityCondition.makeCondition("validTo",EntityOperator.EQUALS, null) )
	activCond.add(EntityCondition.makeCondition("validTo", EntityOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.nowTimestamp()) )
	
}
activCondOR = EntityCondition.makeCondition(activCond,EntityOperator.OR)
filCondAND = EntityCondition.makeCondition(filCond, EntityOperator.AND)
searchCond = EntityCondition.makeCondition([filCondAND, activCondOR], EntityOperator.AND)


if (!sortField) {
	sortField="contractId"
}

pricecheckList = from("ContractContractorView").where(searchCond).orderBy(sortField).queryList()


List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()
for (GenericValue entry: pricecheckList){
	Map<String,Object> e = new HashMap<String,Object>()
	e.put("contractId",entry.get("contractId"))
	e.put("contractorName",entry.get("contractorName"))
	e.put("name",entry.get("name"))
	e.put("validFrom",entry.get("validFrom"))
	e.put("validTo",entry.get("validTo"))

	hashMaps.add(e)
}



context.listContracts = hashMaps
