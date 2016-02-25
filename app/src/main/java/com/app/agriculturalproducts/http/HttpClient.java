package com.app.agriculturalproducts.http;

import android.util.Log;

import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.OtherRecord;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.util.Configure;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by aa on 2016/2/24.
 */
public class HttpClient {
    private LiteHttp liteHttp;
    private static HttpClient single=null;
    public EmployeeInfo employeeInfo;
    public List<Field> fieldList;
    public List<PlanterRecord> planterList;
    public List<FertilizerRecord> fertiList;
    public List<PreventionRecord> preventionList;
    public List<PickRecord> pickList;
    public List<OtherRecord> otherList;
    //public List

    private HttpClient(){
        liteHttp = LiteHttp.newApacheHttpClient(null);
    };
    public static HttpClient getInstance() {
        if (single == null) {
            single = new HttpClient();
        }
        return single;
    }

    public void checkLogin(HttpListener<String> listener,String name ,String pwd){
        if(listener == null || name == null || pwd == null) return;
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username",name);
            object.put("userinfo_password",pwd);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String,String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        liteHttp.executeAsync(new StringRequest(Configure.LOGIN_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString())).setHttpListener(listener));
    }

    public void infoRequestAsync(HttpListener<String> listener,String url,String name){
        if(listener == null || name == null ) return;
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username",name);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String,String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        liteHttp.executeAsync(new StringRequest(url).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString())).setHttpListener(listener));
    }

    public void getAllInfo(String name){
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username",name);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String,String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.GET_EMPLOYEE_BY_USERNAME_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));

        Response<String>  result = liteHttp.execute(stringRequest);
        employeeInfo = parseEmployeeInfo(result.getResult());
//        Log.e("testcc", result.getResult());
        employeeInfo.printfInfo();

        stringRequest.setUri(Configure.GET_FIELD_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        fieldList = parseField(result.getResult());
//        Log.e("testcc", result.getResult() + fieldList.size());
//        for(int i=0;i<fieldList.size();i++){
//            fieldList.get(i).printfInfo();
//        }

        stringRequest.setUri(Configure.GET_PLANTRECORD_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        planterList = parsePlant(result.getResult());
//        Log.e("testcc", result.getResult()+planterList.size());
//        for(int i=0;i<planterList.size();i++){
//            planterList.get(i).printfInfo();
//        }

        stringRequest.setUri(Configure.GET_FERTILIZERECORD_BY_USERNAME_URL );
        result = liteHttp.execute(stringRequest);
        fertiList = parseFertilizer(result.getResult());
//        Log.e("testcc", result.getResult()+fertiList.size());
//        for(int i=0;i<fertiList.size();i++){
//            fertiList.get(i).printfInfo();
//        }


        stringRequest.setUri(Configure.GET_PREVENTIONRECORD_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        preventionList = parsePrevention(result.getResult());
//        Log.e("testcc", result.getResult()+preventionList.size());
//        for(int i=0;i<preventionList.size();i++){
//            preventionList.get(i).printfInfo();
//        }

        stringRequest.setUri(Configure. GET_PICKRECORD_BY_USERNAME_URL );
        result = liteHttp.execute(stringRequest);
        pickList = parsePick(result.getResult());
//        Log.e("testcc", result.getResult()+pickList.size());
//        for(int i=0;i<pickList.size();i++){
//            pickList.get(i).printfInfo();
//        }

        stringRequest.setUri(Configure. GET_OTHERRECORD_BY_USERNAME_URL );
        result = liteHttp.execute(stringRequest);
        otherList = parseOther(result.getResult());
        Log.e("testcc", result.getResult());
    }

    public EmployeeInfo parseEmployeeInfo(String val){
        try {
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if(result.equals("success")){
                EmployeeInfo employeeInfo = new EmployeeInfo();
                String value = (String) object.get("employee_id");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setEmployee_id(value);
                }

                value = (String) object.get("member_id");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setMember_id(value);
                }

                value = (String) object.get("employee_name");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setEmployee_name(value);
                }

                value = (String) object.get("employee_sex");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setEmployee_sex(value);
                }

                value = (String) object.get("employee_birthday");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setEmployee_birthday(value);
                }

                value = (String) object.get("employee_tel");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setEmployee_tel(value);
                }

                value = (String) object.get("employee_address");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setEmployee_address(value);
                }

                value = (String) object.get("employee_passport");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setEmployee_passport(value);
                }

                value = (String) object.get("employee_type");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setEmployee_type(value);
                }

                value = (String) object.get("member_name");
                if(!(value.equals("null") && value !=null)){
                    employeeInfo.setMember_name(value);
                }
                return employeeInfo;
            }
        } catch (JSONException e) {
           // e.printStackTrace();
        }
        return null;
    }

    public List<Field> parseField(String val){
        try {
            List<Field> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if(result.equals("success")){
                JSONArray array = object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    JSONObject jobject = (JSONObject) array.get(i);

                    Field field = new Field();
                    String value = (String) jobject.get("field_id");
                    if(!(value.equals("null") && value !=null)){
                        field.setField_id(value);
                    }

                    value = (String) jobject.get("employee_id");
                    if(!(value.equals("null") && value !=null)){
                        field.setEmployee_id(value);
                    }
                    value = (String) jobject.get("field_name");
                    if(!(value.equals("null") && value !=null)){
                        field.setField_name(value);
                    }
                    value = (String) jobject.get("field_type");
                    if(!(value.equals("null") && value !=null)){
                        field.setField_type(value);
                    }
                    value = (String) jobject.get("field_area");
                    if(!(value.equals("null") && value !=null)){
                        field.setField_area(value);
                    }
                    value = (String) jobject.get("field_status");
                    if(!(value.equals("null") && value !=null)){
                        field.setField_status(value);
                    }
                    ls.add(field);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<PlanterRecord> parsePlant(String val){
        try {
            List<PlanterRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if(result.equals("success")){
                JSONArray array = object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    JSONObject jobject = (JSONObject) array.get(i);

                    PlanterRecord plant = new PlanterRecord();
                    String value = (String) jobject.get("plantrecord_id");
                    if(!(value.equals("null") && value !=null)){
                        plant.setPlantrecord_id(value);
                    }

                    value = (String) jobject.get("plantrecord_seed_name");
                    if(!(value.equals("null") && value !=null)){
                        plant.setPlantrecord_seed_name(value);
                    }
                    value = (String) jobject.get("plantrecord_seed_source");
                    if(!(value.equals("null") && value !=null)){
                        plant.setPlantrecord_seed_source(value);
                    }
                    value = (String) jobject.get("plantrecord_specifications");
                    if(!(value.equals("null") && value !=null)){
                        plant.setPlantrecord_specifications(value);
                    }
                    value = (String) jobject.get("plantrecord_seed_number");
                    if(!(value.equals("null") && value !=null)){
                        plant.setPlantrecord_seed_number(value);
                    }

                    value = (String) jobject.get("plantrecord_plant_date");
                    if(!(value.equals("null") && value !=null)){
                        plant.setPlantrecord_plant_date(value);
                    }
                    value = (String) jobject.get("field_name");
                    if(!(value.equals("null") && value !=null)){
                        plant.setField_name(value);
                    }
                    value = (String) jobject.get("employee_name");
                    if(!(value.equals("null") && value !=null)){
                        plant.setEmployee_name(value);
                    }
                    value = (String) jobject.get("plantrecord_breed");
                    if(!(value.equals("null") && value !=null)){
                        plant.setPlantrecord_breed(value);
                    }
                    ls.add(plant);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<FertilizerRecord> parseFertilizer(String val){
        try {
            List<FertilizerRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if(result.equals("success")){
                JSONArray array = object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    JSONObject jobject = (JSONObject) array.get(i);

                    FertilizerRecord fertilizer = new FertilizerRecord();
                    String value = (String) jobject.get("fertilizerecord_id");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_id(value);
                    }
                    value = (String) jobject.get("fertilizerecord_date");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_date(value);
                    }
                    value = (String) jobject.get("fertilizerecord_name");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_name(value);
                    }
                    value = (String) jobject.get("fertilizerecord_number");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_number(value);
                    }
                    value = (String) jobject.get("fertilizerecord_range");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_range(value);
                    }
                    value = (String) jobject.get("fertilizerecord_type");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_type(value);
                    }
                    value = (String) jobject.get("fertilizerecord_spec");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_spec(value);
                    }
                    value = (String) jobject.get("fertilizerecord_method");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_method(value);
                    }
                    value = (String) jobject.get("field_name");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setField_name(value);
                    }
                    value = (String) jobject.get("field_area");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setField_area(value);
                    }
                    value = (String) jobject.get("member_name");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setMember_name(value);
                    }
                    value = (String) jobject.get("employee_name");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setEmployee_name(value);
                    }
                    value = (String) jobject.get("plantrecord_breed");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setPlantrecord_breed(value);
                    }
                    value = (String) jobject.get("fertilizerecord_people");
                    if(!(value.equals("null") && value !=null)){
                        fertilizer.setFertilizerecord_people(value);
                    }
                    ls.add(fertilizer);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<PreventionRecord> parsePrevention(String val){
        try {
            List<PreventionRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if(result.equals("success")){
                JSONArray array = object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    JSONObject jobject = (JSONObject) array.get(i);

                    PreventionRecord preventionRecord = new PreventionRecord();
                    String value = (String) jobject.get("preventionrecord_id");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_id(value);
                    }
                    value = (String) jobject.get("preventionrecord_medicine_name");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_medicine_name(value);
                    }

                    value = (String) jobject.get("preventionrecord_date");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_date(value);
                    }

                    value = (String) jobject.get("preventionrecord_range");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_range(value);
                    }
                    value = (String) jobject.get("preventionrecord_type");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_type(value);
                    }

                    value = (String) jobject.get("preventionrecord_spec");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_spec(value);
                    }
                    value = (String) jobject.get("preventionrecord_method");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_method(value);
                    }

                    value = (String) jobject.get("preventionrecord_medicine_number");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_medicine_number(value);
                    }
                    value = (String) jobject.get("preventionrecord_plant_day");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_plant_day(value);
                    }
                    value = (String) jobject.get("preventionrecord_symptom");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_symptom(value);
                    }

                    value = (String) jobject.get("preventionrecord_medicine_people");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_medicine_people(value);
                    }
                    value = (String) jobject.get("preventionrecord_use_people");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPreventionrecord_use_people(value);
                    }

                    value = (String) jobject.get("field_name");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setField_name(value);
                    }
                    value = (String) jobject.get("field_area");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setField_area(value);
                    }

                    value = (String) jobject.get("plantrecord_breed");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPlantrecord_breed(value);
                    }
                    value = (String) jobject.get("plantrecord_plant_date");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setPlantrecord_plant_date(value);
                    }
                    value = (String) jobject.get("member_name");
                    if(!(value.equals("null") && value !=null)){
                        preventionRecord.setMember_name(value);
                    }
                    ls.add(preventionRecord);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<PickRecord> parsePick(String val){
        try {
            List<PickRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if(result.equals("success")){
                JSONArray array = object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    JSONObject jobject = (JSONObject) array.get(i);

                    PickRecord pickRecord = new PickRecord();
                    String value = (String) jobject.get("pickrecord_id");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setPickrecord_id(value);
                    }
                    value = (String) jobject.get("pickrecord_date");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setPickrecord_date(value);
                    }
                    value = (String) jobject.get("pickrecord_number");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setPickrecord_number(value);
                    }
                    value = (String) jobject.get("pickrecord_area");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setPickrecord_area(value);
                    }
                    value = (String) jobject.get("pickrecord_people");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setPickrecord_people(value);
                    }
                    value = (String) jobject.get("plantrecord_plant_date");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setPlantrecord_plant_date(value);
                    }
                    value = (String) jobject.get("field_name");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setField_name(value);
                    }
                    value = (String) jobject.get("plantrecord_breed");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setPlantrecord_breed(value);
                    }
                    value = (String) jobject.get("field_area");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setField_area(value);
                    }
//                    value = (String) jobject.get("pickrecord_code");
//                    if(!(value.equals("null") && value !=null)){
//                        pickRecord.setPickrecord_code(value);
//                    }
                    value = (String) jobject.get("pickrecord_status");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setPickrecord_status(value);
                    }
                    value = (String) jobject.get("member_name");
                    if(!(value.equals("null") && value !=null)){
                        pickRecord.setMember_name(value);
                    }

                    ls.add(pickRecord);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<OtherRecord> parseOther(String val){
        try {
            List<OtherRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if(result.equals("success")){
                JSONArray array = object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    JSONObject jobject = (JSONObject) array.get(i);

                    OtherRecord otherRecord = new OtherRecord();

                    String value = (String) jobject.get("otherrecord_id");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setOtherrecord_id(value);
                    }

                    value = (String) jobject.get("otherrecord_situation");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setOtherrecord_situation(value);
                    }
                    value = (String) jobject.get("otherrecord_date");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setOtherrecord_date(value);
                    }

                    value = (String) jobject.get("otherrecord_method");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setOtherrecord_method(value);
                    }
                    value = (String) jobject.get("otherrecord_place");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setOtherrecord_place(value);
                    }

                    value = (String) jobject.get("otherrecord_people");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setOtherrecord_people(value);
                    }
                    value = (String) jobject.get("plantrecord_breed");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setPlantrecord_breed(value);
                    }

                    value = (String) jobject.get("field_name");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setField_name(value);
                    }
                    value = (String) jobject.get("member_name");
                    if(!(value.equals("null") && value !=null)){
                        otherRecord.setMember_name(value);
                    }
                    ls.add(otherRecord);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }
}
