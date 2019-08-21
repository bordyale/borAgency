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

contractId = parameters.contractId


contractDetails = from("BorContractDetail").where("contractId",contractId).cache(false).queryList()

contractDetails = EntityUtil.orderBy(contractDetails,  ["contractdetailId"])

List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()
for (GenericValue entry: contractDetails){
	Map<String,Object> e = new HashMap<String,Object>()
	e.put("contractdetailId",entry.get("contractdetailId"))
	String contractDetailType = entry.get("contractDetailType")
	BigDecimal value = entry.get("value")
	BigDecimal refRevenue = entry.get("refRevenue")
	BigDecimal perc = BigDecimal.ZERO
	if (contractDetailType.equals("CONR_TYPE_PERC")){
		perc= value
	}else{
		perc = value.divide(refRevenue)
	}


	e.put("contractDetailType",contractDetailType)
	e.put("name",entry.get("name"))
	e.put("value",value)
	e.put("refRevenue",refRevenue)
	e.put("perc",perc)

	hashMaps.add(e)
}



context.listIt = hashMaps
