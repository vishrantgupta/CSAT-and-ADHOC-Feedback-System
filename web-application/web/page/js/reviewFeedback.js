$(document).ready(function() {
    resetPage();
    document.getElementById("engagementId").selectedIndex = 0;
});

function showFeedbackTypeRadio(engagement) {
    resetPage();
    if (engagement.value != "Select Enagagement") {
        $("#feedbackTypeRadioTr").show();
    }
}

function showDateRange(){
    $("#dateRangeTr").show();
    $(".datepicker").val("");
    
    $("#appNameTr").hide();
    $("#clientNameTr").hide();
    $("#incidentIdTr").hide();
    $("#feedbackTableTr").hide();
    
    imgTable = document.getElementById("feedbackTable");
    for (var i = imgTable.rows.length - 1; i >= 0; i--) {
        imgTable.deleteRow(i);
    }
    
    $("#selectedIncidentId").empty();
    
    document.getElementById("selectedAppNameId").selectedIndex = 0;
    document.getElementById("selectedIncidentId").selectedIndex = 0;
    
}

function showAppName() {

    $("#clientNameTr").hide();
    $("#incidentIdTr").hide();
    $("#feedbackTableTr").hide();
    
    imgTable = document.getElementById("feedbackTable");
    for (var i = imgTable.rows.length - 1; i >= 0; i--) {
        imgTable.deleteRow(i);
    }
    $("#selectedIncidentId").empty();
    
    document.getElementById("selectedAppNameId").selectedIndex = 0;
    document.getElementById("selectedIncidentId").selectedIndex = 0;

    var selectedRadio = $('input[name=feedbackTypeRadio]:checked').val();

    var fromDate = document.getElementById("fromDate").value;
    var toDate = document.getElementById("toDate").value;

    if (fromDate == '' || toDate == '') {
        alert("Please select date range first.");
        return;
    }

    if (!validateDateRangeForGivenDays(fromDate, toDate, 15)) {
        return;
    }

    var engagementId = document.getElementById("engagementId").value;
    $('#descriptionId').val("");

    if (engagementId != "Select Enagagement" && selectedRadio == 'incident') {
        var urlString = root + contextRoot + "/page/review.do?action=getAppName";
        var parameterSrt = "&engagementId=" + engagementId + "&feedbackType=" + selectedRadio + "&fromDate=" + fromDate + "&toDate=" + toDate;
        
        showWait();
        $.ajax({
            type: "POST",
            url: urlString,
            data: parameterSrt,
            success: function(response) {
                displayAppName(response, selectedRadio);
            },
            error: function(e) {
                ajaxFailHandle(e);
            }
        });
    } else {
        $('#selectedAppNameId').empty();
        
        getIncidentList();
    }
}

function displayAppName(response, selectedRadio) {

    hideWait();

    $("#appNameTr").hide();
    $("#clientNameTr").hide();
    $("#descriptionIdTr").hide();
    $("#feedbackTableTr").hide();
    $('#selectedAppNameId').empty();

    var dataFoundMsg = $(response).find("noDataFound").text();
    if (dataFoundMsg != '') {
        alert(dataFoundMsg);
    } else {

        newoption = new Option("Select Application", "Select Application", false, false);
        document.getElementById("selectedAppNameId").options.add(newoption);

        $(response).find("app").each(function() {
            var appId = $(this).find("appId").text();
            var appName = $(this).find("appName").text();

            newoption = new Option(appName, appId, false, false);
            document.getElementById("selectedAppNameId").options.add(newoption);
            newoption = null;
        });

        $("#appNameTr").show();

        if (selectedRadio == "event") {
            getFeedbackForm();
        }
    }
}

function clearReport(){
    
    $("#appNameTr").hide();
    $("#clientNameTr").hide();
    $("#incidentIdTr").hide();
    $("#feedbackTableTr").hide();
    
    imgTable = document.getElementById("feedbackTable");
    for (var i = imgTable.rows.length - 1; i >= 0; i--) {
        imgTable.deleteRow(i);
    }
    
    $("#selectedIncidentId").empty();
    
    document.getElementById("selectedAppNameId").selectedIndex = 0;
    document.getElementById("selectedIncidentId").selectedIndex = 0;
    
}

function showClientName() {

    var fromDate = document.getElementById("fromDate").value;
    var toDate = document.getElementById("toDate").value;

    if (fromDate == '' || toDate == '') {
        document.getElementById("selectedAppNameId").selectedIndex = 0;
        alert("Please select date range first.");
        return;
    }

    if (validateDateRangeForGivenDays(fromDate, toDate, 15)) {

        var appId = document.getElementById("selectedAppNameId").value;

        $("#clientNameTr").hide();
        $("#incidentIdTr").hide();
        $("#feedbackTableTr").hide();

        if (appId != "Select Application") {
            var urlString = root + contextRoot + "/page/review.do?action=getClientName";
            var parameterSrt = "&appId=" + appId;

            showWait();
            ajaxCallWithFailureHandler(urlString, parameterSrt, "displayClientDetails", "ajaxFailHandle");
        }
    } else {
        document.getElementById("selectedAppNameId").selectedIndex = 0;
    }

}

function displayClientDetails(response) {
    var clientNameTd = document.getElementById("clientNameTd");
    hideWait();
    var clientNameHtml = "";

    $(response).find("start").each(function() {
        var clientName = $(this).find("clientName").text();
        clientNameHtml += "<span class='client-span' >" + clientName + "</span>";
    });

    clientNameTd.innerHTML = clientNameHtml;

    $("#clientNameTr").show();
    getIncidentList();
}

function ajaxFailHandle(e) {
    disableDocumentFields(document, false);
    resetPage();
    alert("Error occured, please try again.");
    reportJSError("Error occured in report " + e.responseText);
}

function getIncidentList() {

    var engagementId = document.getElementById("engagementId").value;
    var feedbackType = $('input[name=feedbackTypeRadio]:checked').val();
    var fromDate = document.getElementById("fromDate").value;
    var toDate = document.getElementById("toDate").value;

    var appIdObj = document.getElementById("selectedAppNameId");
    var appId = appIdObj.selectedIndex == 0 ? "" : appIdObj.value;

    var urlString = root + contextRoot + "/page/review.do?action=getIncidentList";
    var parameterSrt = "&appId=" + appId + "&engagementId=" + engagementId + "&feedbackType=" + feedbackType + "&fromDate=" + fromDate + "&toDate=" + toDate;

    showWait();
    ajaxCallWithFailureHandler(urlString, parameterSrt, "displayIncidentList", "ajaxFailHandle");
}

function displayIncidentList(response) {

    hideWait();

    var dataFoundMsg = $(response).find("noDataFound").text();
    if (dataFoundMsg != '') {
        alert(dataFoundMsg);
    } else {

        $('#selectedIncidentId').empty();
        $(response).find("incident").each(function() {
            
            var incidentId = $(this).find("incidentId").text();
            var descTxt = $(this).find("descTxt").text();
            
            newoption = new Option(descTxt, incidentId, false, false);
            document.getElementById("selectedIncidentId").options.add(newoption);
            newoption = null;
        });

        $("#incidentIdTr").show();
    }
}

function showFeedback(incidentIdObj) {
    imgTable = document.getElementById("feedbackTable");
    $("#feedbackTableTr").hide();

    for (var i = imgTable.rows.length - 1; i >= 0; i--) {
        imgTable.deleteRow(i);
    }

    if (incidentIdObj.value != "Select description") {

        var appIdObj = document.getElementById("selectedAppNameId");
        var appId = appIdObj.selectedIndex == 0 ? "" : appIdObj.value;

        var urlString = root + contextRoot + "/page/review.do?action=getReport";
        var parameterSrt = "&appId=" + appId + "&incidentId=" + incidentIdObj.value;

        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "displayReport", "ajaxFailHandle");
    }
}

function displayReport(response) {

    hideWait();
    imgTable = document.getElementById("feedbackTable");

    var dataFoundMsg = $(response).find("nodatafound").text();
    if (dataFoundMsg != '') {
        alert(dataFoundMsg);
    } else {

        var previousCategory = "";
        $(response).find("feedback").each(function() {

            var category = $(this).find("category").text();

            if (previousCategory != category) {
                var row = imgTable.insertRow(-1);
                tableTd = row.insertCell(-1);
                tableTd.setAttribute("class", "categoryNameTd");

                var tableTdHtml = "<div><span font-size='20px' font-weight='bold' class='question-category'>" + category + "</span></div>";

                tableTd.innerHTML = tableTdHtml;
            }

            var questionRow = imgTable.insertRow(-1);
            quesTableTd = questionRow.insertCell(-1);
            quesTableTd.setAttribute("class", "questionTd");

            var questionTxt = $(this).find("questionTxt").text();
            var tableTdHtml = "<div><span font-size='20px' font-weight='bold' class='question-span'>" + questionTxt + "</span></div>";

            quesTableTd.innerHTML = tableTdHtml;

            var responseRow = imgTable.insertRow(-1);
            responseTableTd = responseRow.insertCell(-1);

            var responseTxt = $(this).find("responseTxt").text();
            var tableTdHtml = "<div><span font-size='20px' font-weight='bold' class='response-span'>" + responseTxt + "</span></div>";

            responseTableTd.innerHTML = tableTdHtml;

            previousCategory = category;

        });

        $("#feedbackTableTr").show();
    }
}

function resetPage() {
    feedbackDataArray = [];
    hideWait();
    $("input:radio").attr("checked", false);

    $(".datepicker").val("");

    $("#feedbackTypeRadioTr").hide();
    $("#dateRangeTr").hide();

    $("#appNameTr").hide();
    $("#clientNameTr").hide();
    $("#incidentIdTr").hide();
    $("#feedbackTableTr").hide();
    $("#selectedAppNameId").empty();
    $("#selectedIncidentId").empty();
    document.getElementById("selectedAppNameId").selectedIndex = 0;
    document.getElementById("selectedIncidentId").selectedIndex = 0;
    disableDocumentFields(document, false);
}

function getElementsByClassName(node, classname) {
    var a = [];
    var re = new RegExp('(^| )' + classname + '( |$)');
    var els = node.getElementsByTagName("*");
    for (var i = 0, j = els.length; i < j; i++)
        if (re.test(els[i].className))
            a.push(els[i]);
    return a;
}
