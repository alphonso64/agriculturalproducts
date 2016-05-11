package com.app.agriculturalproducts.http;

import android.util.Log;

import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.OtherRecord;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PersonalStockDetail;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.util.Configure;
import com.litesuits.http.HttpConfig;
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
    private static HttpClient single = null;
    public EmployeeInfo employeeInfo;
    public List<Field> fieldList;
    public List<PlanterRecord> planterList;
    public List<FertilizerRecord> fertiList;
    public List<PreventionRecord> preventionList;
    public List<PickRecord> pickList;
    public List<OtherRecord> otherList;
    public List<PersonalStock> seedStockList;
    public List<PersonalStock> fStockList;
    public List<PersonalStock> pStcokList;
    public List<PersonalStockDetail> enterstockList;
    public List<PersonalStockDetail> outstockList;
    public List<TaskRecord> taskList;
    //public List

    private HttpClient() {

//        HttpConfig config = new HttpConfig(AppApplication.getContext()) // configuration quickly
//                .setDetectNetwork(true);
        liteHttp = LiteHttp.newApacheHttpClient(null);

    }

    public static HttpClient getInstance() {
        if (single == null) {
            single = new HttpClient();
        }
        return single;
    }

    public void checkLogin(HttpListener<String> listener, String name, String pwd) {
        if (listener == null || name == null || pwd == null) return;
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username", name);
            object.put("userinfo_password", pwd);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        liteHttp.executeAsync(new StringRequest(Configure.LOGIN_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString())).setHttpListener(listener));
    }

    public void infoRequestAsync(HttpListener<String> listener, String url, String name) {
        if (listener == null || name == null) return;
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username", name);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        liteHttp.executeAsync(new StringRequest(url).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString())).setHttpListener(listener));
    }

    public void getStockInfo(String name){
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username", name);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.GET_EMPLOYEE_BY_USERNAME_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));

        stringRequest.setUri(Configure.GET_SEED_PERSONALSTOCK_BY_USERNAME_URL);
        Response<String> result = liteHttp.execute(stringRequest);
        seedStockList = parsePersonalStock(result.getResult(),0);
        for (int i = 0; i < seedStockList.size(); i++) {
            seedStockList.get(i).printfInfo();
        }
        // Log.e("testcc", result.getResult());

        stringRequest.setUri(Configure.GET_FERTILIZER_PERSONALSTOCK_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        fStockList = parsePersonalStock(result.getResult(), 1);
        for (int i = 0; i < fStockList.size(); i++) {
            fStockList.get(i).printfInfo();
        }

        stringRequest.setUri(Configure.GET_PESTICIDE_PERSONALSTOCK_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        pStcokList = parsePersonalStock(result.getResult(), 2);
        for (int i = 0; i < pStcokList.size(); i++) {
            pStcokList.get(i).printfInfo();
        }
    }

    public void getStockDetailInfo(String name){
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username", name);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.GET_EMPLOYEE_BY_USERNAME_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));

        stringRequest.setUri(Configure.GET_ENTERPERSONALSTOCKDETAIL_BY_USERNAME_URL);
        Response<String> result = liteHttp.execute(stringRequest);
        enterstockList = parsePersonalStockDetail(result.getResult(), 0);
        for (int i = 0; i < enterstockList.size(); i++) {
            enterstockList.get(i).printfInfo();
        }

        stringRequest.setUri(Configure.GET_OUTPERSONALSTOCKDETAIL_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        outstockList= parsePersonalStockDetail(result.getResult(), 1);
        for (int i = 0; i < outstockList.size(); i++) {
            outstockList.get(i).printfInfo();
        }
    }

    public void getFieldInfo(String name){
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username", name);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.GET_FIELD_BY_USERNAME_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));

        Response<String> result = liteHttp.execute(stringRequest);
        fieldList = parseField(result.getResult());
    }

    public int getTaskInfo(String name)
    {
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username", name);
        } catch (JSONException e) {
            return -2;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.GET_WORKTASK_BY_USERNAME_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));
        try {
            Response<String> result = liteHttp.execute(stringRequest);
            taskList = parseTask(result.getResult());
        } catch (Exception e) {
            return -1;
        }
        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).printfInfo();
        }
        return 0;
    }


    public void getAllInfo(String name) {
        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username", name);
        } catch (JSONException e) {
            return;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.GET_EMPLOYEE_BY_USERNAME_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));

        Response<String> result = liteHttp.execute(stringRequest);
        employeeInfo = parseEmployeeInfo(result.getResult());
//        Log.e("testcc", result.getResult());
//        employeeInfo.printfInfo();

        stringRequest.setUri(Configure.GET_FIELD_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        fieldList = parseField(result.getResult());
        Log.e("testcc", result.getResult() + fieldList.size());
//        for(int i=0;i<fieldList.size();i++){
//            fieldList.get(i).printfInfo();
//        }

        stringRequest.setUri(Configure.GET_PLANTRECORD_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        planterList = parsePlant(result.getResult());
        Log.e("testcc", result.getResult()+planterList.size());
//        for(int i=0;i<planterList.size();i++){
//            planterList.get(i).printfInfo();
//        }

        stringRequest.setUri(Configure.GET_FERTILIZERECORD_BY_USERNAME_URL);
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

        stringRequest.setUri(Configure.GET_PICKRECORD_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        pickList = parsePick(result.getResult());
//        Log.e("testcc", result.getResult()+pickList.size());
//        for(int i=0;i<pickList.size();i++){
//            pickList.get(i).printfInfo();
//        }

        stringRequest.setUri(Configure.GET_OTHERRECORD_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        otherList = parseOther(result.getResult());
        //Log.e("testcc", result.getResult());

        stringRequest.setUri(Configure.GET_ENTERPERSONALSTOCKDETAIL_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        enterstockList = parsePersonalStockDetail(result.getResult(),0);
//        Log.e("testcc", result.getResult());
//        for (int i = 0; i < stockList.size(); i++) {
//            stockList.get(i).printfInfo();
//        }
        stringRequest.setUri(Configure.GET_OUTPERSONALSTOCKDETAIL_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        outstockList = parsePersonalStockDetail(result.getResult(),1);

        stringRequest.setUri(Configure.GET_SEED_PERSONALSTOCK_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        seedStockList = parsePersonalStock(result.getResult(),0);
//        for (int i = 0; i < seedStockList.size(); i++) {
//            seedStockList.get(i).printfInfo();
//        }
        Log.e("testcc", result.getResult());

        stringRequest.setUri(Configure.GET_FERTILIZER_PERSONALSTOCK_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        fStockList = parsePersonalStock(result.getResult(),1);
//        for (int i = 0; i < fStockList.size(); i++) {
//            fStockList.get(i).printfInfo();
//        }
        Log.e("testcc", result.getResult());

        stringRequest.setUri(Configure.GET_PESTICIDE_PERSONALSTOCK_BY_USERNAME_URL);
        result = liteHttp.execute(stringRequest);
        pStcokList = parsePersonalStock(result.getResult(),2);
//        for (int i = 0; i < pStcokList.size(); i++) {
//            pStcokList.get(i).printfInfo();
//        }
        Log.e("testcc", result.getResult());
    }

    public EmployeeInfo parseEmployeeInfo(String val) {
        try {
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                EmployeeInfo employeeInfo = new EmployeeInfo();
                String value = (String) object.get("employee_id");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setEmployee_id(value);
                }

                value = (String) object.get("member_id");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setMember_id(value);
                }

                value = (String) object.get("employee_name");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setEmployee_name(value);
                }

                value = (String) object.get("employee_sex");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setEmployee_sex(value);
                }

                value = (String) object.get("employee_birthday");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setEmployee_birthday(value);
                }

                value = (String) object.get("employee_tel");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setEmployee_tel(value);
                }

                value = (String) object.get("employee_address");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setEmployee_address(value);
                }

                value = (String) object.get("employee_passport");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setEmployee_passport(value);
                }

                value = (String) object.get("employee_type");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setEmployee_type(value);
                }

                value = (String) object.get("member_name");
                if (!(value.equals("null") && value != null)) {
                    employeeInfo.setMember_name(value);
                }
                return employeeInfo;
            }
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<TaskRecord> parseTask(String val) {
        try {
            List<TaskRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    TaskRecord task = new TaskRecord();
                    String value = (String) jobject.get("worktasklist_id");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktasklist_id(value);
                    }

                    value = (String) jobject.get("worktasklist_ischeck");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktasklist_ischeck(value);
                    }
                    value = (String) jobject.get("worktasklist_status");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktasklist_status(value);
                    }
                    value = (String) jobject.get("worktask_name");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktask_name(value);
                    }
                    value = (String) jobject.get("worktask_type");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktask_type(value);
                    }
                    value = (String) jobject.get("worktask_content");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktask_content(value);
                    }

                    value = (String) jobject.get("worktask_publish_people");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktask_publish_people(value);
                    }
                    value = (String) jobject.get("worktask_publish_date");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktask_publish_date(value);
                    }

                    value = (String) jobject.get("worktask_finish_date");
                    if (!(value.equals("null") && value != null)) {
                        task.setWorktask_finish_date(value);
                    }
                    value = (String) jobject.get("employee_name");
                    if (!(value.equals("null") && value != null)) {
                        task.setEmployee_name(value);
                    }

                    value = (String) jobject.get("employee_card");
                    if (!(value.equals("null") && value != null)) {
                        task.setEmployee_card(value);
                    }


                    ls.add(task);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<Field> parseField(String val) {
        try {
            List<Field> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    Field field = new Field();
                    String value = (String) jobject.get("field_id");
                    if (!(value.equals("null") && value != null)) {
                        field.setField_id(value);
                    }

                    value = (String) jobject.get("employee_id");
                    if (!(value.equals("null") && value != null)) {
                        field.setEmployee_id(value);
                    }
                    value = (String) jobject.get("field_name");
                    if (!(value.equals("null") && value != null)) {
                        field.setField_name(value);
                    }
                    value = (String) jobject.get("field_type");
                    if (!(value.equals("null") && value != null)) {
                        field.setField_type(value);
                    }
                    value = (String) jobject.get("field_area");
                    if (!(value.equals("null") && value != null)) {
                        field.setField_area(value);
                    }
                    value = (String) jobject.get("field_status");
                    if (!(value.equals("null") && value != null)) {
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

    public List<PlanterRecord> parsePlant(String val) {
        try {
            List<PlanterRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    PlanterRecord plant = new PlanterRecord();
                    String value = (String) jobject.get("plantrecord_id");
                    if (!(value.equals("null") && value != null)) {
                        plant.setPlantrecord_id(value);
                    }

                    value = (String) jobject.get("plantrecord_seed_name");
                    if (!(value.equals("null") && value != null)) {
                        plant.setPlantrecord_seed_name(value);
                    }
                    value = (String) jobject.get("plantrecord_seed_source");
                    if (!(value.equals("null") && value != null)) {
                        plant.setPlantrecord_seed_source(value);
                    }
                    value = (String) jobject.get("plantrecord_specifications");
                    if (!(value.equals("null") && value != null)) {
                        plant.setPlantrecord_specifications(value);
                    }
                    value = (String) jobject.get("field_id");
                    if (!(value.equals("null") && value != null)) {
                        plant.setLocal_field_id(value);
                    };
                    value = (String) jobject.get("plantrecord_seed_number");
                    if (!(value.equals("null") && value != null)) {
                        plant.setPlantrecord_seed_number(value);
                    }

                    value = (String) jobject.get("plantrecord_plant_date");
                    if (!(value.equals("null") && value != null)) {
                        plant.setPlantrecord_plant_date(value);
                    }
                    value = (String) jobject.get("field_name");
                    if (!(value.equals("null") && value != null)) {
                        plant.setField_name(value);
                    }
                    value = (String) jobject.get("plantrecord_area");
                    if (!(value.equals("null") && value != null)) {
                        plant.setField_plant_area(value);
                    }
                    value = (String) jobject.get("employee_name");
                    if (!(value.equals("null") && value != null)) {
                        plant.setEmployee_name(value);
                    }
                    value = (String) jobject.get("plantrecord_breed");
                    if (!(value.equals("null") && value != null)) {
                        plant.setPlantrecord_breed(value);
                    }
                    plant.setSaved("yes");
                    ls.add(plant);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<FertilizerRecord> parseFertilizer(String val) {
        try {
            List<FertilizerRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    FertilizerRecord fertilizer = new FertilizerRecord();
                    String value = (String) jobject.get("fertilizerecord_id");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_id(value);
                    }
                    value = (String) jobject.get("fertilizerecord_date");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_date(value);
                    }
                    value = (String) jobject.get("fertilizerecord_name");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_name(value);
                    }
                    value = (String) jobject.get("fertilizerecord_number");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_number(value);
                    }
                    value = (String) jobject.get("fertilizerecord_range");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_range(value);
                    }
                    value = (String) jobject.get("fertilizerecord_type");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_type(value);
                    }
                    value = (String) jobject.get("fertilizerecord_spec");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_spec(value);
                    }
                    value = (String) jobject.get("fertilizerecord_method");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_method(value);
                    }
                    value = (String) jobject.get("field_name");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setField_name(value);
                    }
                    value = (String) jobject.get("field_area");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setField_area(value);
                    }
                    value = (String) jobject.get("member_name");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setMember_name(value);
                    }
                    value = (String) jobject.get("employee_name");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setEmployee_name(value);
                    }
                    value = (String) jobject.get("plantrecord_breed");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setPlantrecord_breed(value);
                    }
                    value = (String) jobject.get("fertilizerecord_people");
                    if (!(value.equals("null") && value != null)) {
                        fertilizer.setFertilizerecord_people(value);
                    }
                    fertilizer.setSaved("yes");
                    ls.add(fertilizer);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<PreventionRecord> parsePrevention(String val) {
        try {
            List<PreventionRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    PreventionRecord preventionRecord = new PreventionRecord();
                    String value = (String) jobject.get("preventionrecord_id");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_id(value);
                    }
                    value = (String) jobject.get("preventionrecord_medicine_name");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_medicine_name(value);
                    }

                    value = (String) jobject.get("preventionrecord_date");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_date(value);
                    }

                    value = (String) jobject.get("preventionrecord_range");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_range(value);
                    }
                    value = (String) jobject.get("preventionrecord_type");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_type(value);
                    }

                    value = (String) jobject.get("preventionrecord_spec");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_spec(value);
                    }
                    value = (String) jobject.get("preventionrecord_method");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_method(value);
                    }

                    value = (String) jobject.get("preventionrecord_medicine_number");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_medicine_number(value);
                    }
                    value = (String) jobject.get("preventionrecord_plant_day");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_plant_day(value);
                    }
                    value = (String) jobject.get("preventionrecord_symptom");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_symptom(value);
                    }

                    value = (String) jobject.get("preventionrecord_medicine_people");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_medicine_people(value);
                    }
                    value = (String) jobject.get("preventionrecord_use_people");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPreventionrecord_use_people(value);
                    }

                    value = (String) jobject.get("field_name");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setField_name(value);
                    }
                    value = (String) jobject.get("field_area");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setField_area(value);
                    }

                    value = (String) jobject.get("plantrecord_breed");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPlantrecord_breed(value);
                    }
                    value = (String) jobject.get("plantrecord_plant_date");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setPlantrecord_plant_date(value);
                    }
                    value = (String) jobject.get("member_name");
                    if (!(value.equals("null") && value != null)) {
                        preventionRecord.setMember_name(value);
                    }
                    preventionRecord.setSaved("yes");
                    ls.add(preventionRecord);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<PickRecord> parsePick(String val) {
        try {
            List<PickRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    PickRecord pickRecord = new PickRecord();
                    String value = (String) jobject.get("pickrecord_id");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setPickrecord_id(value);
                    }
                    value = (String) jobject.get("pickrecord_date");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setPickrecord_date(value);
                    }
                    value = (String) jobject.get("pickrecord_number");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setPickrecord_number(value);
                    }
                    value = (String) jobject.get("pickrecord_area");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setPickrecord_area(value);
                    }
                    value = (String) jobject.get("pickrecord_people");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setPickrecord_people(value);
                    }
                    value = (String) jobject.get("plantrecord_plant_date");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setPlantrecord_plant_date(value);
                    }
                    value = (String) jobject.get("field_name");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setField_name(value);
                    }
                    value = (String) jobject.get("plantrecord_breed");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setPlantrecord_breed(value);
                    }
                    value = (String) jobject.get("field_area");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setField_area(value);
                    }
//                    value = (String) jobject.get("pickrecord_code");
//                    if(!(value.equals("null") && value !=null)){
//                        pickRecord.setPickrecord_code(value);
//                    }
                    value = (String) jobject.get("pickrecord_status");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setPickrecord_status(value);
                    }
                    value = (String) jobject.get("member_name");
                    if (!(value.equals("null") && value != null)) {
                        pickRecord.setMember_name(value);
                    }
                    pickRecord.setSaved("yes");
                    ls.add(pickRecord);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<OtherRecord> parseOther(String val) {
        try {
            List<OtherRecord> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    OtherRecord otherRecord = new OtherRecord();

                    String value = (String) jobject.get("otherrecord_id");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setOtherrecord_id(value);
                    }

                    value = (String) jobject.get("otherrecord_situation");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setOtherrecord_situation(value);
                    }
                    value = (String) jobject.get("otherrecord_date");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setOtherrecord_date(value);
                    }

                    value = (String) jobject.get("otherrecord_method");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setOtherrecord_method(value);
                    }
                    value = (String) jobject.get("otherrecord_place");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setOtherrecord_place(value);
                    }

                    value = (String) jobject.get("otherrecord_people");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setOtherrecord_people(value);
                    }
                    value = (String) jobject.get("plantrecord_breed");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setPlantrecord_breed(value);
                    }

                    value = (String) jobject.get("field_name");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setField_name(value);
                    }
                    value = (String) jobject.get("member_name");
                    if (!(value.equals("null") && value != null)) {
                        otherRecord.setMember_name(value);
                    }
                    otherRecord.setSaved("yes");
                    ls.add(otherRecord);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<PersonalStock> parsePersonalStock(String val,int cmd) {
        try {
            List<PersonalStock> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    PersonalStock personalStock = new PersonalStock();

                    String value = (String) jobject.get("personalstock_id");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstock_id(value);
                    }
                    value = (String) jobject.get("personalstock_num");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstock_num(value);
                    }
                    value = (String) jobject.get("personalstock_goods_id");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstock_goods_id(value);
                    }
                    value = (String) jobject.get("personalstock_goods_type");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstock_goods_type(value);
                    }
                    value = (String) jobject.get("personalstock_goods_name");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstock_goods_name(value);
                    }
                    value = (String) jobject.get("spec");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setSpec(value);
                    }
                    value = (String) jobject.get("producer");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setProducer(value);
                    }
                    value = (String) jobject.get("employee_name");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setEmployee_name(value);
                    }
                    value = (String) jobject.get("member_name");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setMember_name(value);
                    }
                    if(cmd == 0){
                        value = (String) jobject.get("breed");
                        if (!(value.equals("null") && value != null)) {
                            personalStock.setBreed(value);
                        }
                        personalStock.setType(PersonalStock.SEED_TYPE);
                    }else if(cmd == 1) {
                        value = (String) jobject.get("method");
                        if (!(value.equals("null") && value != null)) {
                            personalStock.setMethod(value);
                        }
                        personalStock.setType(PersonalStock.F_TYPE);
                    }else if(cmd == 2) {
                        value = (String) jobject.get("method");
                        if (!(value.equals("null") && value != null)) {
                            personalStock.setMethod(value);
                        }
                        value = (String) jobject.get("pesticide_safe_spacing");
                        if (!(value.equals("null") && value != null)) {
                            personalStock.setSafe_spacing(value);
                        }
                        personalStock.setType(PersonalStock.P_TYPE);
                    }
                        ls.add(personalStock);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<PersonalStockDetail> parsePersonalStockDetail(String val,int cmd) {
        try {
            List<PersonalStockDetail> ls = new ArrayList<>();
            JSONObject object = new JSONObject(val);
            String result = (String) object.get("return_code");
            if (result.equals("success")) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);

                    PersonalStockDetail personalStock = new PersonalStockDetail();

                    String value = (String) jobject.get("personalstockdetail_id");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_id(value);
                    }
                    value = (String) jobject.get("personalstockdetail_orderno");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_orderno(value);
                    }
                    value = (String) jobject.get("personalstockdetail_goods_id");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_goods_id(value);
                    }
                    value = (String) jobject.get("personalstockdetail_num");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_num(value);
                    }
                    value = (String) jobject.get("personalstockdetail_price");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_price(value);
                    }
                    value = (String) jobject.get("personalstockdetail_total");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_total(value);
                    }
                    value = (String) jobject.get("personalstockdetail_goods_type");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_goods_type(value);
                    }
                    value = (String) jobject.get("personalstockdetail_date");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_date(value);
                    }
                    value = (String) jobject.get("personalstockdetail_type");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_type(value);
                    }
                    value = (String) jobject.get("personalstockdetail_goods_name");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setPersonalstockdetail_goods_name(value);
                    }
                    value = (String) jobject.get("spec");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setSpec(value);
                    }
                    value = (String) jobject.get("producer");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setProducer(value);
                    }
                    value = (String) jobject.get("employee_name");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setEmployee_name(value);
                    }
                    value = (String) jobject.get("member_name");
                    if (!(value.equals("null") && value != null)) {
                        personalStock.setMember_name(value);
                    }
                    if(cmd == 0 ){
                        personalStock.setType(PersonalStockDetail.ENTER_TYPE);
                    }else if(cmd == 1){
                        personalStock.setType(PersonalStockDetail.OUT_TYPE);
                    }
                    ls.add(personalStock);
                }
            }
            return ls;
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public int uploadPlant(HttpListener<String> listener,PlanterRecord planterRecord){
        JSONObject object = new JSONObject();
        try {
            object.put("personalstock_id", planterRecord.getLocal_stock_id());
            object.put("plantrecord_breed", planterRecord.getPlantrecord_breed());
            object.put("plantrecord_seed_name", planterRecord.getPlantrecord_seed_name());
            object.put("plantrecord_seed_source", planterRecord.getPlantrecord_seed_source());
            object.put("plantrecord_specifications", planterRecord.getPlantrecord_specifications());
            object.put("plantrecord_seed_number", planterRecord.getPlantrecord_seed_number());
            object.put("plantrecord_plant_date", planterRecord.getPlantrecord_plant_date());
            object.put("field_id", planterRecord.getLocal_field_id());
            object.put("plantrecord_area",planterRecord.getField_plant_area());
            Log.e("testcc",object.toString());
        } catch (JSONException e) {
            return 0;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.ADD_PLANTRECORD_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));
        stringRequest.setHttpListener(listener);
        liteHttp.executeAsync(stringRequest);
        return 0;
    }

    public int uploadFertilizer(HttpListener<String> listener,FertilizerRecord fertilizerRecord){
        JSONObject object = new JSONObject();
        try {
            object.put("personalstock_id", fertilizerRecord.getLocal_stock_id());
            object.put("fertilizerecord_date", fertilizerRecord.getFertilizerecord_date());
            object.put("fertilizerecord_name", fertilizerRecord.getFertilizerecord_name());
            object.put("fertilizerecord_number", fertilizerRecord.getFertilizerecord_number());
            object.put("fertilizerecord_range", fertilizerRecord.getFertilizerecord_range());
            object.put("fertilizerecord_type", fertilizerRecord.getFertilizerecord_type());
            object.put("fertilizerecord_spec", fertilizerRecord.getFertilizerecord_spec());
            object.put("fertilizerecord_method", fertilizerRecord.getFertilizerecord_method());
            object.put("fertilizerecord_people", fertilizerRecord.getFertilizerecord_people());
            object.put("plantrecord_id", fertilizerRecord.getLocal_plant_id());
            Log.e("testcc",object.toString());
        } catch (JSONException e) {
            return 0;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.ADD_FERTILIZERECORD_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));
        stringRequest.setHttpListener(listener);
        liteHttp.executeAsync(stringRequest);
        return 0;
    }

    public int uploadPrevention(HttpListener<String> listener,PreventionRecord preventionRecord){
        JSONObject object = new JSONObject();
        try {
            object.put("personalstock_id", preventionRecord.getLocal_stock_id());
            object.put("preventionrecord_medicine_name", preventionRecord.getPreventionrecord_medicine_name());
            object.put("preventionrecord_date", preventionRecord.getPreventionrecord_date());
            object.put("preventionrecord_range", preventionRecord.getPreventionrecord_range());
            object.put("preventionrecord_type", preventionRecord.getPreventionrecord_type());
            object.put("preventionrecord_spec", preventionRecord.getPreventionrecord_spec());
            object.put("preventionrecord_method", preventionRecord.getPreventionrecord_method());
            object.put("preventionrecord_medicine_number", preventionRecord.getPreventionrecord_medicine_number());
            object.put("preventionrecord_plant_day", preventionRecord.getPreventionrecord_plant_day());
            object.put("preventionrecord_symptom", preventionRecord.getPreventionrecord_symptom());
            object.put("preventionrecord_medicine_people", preventionRecord.getPreventionrecord_medicine_people());
            object.put("preventionrecord_use_people", preventionRecord.getPreventionrecord_use_people());
            object.put("plantrecord_id", preventionRecord.getLocal_plant_id());
            Log.e("testcc",object.toString());
        } catch (JSONException e) {
            return 0;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.ADD_PREVENTIONRECORD_URL ).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));
        stringRequest.setHttpListener(listener);
        liteHttp.executeAsync(stringRequest);
        return 0;
    }

    public int uploadPick(HttpListener<String> listener,PickRecord pickRecord){
        JSONObject object = new JSONObject();
        try {
            object.put("plantrecord_id", pickRecord.getLocal_plant_id());
            object.put("pickrecord_date", pickRecord.getPickrecord_date());
            object.put("pickrecord_number", pickRecord.getPickrecord_number());
            object.put("pickrecord_area", pickRecord.getPickrecord_area());
            object.put("pickrecord_people", pickRecord.getPickrecord_people());
            Log.e("testcc",object.toString());
        } catch (JSONException e) {
            return 0;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.ADD_PICKRECORD_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));
        stringRequest.setHttpListener(listener);
        liteHttp.executeAsync(stringRequest);
        return 0;
    }

    public int uploadOther(HttpListener<String> listener,OtherRecord otherRecord){
        JSONObject object = new JSONObject();
        try {
            object.put("otherrecord_situation", otherRecord.getOtherrecord_situation());
            object.put("otherrecord_date", otherRecord.getOtherrecord_date());
            object.put("otherrecord_method", otherRecord.getOtherrecord_method());
            object.put("otherrecord_place", otherRecord.getOtherrecord_place());
            object.put("otherrecord_people", otherRecord.getOtherrecord_people());
            object.put("plantrecord_id", otherRecord.getLocal_plant_id());
            Log.e("testcc",object.toString());
        } catch (JSONException e) {
            return 0;
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        StringRequest stringRequest = new StringRequest(Configure.ADD_OTHERRECORD_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString()));
        stringRequest.setHttpListener(listener);
        liteHttp.executeAsync(stringRequest);
        return 0;
    }
}
