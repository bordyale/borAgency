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
import java.text.SimpleDateFormat
def sdf = new SimpleDateFormat("yyyy-MM-dd")

filcontractorId = parameters.filcontractorId
filclientId = parameters.filclientId
clientId = parameters.clientId
fildate1From = parameters.fildate1From
filshowClientInfos = parameters.filshowClientInfos

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
	def parseDate = sdf.parse(fildate1From)
	searchCond.add(EntityCondition.makeCondition("noteDateTime", EntityOperator.GREATER_THAN_EQUAL_TO, UtilDateTime.toTimestamp(parseDate)))
}
paramCond = EntityCondition.makeCondition(searchCond, EntityOperator.AND)

//Revenues - fatturati
List noteTypeCondList = []
if (filshowClientInfos) {
	noteTypeCondList.add(EntityCondition.makeCondition("noteType", EntityOperator.EQUALS, "NOTE_FATTURATO"))
}
noteTypeCond = EntityCondition.makeCondition(noteTypeCondList, EntityOperator.AND)

combinedPaymentCond = EntityCondition.makeCondition([noteTypeCond, paramCond], EntityOperator.AND)

pricecheckList = from("BorNoteView").where(combinedPaymentCond).cache(false).orderBy("noteDateTime DESC").queryList()


List<HashMap<String,Object>> hashMaps = new ArrayList<HashMap<String,Object>>()
if(filshowClientInfos.equals("Y")){
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

//metodo pagamento
noteTypeCondList = []
if (filshowClientInfos) {
	noteTypeCondList.add(EntityCondition.makeCondition("noteType", EntityOperator.EQUALS, "NOTE_METODPAG"))
}
noteTypeCond = EntityCondition.makeCondition(noteTypeCondList, EntityOperator.AND)

combinedPaymentCond = EntityCondition.makeCondition([noteTypeCond, paramCond], EntityOperator.AND)

pricecheckList = from("BorNoteView").where(combinedPaymentCond).cache(false).orderBy("noteDateTime DESC").queryList()


List<HashMap<String,Object>> hashMaps3 = new ArrayList<HashMap<String,Object>>()
if(filshowClientInfos.equals("Y")){
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

		hashMaps3.add(e)
	}
}

//gruppo acq
noteTypeCondList = []
if (filshowClientInfos) {
	noteTypeCondList.add(EntityCondition.makeCondition("noteType", EntityOperator.EQUALS, "NOTE_GRUPACQ"))
}
noteTypeCond = EntityCondition.makeCondition(noteTypeCondList, EntityOperator.AND)

combinedPaymentCond = EntityCondition.makeCondition([noteTypeCond, paramCond], EntityOperator.AND)

pricecheckList = from("BorNoteView").where(combinedPaymentCond).cache(false).orderBy("noteDateTime DESC").queryList()


List<HashMap<String,Object>> hashMaps4 = new ArrayList<HashMap<String,Object>>()
if(filshowClientInfos.equals("Y")){
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

		hashMaps4.add(e)
	}
}


//Contacts
searchCond = []
if (filclientId) {
	searchCond.add(EntityCondition.makeCondition("clientId", EntityOperator.EQUALS, filclientId))
}
if (clientId) {
	searchCond.add(EntityCondition.makeCondition("clientId", EntityOperator.EQUALS, clientId))
}
pricecheckList = from("BorContact").where(searchCond).cache(false).queryList()

List<HashMap<String,Object>> hashMaps2 = new ArrayList<HashMap<String,Object>>()
if(filshowClientInfos.equals("Y")){
	for (GenericValue entry: pricecheckList){
		Map<String,Object> e = new HashMap<String,Object>()
		e.put("clientId",entry.get("clientId"))
		e.put("contactId",entry.get("contactId"))
		e.put("name",entry.get("name"))
		e.put("title",entry.get("title"))
		e.put("telefon",entry.get("telefon"))
		e.put("email",entry.get("email"))

		hashMaps2.add(e)
	}
}

//context.fildate1FromTitle ="xxx"
context.listClientRevenues = hashMaps
context.listClientContacts = hashMaps2
context.listClientPayCond = hashMaps3
context.listClientBuyGroup = hashMaps4
