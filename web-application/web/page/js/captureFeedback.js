var feedbackDataArray = [];

$(document).ready(function() {
    resetPage();
    document.getElementById("engagementId").selectedIndex = 0;
});

function FeedbackData(questionId, type, txt) {
    this.questionId = questionId;
    this.type = type;
    this.txt = txt;
    return this;
}

function showFeedbackTypeRadio(engagement) {
    resetPage();
    if (engagement.value != "Select Enagagement") {
        $("#feedbackTypeRadioTr").show();
    }
}

function showAppName(selectedRadio) {
    var engagementId = document.getElementById("engagementId").value;
    $('#descriptionId').val("");

    if (engagementId != "Select Enagagement") {
        var urlString = root + contextRoot + "/page/capture.do?action=getAppName";
        var parameterSrt = "&engagementId=" + engagementId;
        showWait();
        $.ajax({
            type: "POST",
            url: urlString,
            data: parameterSrt,
            success: function(response) {
                displayAppName(response, selectedRadio.value);
            },
            error: function(e) {
                ajaxFailHandle(e);
            }
        });
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
        document.getElementById("engagementId").selectedIndex = 0;
        resetPage();
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
        
        if(selectedRadio == "event"){
            getFeedbackForm();
        }
    }
}

function showClientName() {

    var appId = document.getElementById("selectedAppNameId").value;
    $("#clientNameTr").hide();
    
    var selectedRadio = $('input[name=feedbackTypeRadio]:checked').val();
    if (selectedRadio != "event") {
        $("#descriptionIdTr").hide();
        $("#feedbackTableTr").hide();
    }

    if (appId != "Select Application") {
        var urlString = root + contextRoot + "/page/capture.do?action=getClientName";
        var parameterSrt = "&appId=" + appId;
        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "displayClientDetails", "ajaxFailHandle");
    }
}

function displayClientDetails(response) {
    var clientNameTd = document.getElementById("clientNameTd");

    var clientNameHtml = "";

    $(response).find("start").each(function() {
        var clientName = $(this).find("clientName").text();
        clientNameHtml += "<span class='client-span'>" + clientName + "</span>";
    });

    clientNameTd.innerHTML = clientNameHtml;

    $("#clientNameTr").show();
    hideWait();
    
    var selectedRadio = $('input[name=feedbackTypeRadio]:checked').val();
    if (selectedRadio != "event") {
        getFeedbackForm();
    }
}

function ajaxFailHandle(e) {
    disableDocumentFields(document, false);
    resetPage();
    alert("Error occured, please try again.");
    reportJSError("Error occured in report " + e.responseText);
}

function getFeedbackForm() {

    var urlString = root + contextRoot + "/page/capture.do?action=getFeedbackFormQuestions";
    var parameterSrt = "";

    ajaxCallWithFailureHandler(urlString, parameterSrt, "displayFeedbackForm", "ajaxFailHandle");
}

function displayFeedbackForm(response) {

    hideWait();

    imgTable = document.getElementById("feedbackTable");
    $("#feedbackTableTr").hide();

    for (var i = imgTable.rows.length - 1; i >= 0; i--) {
        imgTable.deleteRow(i);
    }

    var dataFoundMsg = $(response).find("nodatafound").text();
    if (dataFoundMsg != '') {
        alert(dataFoundMsg);
    } else {
        $(response).find("category").each(function() {

            var categoryName = $(this).attr("name");

            var row = imgTable.insertRow(-1);
            tableTd = row.insertCell(-1);
            tableTd.setAttribute("class", "categoryNameTd");

            var tableTdHtml = "<div><span font-size='20px' font-weight='bold' class='question-category'>" + categoryName + "</span></div>";
            tableTd.innerHTML = tableTdHtml;

            var count = 0;
            $(this).find("question").each(function() {

                var questionId = $(this).attr("id");
                var questiontype = $(this).attr("type");
                var ratingMaxValue = $(this).attr("ratingMaxValue");
                var countflag = $(this).attr("count");

                var questionTxt = $(this).text();

                var questionRow = imgTable.insertRow(-1);
                questionRowTd = questionRow.insertCell(-1);
                questionRowTd.setAttribute("class", "questionTd");

                var questionRowTdHtml = "<div><span class='question-span' font-size='20px' font-weight='bold'>" + (countflag == 'Y' ? (++count) + ". " : "") + questionTxt + "</span></div>";
                questionRowTd.innerHTML = questionRowTdHtml;

                if (questiontype == 'OPEN_TEXT') {
                    var txtRow = imgTable.insertRow(-1);
                    txtRowRowTd = txtRow.insertCell(-1);

                    var txtRowRowTdHtml = "<textarea class='openTxt form-control' maxlength='500' placeholder='Message' id='" + questionId + "'></textarea>";
                    txtRowRowTd.innerHTML = txtRowRowTdHtml;
                } else if (questiontype == 'RATING') {
                    var ratingRow = imgTable.insertRow(-1);
                    ratingRowTd = ratingRow.insertCell(-1);

                    var ratingRowTdHtml = "";
                    if (ratingMaxValue == 5 || ratingMaxValue == 10) {
                        ratingRowTdHtml = "<div class='rating-" + ratingMaxValue + "' id='" + questionId + "'></div></td></tr><tr><td><span font-size='20px' font-weight='bold'>Your Rating: </span><span class='rating-span' id='span-" + questionId + "'>not set</span>";
                    }

                    ratingRowTd.innerHTML = ratingRowTdHtml;
                }
            });

        });

        var rowSubmitBtn = imgTable.insertRow(-1);
        rowSubmitBtnTd = rowSubmitBtn.insertCell(-1);
        rowSubmitBtnTd.setAttribute("style", "text-align: right");
        var rowSubmitBtnTdHtml = "<input id='submitBtn' type='button' class='form-control btn-primary' name='submit' onclick='return submitFeedback()' value='Submit'/>";
        rowSubmitBtnTd.innerHTML = rowSubmitBtnTdHtml;

        enableStar();

        $("#descriptionIdTr").show();
        $("#feedbackTableTr").show();
    }

}

function enableStar() {
    $('.rating-10').ratings(10);
    $('.rating-5').ratings(5);
}

function submitFeedback() {

    var engagementId = document.getElementById("engagementId").value;
    
    var descriptionTxt = document.getElementById("descriptionId");
    if (descriptionTxt == null || descriptionTxt.value == "") {
        alert("Please enter description.");
        return false;
    }

    var openTxtElements = getElementsByClassName(document.body, 'openTxt');

    var flag = false;
    for (var i = 0; i < openTxtElements.length; i++) {
        var textbox = openTxtElements[i];
        
        if (textbox.value == "" || textbox.value == null) {
            alert("Please fill all the fields.");
            return false;
        } else {
            if(/^[a-zA-Z0-9- ]*$/.test(textbox.value) == false) {
                textbox.parentNode.className += " has-error";
                flag = true;
            } else {
                textbox.parentNode.className = textbox.parentNode.className.replace("has-error", "");
                var data = new FeedbackData(textbox.id, 'OPEN_TEXT', textbox.value);
                feedbackDataArray.push(data);
            }
        }
    }

    if(flag) {
        alert('Special characters not allowed for message.');
        return false;
    }

    var ratingSpan = getElementsByClassName(document.body, 'rating-span');

    for (var i = 0; i < ratingSpan.length; i++) {
        var spans = ratingSpan[i];
        var spanTxt = spans.innerHTML;
        if (spanTxt == "" || spanTxt == null || spanTxt == 'not set') {
            alert("Please fill all the fields.");
            return false;
        } else {
            var data = new FeedbackData(spans.id.split('-')[1], 'RATING', spanTxt);
            feedbackDataArray.push(data);
        }
    }

    var feedbackData = "";
    for (var int = 0; int < feedbackDataArray.length; int++) {
        feedbackData += feedbackDataArray[int].questionId + ":" + feedbackDataArray[int].type + ":" + feedbackDataArray[int].txt + "#";
    }

    var conf = confirm("Are you sure you want to submit feedback?");
    if (!conf) {
        feedbackDataArray = [];
        return false;
    } else {
        disableDocumentFields(document, true);

        var appIdObj = document.getElementById("selectedAppNameId");
        var appId = appIdObj.selectedIndex == 0 ? "" : appIdObj.value;
        
        var selectedRadio = $('input[name=feedbackTypeRadio]:checked').val();
        
        var urlString = root + contextRoot + "/page/capture.do?action=submitFeedback";
        var parameterSrt = "&appId=" + appId + "&descriptionTxt=" + descriptionTxt.value + "&engagementId=" + engagementId + "&incidentType=" + selectedRadio + "&feedbackData=" + feedbackData.substr(0, (feedbackData.length - 1));

        ajaxCallWithFailureHandler(urlString, parameterSrt, "successMsg", "ajaxFailHandle");
    }
}

function successMsg(response) {
    disableDocumentFields(document, false);
    var userMsg = $(response).find("status").text();

    var msg = "";
    if (userMsg == "success") {
        msg = "Thanks for submitting your valuable feedback.";
    } else if (userMsg == "fail") {
        msg = "Some error occured please try again.";
    } else {
        msg = userMsg;
    }

    resetPage();
    document.getElementById("engagementId").selectedIndex = 0;
    
    alert(msg);

}

function resetPage() {
    
    feedbackDataArray = [];
    $("input:radio").attr("checked", false);
    $("#feedbackTypeRadioTr").hide();
    $("#appNameTr").hide();
    $("#clientNameTr").hide();
    $("#descriptionIdTr").hide();
    $("#feedbackTableTr").hide();
    hideWait();

    document.getElementById("selectedAppNameId").selectedIndex = 0;
    document.getElementById("descriptionId").value = "";
    disableDocumentFields(document, false);
}
