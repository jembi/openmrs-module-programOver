/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.programOver.web.controller;

/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.programOver.advice.UsageStatsUtils;
import org.openmrs.module.programOver.service.ProgramOverviewService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.NMTOKENDatatypeValidator;

import sun.print.resources.serviceui;

/**
 *
 */
public class PatientsDetailsOverFormController extends ParameterizableViewController {
	
	@SuppressWarnings("unchecked")
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		int programId = Integer.parseInt(request.getParameter("programId"));
		Map<String, Integer> patientNumbers = new HashMap<String, Integer>();
		ProgramCriteraList programCriteriaList = new ProgramCriteraList();
		
		String startDate =request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String gender = request.getParameter("gender");
		String maxAge = request.getParameter("maxAge");
		String minAge = request.getParameter("minAge");
		
		//Map<String, Integer> patientNumbers = new HashMap<String, Integer>();
		//List<Patient> allPatients = new ArrayList<Patient>();
		
		String[] criteriaWithParameters = programCriteriaList.getAllCriteriaBasedOnProgram(programId);
		String programIdKey = request.getParameter("checkType");
		List<Object> nameOfeventDate = new ArrayList<Object>();
		List<Object> returnVisitDates= new ArrayList<Object>();
		List<Object> consultationDates= new ArrayList<Object>();
		List<Object> drugTitles= new ArrayList<Object>();
		List<Object[]> listPatientHistory = (List<Object[]>) request.getSession().getAttribute(
		    "patientSize_" + (Integer.parseInt(request.getParameter("lineNumber")) - 1));
		Object eventDate = null;
		Object eventDate1= null;
		Object regimenTitle=null;
		Object consultatioDatetitle=null;
		
		
		
//		int i = 0;
//		ProgramPatientsController pc = new ProgramPatientsController();
//		
//
//		
//		for (String key : criteriaWithParameters) {
//			
//			if (programIdKey.equals(key)) {
//				
//				
//				patientNumbers.put(key, listPatientHistory.size());
//				
//				model.put("patientNumbers", patientNumbers);
//				request.getSession().setAttribute("patientSize_" + i, listPatientHistory);
//				i++;
//			}
//			
//		}
//		
		
		
		for (Object[] objects : listPatientHistory) {
			//the object defined below holds the title of added columns
			if (objects.length >3 && objects.length<=5) {
				nameOfeventDate.add(objects[2]);
				returnVisitDates.add(objects[4]);
				eventDate = nameOfeventDate.get(0);
				eventDate1=returnVisitDates.get(0);				
				
				
			}
			if (objects.length ==3) {
				nameOfeventDate.add(objects[2]);				
				eventDate = nameOfeventDate.get(0);
	            
            }
			if (objects.length >5) {
				nameOfeventDate.add(objects[2]);
				returnVisitDates.add(objects[4]);
				eventDate = nameOfeventDate.get(0);
				eventDate1=returnVisitDates.get(0);				
				consultationDates.add(objects[6]);
				consultatioDatetitle=consultationDates.get(0);
				
            }
			
		}
		model.put("lineNumber", request.getParameter("lineNumber"));
		model.put("consultationDateTitle", consultatioDatetitle);
		model.put("regimenTitle", regimenTitle);
		model.put("eventDate1", eventDate1);
		model.put("eventDate", eventDate);
		model.put("patients", listPatientHistory);
		for (Object[] objects : listPatientHistory) {
		Patient p=	(Patient) objects[0];
		//System.out.println(">>>>>>>>patient ident"+p.getPatientIdentifier().getIdentifier().toString());
		
	        
        }
		model.put("programId", programId);
		model.put("criteriaSet", criteriaWithParameters);
		
		model.put("programIdKey", programIdKey);
		model.put("checkType", programIdKey);
		model.put("startDate", startDate);
		model.put("endDate", endDate);
		model.put("maxAge", maxAge);
		model.put("minAge", minAge);
		model.put("gender", gender);
		
		
		return new ModelAndView(getViewName(), model);
	
		
	}
	
}
