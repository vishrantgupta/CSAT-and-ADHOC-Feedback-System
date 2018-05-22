var applicationData = ["row0"];
var i=1;
$(document).ready(function() {
    // $('#applicationTable').dataTable();
    resetPage();
    $('#addApplication').click(function(){
        resetSelectBox();
        resetInputBox();
        $("#editApplicationHeading, #editEngagementTr, #editApplicationTr, #editApplicationTbl").hide();
        $('#addApplicationHeading, #engagementTr, #newApplicationTr').fadeToggle("fast");
    });
    $('#editApplication').click(function(){
        resetSelectBox();
        resetInputBox();
        $("#addApplicationHeading, #engagementTr, #newApplicationTr").hide();
        $("#editApplicationHeading, #editEngagementTr, #editApplicationTr").fadeToggle("fast");
    });
    
    $("#add_row").click(function(){
        
        var selectObj = document.getElementById("addAppEngagementId");
        if (selectObj.selectedIndex == 0) {
            alert("Please select engagement name first.");
            return false;
        }
        
        if(i > 9) {
           alert("More than 10 rows are not allowed.");
           return false;
        }
        $('#addr'+i).html("<td>"+ (i+1) +"</td><td><input name='application' type='text' placeholder='Application' class='form-control input-md row" + i + "'  /> </td><td><select id='clientName" + i + "' name='clientSelectBox' type='text' multiple='true'  style='display: inline; max-width: 100%; overflow-y:scroll;' class='form-control input-md row" + i + "'></td>");

        var $options = $("#clientName0 > option").clone();
        $('#clientName' + i).append($options);

        $('#tab_logic').append('<tr id="addr'+(i+1)+'"></tr>');
        applicationData.push("row" + i);
        i++;
    });
    $("#delete_row").click(function () {
        if (i > 1) {
            $("#addr" + (i - 1)).html('');
            i--;
        }
        var index = applicationData.indexOf("row" + i);
        if (index > -1) {
            applicationData.splice(index, 1);
        }
    });
} );

function udpateApplicationDetails() {
    
    var appId = ($("#editAppId").val()).trim();
    var appName = ($("#editAppName").val()).trim();
    var firstName = ($("#editFirstName").val()).trim();
    var lastName = ($("#editLastName").val()).trim();
    var engagementId = $("#editAppEngagementId").val();
    
    if (appName != "" && firstName != "" && lastName != "") {
        
        var urlString = root + contextRoot + "/page/application.do?action=updateApplicationData";

        var parameterSrt = "&appName=" + appName + "&firstName=" + firstName + "&lastName=" + lastName + "&appId=" + appId + "&engagementId=" + engagementId;
        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "successMsg", "ajaxFailHandle");
        
    } else {
        alert("Please fill all the fields.");
    }
}

function resetSelectBox() {
    var elements = document.getElementsByTagName("select");
    for(var i = 0; i < elements.length; i++) {
        elements[i].selectedIndex = 0;
    }
}

function resetInputBox() {
    var elements = document.getElementsByTagName("input");
    for(var i = 0; i < elements.length; i++) {
        if (elements[i].type == "text") {
            elements[i].value = "";
        }
    }
}

function resetPage() {
    resetInputBox();
    resetSelectBox();
    $('#editApplicationHeading, #editEngagementTr, #editApplicationTr, #addApplicationHeading, #engagementTr, #newApplicationTr, #editApplicationTbl').hide();
    hideWait();
}

function Data(engagement, application, selectedClients) {
    this.engagement = engagement;
    this.applicaiton = application;
    /*this.clientFirstName = firstName;
    this.clientLastName = lastName;*/
    
    this.selectedClients = selectedClients;
    
    return this;
}
var applicationDataArray = [];

function submitApplicationDetails() {
    
    applicationDataArray = [];
    
    var engagementId = document.getElementById("addAppEngagementId");
    
    if (engagementId.selectedIndex == 0) {
        alert("Please select engagement.");
        return false;
    }
    
    for (var i = 0; i < applicationData.length; i++) {
        var elements = document.getElementsByClassName("row" + i);
        
        var application;
        /*var clientFirstName;
        var clientLastName;*/
        var selectedClients;
        
        for (var j = 0; j < elements.length; j++) {
            
            if(elements[j].name == "application") {
                
                if(!checkSpecialCharacter(elements[j])) {
                    application = elements[j].value;
                } else {
                    return false;
                }
                
            }
            
            if(elements[j].name == "clientSelectBox") {
                var selectedValues = getSelectValues(elements[j]);
                if(selectedValues != "") {
                    selectedClients = selectedValues;
                    hideErrorCss(elements[j]);
                } else {
                    showErrorCss(elements[j]);
                    alert("Please select all client name.");
                    return false;
                }
            }
            
            
            /*if(elements[j].name == "firstName") {
                
                if(!checkSpecialCharacter(elements[j])) {
                    clientFirstName = elements[j].value;
                } else {
                    return false;
                }
                
            } else if(elements[j].name == "application") {
                
                if(!checkSpecialCharacter(elements[j])) {
                    application = elements[j].value;
                } else {
                    return false;
                }
                
            } else if(elements[j].name == "lastName") {
                if(!checkSpecialCharacter(elements[j])) {
                    clientLastName = elements[j].value;
                } else {
                    return false;
                }
                
            }*/
        }
        
        if(application.trim() == "") { /*|| clientFirstName.trim() == "" || clientLastName.trim() == ""*/
            alert("Please fill all the values.");
            return false;
        }
        
        var appData = new Data(engagementId.value, application, selectedClients); /*clientFirstName, clientLastName*/
        applicationDataArray.push(appData);
    }
    
    if (applicationDataArray.length > 0) {
        var saveData = "";
        for (var i = 0; i < applicationDataArray.length; i++) {
            saveData += applicationDataArray[i].engagement + "|" + applicationDataArray[i].applicaiton + "|" + applicationDataArray[i].selectedClients + "#";
        }

        var urlString = root + contextRoot + "/page/application.do?action=saveApplicationData";

        var parameterSrt = "&saveData=" + saveData;
        
        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "successMsg", "ajaxFailHandle");
    } else {
        alert("Nothing to save, add at least one row with app details.");
        return false;
    }
    
}

function successMsg(response) {
    var userMsg = $(response).find("status").text();
    hideWait();
    alert(userMsg);
}

function ajaxFailHandle(e) {
    alert("Error Occured: Please try again.");
    resetPage();
}

function loadApplications(selectBox) {
    
    $('#editAppSelectBox').empty();
    $('#editApplicationTbl').fadeOut(100);
    
    newoption = new Option("Select Application", "Select Application", false, false);
    document.getElementById("editAppSelectBox").options.add(newoption);
    
    if (selectBox.selectedIndex != 0) {
        
        var urlString = root + contextRoot + "/page/application.do?action=getAllAppName";
        var parameterSrt = "&engagementId=" + selectBox.value;
        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "populateSelectBox", "ajaxFailHandle");
    }
}

function populateSelectBox(response) {
    var dataFoundMsg = $(response).find("noDataFound").text();
    
    if (dataFoundMsg != "") {
        alert(dataFoundMsg);
    } else {
        
        $(response).find("app").each(function() {
            var appId = $(this).find("appId").text();
            var appName = $(this).find("appName").text();
            
            newoption = new Option(appName, appId, false, false);
            document.getElementById("editAppSelectBox").options.add(newoption);
            newoption = null;
        });
        
    }
    hideWait();
}

function getApplicationDetails(appSelectBox) {
    
    $('#editApplicationTbl').hide();
    
    if (appSelectBox.selectedIndex != 0) {
        
        var urlString = root + contextRoot + "/page/application.do?action=getApplicationDetails";
        var parameterSrt = "&appId=" + appSelectBox.value;
        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "showAppDetails", "ajaxFailHandle");
    }
}

function showAppDetails(response) {
    
    var appId = $(response).find("appId").text();
    var appName = $(response).find("appName").text();
    var firstName = $(response).find("firstName").text();
    var clientName = $(response).find("clientName").text();
    
    $("#editAppId").val(appId);
    $("#editAppName").val(appName);
    $("#editFirstName").val(firstName);
    $("#editLastName").val(clientName);
    
    $('#editApplicationTbl').show();   
    hideWait();
}

function populateApplicaitons(selectObj) {
    
    imgTable = document.getElementById("applicationTable");
    $("#feedbackTableTr").hide();

    for (var i = imgTable.rows.length - 1; i >= 1; i--) {
        imgTable.deleteRow(i);
    }
    
    if (selectObj.selectedIndex != 0) {
    
        var urlString = root + contextRoot + "/page/application.do?action=getAllAppName";
        var parameterSrt = "&engagementId=" + selectObj.value;
        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "populateDataTable", "ajaxFailHandle");
        
    }
}

function populateDataTable(response) {
    
    $(response).find("app").each(function() {
        var questionRow = imgTable.insertRow(-1);

        appNameTd = questionRow.insertCell(-1);
        var appName = $(this).find("appName").text();
        var tableTdHtml = "<div><span font-size='20px' font-weight='' class=''>" + appName + "</span></div>";

        appNameTd.innerHTML = tableTdHtml;
        
        appNameTd = questionRow.insertCell(-1);
        
        appName = $(this).find("clientName").text();
        tableTdHtml = "<div><span font-size='20px' font-weight='' class=''>" + appName + "</span></div>";

        appNameTd.innerHTML = tableTdHtml;
    });
    
    // $('#applicationTable').dataTable();
    hideWait();
}

function populateClientName(selectObj) {
    
    $("#clientName0 option").remove();
    removeAllRows();
    
    if (selectObj.selectedIndex != 0) {
    
        var urlString = root + contextRoot + "/page/engagement.do?action=getClientListForEngagement";
        var parameterSrt = "&engagementId=" + selectObj.value;
        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "populateClientDetails", "ajaxFailHandle");
    }
}

function removeAllRows() {
    for(var j = i; j > 1; j--, i--) {
        if (j > 1) {
            $("#addr" + (i - 1)).html('');
        }
        var index = applicationData.indexOf("row" + i);
        if (index > -1) {
            applicationData.splice(index, 1);
        }
    }
}

function populateClientDetails(response) {
    
    var dataFoundMsg = $(response).find("noDataFound").text();
    hideWait();
    
    if (dataFoundMsg != "") {
        alert(dataFoundMsg);
        removeAllRows();
        document.getElementById("addAppEngagementId").selectedIndex = 0;
    } else {
        $(response).find("clientDetails").each(function() {
            var id = $(this).find("clientId").text();
            var firstName = $(this).find("firstName").text();
            var lastName = $(this).find("lastName").text();

            newoption = new Option((firstName + " " + lastName), id, false, false);
            document.getElementById("clientName0").options.add(newoption);
            newoption = null;
        });
    }
    
}
