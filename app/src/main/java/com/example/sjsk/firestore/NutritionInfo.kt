package com.example.sjsk.firestore

import android.util.Log
import com.google.gson.annotations.SerializedName
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.InputStream


//API에서 받을 JSON 데이터에 맞춰 Kotlin 데이터 클래스를 정의

@Root(name = "response", strict = false)
data class ApiResponse(
    @field:Element(name = "header", required = false)
    var header: Header? = null,

    @field:Element(name = "body", required = false)
    var body: Body? = null
)

@Root(name = "header", strict = false)
data class Header(
    @field:Element(name = "resultCode", required = false)
    var resultCode: String? = null,

    @field:Element(name = "resultMsg", required = false)
    var resultMsg: String? = null
)

@Root(name = "body", strict = false)
data class Body(
    @field:Element(name = "numOfRows", required = false)
    var numOfRows: String? = null,

    @field:Element(name = "pageNo", required = false)
    var pageNo: String? = null,

    @field:Element(name = "totalCount", required = false)
    var totalCount: String? = null,

    @field:ElementList(entry = "items", required = false, inline = true)
    var items: ArrayList<Items>? = null
)

@Root(name = "items", strict = false)
data class Items(
    @field:ElementList(entry = "item", required = false, inline = true)
    var item: ArrayList<Item>? = null
)

@Root(name = "item", strict = false)
data class Item(
//    val NUM: String, //번호

    @field:Element(name = "FOOD_CD", required = false)
    var FOOD_CD: String? = null,

    @field:Element(name = "ITEM_REPORT_NO", required = false)
    var ITEM_REPORT_NO: String? = null,

    @field:Element(name = "FOOD_NM_KR", required = false)
    var FOOD_NM_KR: String? = null,

    @field:Element(name = "FOOD_OR_NM", required = false)
    var FOOD_OR_NM: String? = null,

    @field:Element(name = "FOOD_REF_NM", required = false)
    var FOOD_REF_NM: String? = null,

    @field:Element(name = "FOOD_CAT3_NM", required = false)
    var FOOD_CAT3_NM: String? = null,

    @field:Element(name = "SERVING_SIZE", required = false)
    var SERVING_SIZE: String? = null,

    @field:Element(name = "AMT_NUM1", required = false)
    var AMT_NUM1: String? = null,

    @field:Element(name = "AMT_NUM3", required = false)
    var AMT_NUM3: String? = null,

    @field:Element(name = "AMT_NUM4", required = false)
    var AMT_NUM4: String? = null,

    @field:Element(name = "AMT_NUM6", required = false)
    var AMT_NUM6: String? = null,

    @field:Element(name = "AMT_NUM7", required = false)
    var AMT_NUM7: String? = null,

    @field:Element(name = "AMT_NUM13", required = false)
    var AMT_NUM13: String? = null,

    @field:Element(name = "AMT_NUM22", required = false)
    var AMT_NUM22: String? = null,

    @field:Element(name = "AMT_NUM23", required = false)
    var AMT_NUM23: String? = null,

    @field:Element(name = "NUTRI_AMOUNT_SERVING", required = false)
    var NUTRI_AMOUNT_SERVING: String? = null,

    @field:Element(name = "Z10500", required = false)
    var Z10500: String? = null,

    @field:Element(name = "MAKER_NM", required = false)
    var MAKER_NM: String? = null,

    @field:Element(name = "UPDATE_YMD", required = false)
    var UPDATE_YMD: String? = null
)




