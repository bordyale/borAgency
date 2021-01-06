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
import java.text.SimpleDateFormat
import org.apache.ofbiz.base.util.UtilDateTime

def sdf = new SimpleDateFormat("yyyy-MM-dd")

filcontractorId = parameters.filcontractorId
filproductId = parameters.filproductId
filclientId = parameters.filclientId
fildate1From = parameters.fildate1From
fildate2From = parameters.fildate2From


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

if (fildate1From) {
	def parseDate = sdf.parse(fildate1From)
	searchCond.add(EntityCondition.makeCondition("date", EntityOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.toTimestamp(parseDate)))
}
if (fildate2From) {
	def parseDate = sdf.parse(fildate2From)
	searchCond.add(EntityCondition.makeCondition("date", EntityOperator.LESS_THAN_EQUAL_TO, UtilDateTime.toTimestamp(parseDate)))
}

pricecheckList = from("BorPriceCheckView").where(searchCond).cache(false).queryList()

pricecheckList = EntityUtil.orderBy(pricecheckList,  ["date"])

List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()
for (GenericValue entry: pricecheckList){
	Map<String,Object> e = new HashMap<String,Object>()
	e.put("pricecheckId",entry.get("pricecheckId"))
	e.put("prodCode",entry.get("prodCode"))
	e.put("productName",entry.get("productName"))
	e.put("clientName",entry.get("clientName"))
	e.put("clientshopName",entry.get("clientshopName"))
	e.put("date",entry.get("date"))
	e.put("price",entry.get("price"))

	hashMaps.add(e)
}



context.listPriceChecks = hashMaps
