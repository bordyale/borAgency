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
filclientId = parameters.filclientId
clientId = parameters.clientId
fildate1From = parameters.fildate1From
filshowClientNotes = parameters.filshowClientNotes


List searchCond = []
if (filcontractorId) {
	searchCond.add(EntityCondition.makeCondition("contractorId", EntityOperator.EQUALS, filcontractorId))
}
if (filclientId) {
	searchCond.add(EntityCondition.makeCondition("clientId", EntityOperator.EQUALS, filclientId))
}
if (clientId) {
	searchCond.add(EntityCondition.makeCondition("clientId", EntityOperator.EQUALS, clientId))
}
if (fildate1From) {
	searchCond.add(EntityCondition.makeCondition("noteDateTime", EntityOperator.GREATER_THAN_EQUAL_TO, Timestamp.valueOf(fildate1From)))
}
if (reportPage=="Y") {
	searchCond.add(EntityCondition.makeCondition("noteType", EntityOperator.EQUALS, "NOTE_STANDARD"))
}

pricecheckList = from("BorNoteView").where(searchCond).cache(false).orderBy("noteDateTime DESC").queryList()


List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()
if(filshowClientNotes.equals("Y")){
	for (GenericValue entry: pricecheckList){
		Map<String,Object> e = new HashMap<String,Object>()
		e.put("noteId",entry.get("noteId"))
		e.put("contractorName",entry.get("contractorName"))
		e.put("noteDateTime",entry.get("noteDateTime"))
		e.put("clientId",entry.get("clientId"))
		e.put("clientName",entry.get("clientName"))
		e.put("noteType",entry.get("noteType"))
		e.put("noteName",entry.get("noteName"))
		e.put("noteInfo",entry.get("noteInfo"))
	
		hashMaps.add(e)
	}
}


//context.fildate1FromTitle ="xxx"
context.listClientNotes = hashMaps
